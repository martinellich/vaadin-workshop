package ch.martinelli.vaadin.workshop.ui.views;

import com.microsoft.playwright.*;
import in.virit.mopo.Mopo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class PlaywrightIT {

    @LocalServerPort
    protected Integer localServerPort;

    private static Playwright playwright;
    private static Browser browser;

    protected Page page;
    protected Mopo mopo;
    private BrowserContext browserContext;

    @BeforeAll
    static void setUpClass() {
        playwright = Playwright.create();
        BrowserType browserType = playwright.chromium();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        // set to false if you want to see the browser during development
        launchOptions.headless = true;
        browser = browserType.launch(launchOptions);
    }

    @AfterAll
    static void tearDownClass() {
        browser.close();
        playwright.close();
    }

    @BeforeEach
    void setUp() {
        browserContext = browser.newContext();
        page = browserContext.newPage();
        mopo = new Mopo(page);
    }

    @AfterEach
    void tearDown() {
        page.close();
        browserContext.close();
    }

    protected void login(String username) {
        // Navigate to the login view
        page.navigate("http://localhost:%d/login".formatted(localServerPort));

        // Wait for the login page to load
        page.waitForLoadState();

        // Login with provided username and default password "pass"
        var loginPO = new LoginPO(page);
        loginPO.login(username, "pass");

        // Wait for navigation after login
        page.waitForLoadState();

        // Wait for Vaadin client-server connection to settle
        mopo.waitForConnectionToSettle();
    }

}
