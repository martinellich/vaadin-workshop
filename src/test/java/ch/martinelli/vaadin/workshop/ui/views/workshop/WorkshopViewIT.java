package ch.martinelli.vaadin.workshop.ui.views.workshop;

import ch.martinelli.vaadin.workshop.ui.views.PlaywrightIT;
import org.junit.jupiter.api.Test;

class WorkshopViewIT extends PlaywrightIT {

    @Test
    void filter() {
        page.navigate("http://localhost:%d".formatted(localServerPort));

    }

}
