package ch.martinelli.vaadin.workshop.ui.views.helloworld;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.BrowserTest;
import com.vaadin.testbench.BrowserTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class HelloWorldViewIT extends BrowserTestBase {

    @BeforeEach
    public void setup() {
        getDriver().get("http://localhost:8080/hello");
    }

    @BrowserTest
    public void say_hello() {
        $(TextFieldElement.class).first().setValue("Peter Muster");
        $(ButtonElement.class).first().click();

        NotificationElement notificationElement = $(NotificationElement.class).first();

        Assertions.assertThat(notificationElement.getText()).isEqualTo("Hello Peter Muster");
    }

}
