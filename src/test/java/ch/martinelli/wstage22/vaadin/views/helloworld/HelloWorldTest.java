package ch.martinelli.wstage22.vaadin.views.helloworld;

import ch.martinelli.wstage22.vaadin.views.KaribuTest;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.mvysny.kaributesting.v10.LocatorJ._assert;
import static com.github.mvysny.kaributesting.v10.LocatorJ._get;
import static com.github.mvysny.kaributesting.v10.NotificationsKt.expectNotifications;

@SpringBootTest
class HelloWorldTest extends KaribuTest {

    @Test
    void say_hello() {
        _get(TextField.class, spec -> spec.withCaption("Your name")).setValue("Workshop-Tage 2022");
        _get(Button.class, spec -> spec.withCaption("Say hello")).click();

        _assert(Notification.class, 1);

        expectNotifications("Hello Workshop-Tage 2022");
    }

}
