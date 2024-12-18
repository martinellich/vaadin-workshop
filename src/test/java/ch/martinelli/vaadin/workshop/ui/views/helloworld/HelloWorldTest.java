package ch.martinelli.vaadin.workshop.ui.views.helloworld;

import ch.martinelli.vaadin.workshop.ui.views.KaribuTest;
import com.github.mvysny.kaributesting.v10.LocatorJ;
import com.github.mvysny.kaributesting.v10.NotificationsKt;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.jupiter.api.Test;

class HelloWorldTest extends KaribuTest {

    @Test
    void say_hello() {
        UI.getCurrent().navigate(HelloWorldView.class);

        TextField input = LocatorJ._get(TextField.class, spec -> spec.withLabel("Your name"));
        LocatorJ._setValue(input, "Peter Muster");
        Button button = LocatorJ._get(Button.class, spec -> spec.withText("Say hello"));
        LocatorJ._click(button);

        LocatorJ._assert(Notification.class, 1);

        NotificationsKt.expectNotifications("Hello Peter Muster");
    }

}
