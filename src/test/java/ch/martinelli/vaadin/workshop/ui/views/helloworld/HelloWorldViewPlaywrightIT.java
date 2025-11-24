package ch.martinelli.vaadin.workshop.ui.views.helloworld;

import ch.martinelli.vaadin.workshop.ui.views.PlaywrightIT;
import in.virit.mopo.Mopo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HelloWorldViewPlaywrightIT extends PlaywrightIT {

    @Test
    void say_hello() {
        page.navigate("http://localhost:%d/hello".formatted(localServerPort));
        var mopo = new Mopo(page);

        page.locator("vaadin-text-field > input").fill("Test");
        mopo.click(page.getByText("Say hello"));

        var notification = page.locator("vaadin-notification-card");
        Assertions.assertThat(notification.innerText()).endsWith("Hello Test");
    }

}