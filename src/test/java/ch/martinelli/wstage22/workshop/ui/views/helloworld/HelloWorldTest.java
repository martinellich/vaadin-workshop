package ch.martinelli.wstage22.workshop.ui.views.helloworld;

import ch.martinelli.wstage22.workshop.ui.views.KaribuTest;
import com.github.mvysny.kaributesting.v10.LocatorJ;
import com.github.mvysny.kaributesting.v10.NotificationsKt;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelloWorldTest extends KaribuTest {

    @Test
    void say_hello() {
        UI.getCurrent().navigate(HelloWorldView.class);

        LocatorJ._get(TextField.class, spec -> spec.withCaption("Your name")).setValue("Workshop-Tage 2022");
        LocatorJ._get(Button.class, spec -> spec.withCaption("Say hello")).click();

        LocatorJ._assert(Notification.class, 1);

        NotificationsKt.expectNotifications("Hello Workshop-Tage 2022");
    }

}
