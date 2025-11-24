package ch.martinelli.vaadin.workshop.ui.views.helloworld;

import ch.martinelli.vaadin.workshop.ui.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.Instant;

@AnonymousAllowed
@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public HelloWorldView(ThreadPoolTaskScheduler scheduler) {
        name = new TextField("Your name");
        name.setId("name");
        sayHello = new Button("Say hello");
        sayHello.setId("say-hello");
        sayHello.addClickListener(e ->
                scheduler.schedule(
                        () -> sayHello.getUI().ifPresent(ui -> ui.access(() -> Notification.show("Hello " + name.getValue()))),
                        Instant.now().plusSeconds(1)));
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        add(name, sayHello);
    }

}
