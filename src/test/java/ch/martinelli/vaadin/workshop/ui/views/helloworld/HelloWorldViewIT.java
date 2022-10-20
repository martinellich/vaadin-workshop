package ch.martinelli.vaadin.workshop.ui.views.helloworld;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class HelloWorldViewIT extends TestBenchTestCase {

    @Rule
    public ScreenshotOnFailureRule rule = new ScreenshotOnFailureRule(this, true);

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setup() {
        // Create a new browser instance
        setDriver(new ChromeDriver());
        // Open the application
        getDriver().get("http://localhost:8080/hello");
    }

    @Test
    public void say_hello() {
        $(TextFieldElement.class).first().setValue("Workshop-Tage 2022");
        $(ButtonElement.class).first().click();

        NotificationElement notificationElement = $(NotificationElement.class).first();

        Assert.assertEquals("Hello Workshop-Tage 2022", notificationElement.getText());
    }

}
