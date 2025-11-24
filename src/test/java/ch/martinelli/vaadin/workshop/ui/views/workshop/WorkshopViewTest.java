package ch.martinelli.vaadin.workshop.ui.views.workshop;

import ch.martinelli.vaadin.workshop.entity.Workshop;
import ch.martinelli.vaadin.workshop.ui.views.KaribuTest;
import com.github.mvysny.kaributesting.v10.GridKt;
import com.github.mvysny.kaributesting.v10.LocatorJ;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class WorkshopViewTest extends KaribuTest {

    @Test
    void select_row_and_change_title() {
        UI.getCurrent().navigate(WorkshopView.class);

        Grid<Workshop> grid = LocatorJ._get(Grid.class);

        Assertions.assertThat(GridKt._size(grid)).isEqualTo(12);

        Workshop workshop = GridKt._get(grid, 0);
        Assertions.assertThat(workshop.getTitle()).isEqualTo("ATDD mit Spring Boot & Karate");

        GridKt._clickItem(grid, 0);

        TextField title = LocatorJ._get(TextField.class, spec -> spec.withLabel("Title"));
        Assertions.assertThat(title.getValue()).isEqualTo("ATDD mit Spring Boot & Karate");

        LocatorJ._setValue(title, "Test");

        LocatorJ._click(LocatorJ._get(Button.class, spec -> spec.withText("Save")));

        Workshop workshopAfterSave = GridKt._get(grid, 0);
        Assertions.assertThat(workshopAfterSave.getTitle()).isEqualTo("Test");
    }

    @Test
    void filter() {
        UI.getCurrent().navigate(WorkshopView.class);

        Grid<Workshop> grid = LocatorJ._get(Grid.class);
        Assertions.assertThat(GridKt._size(grid)).isEqualTo(12);

        TextField filter = LocatorJ._get(TextField.class, spec -> spec.withLabel("Filter"));
        filter.setValue("Vaadin");

        Assertions.assertThat(GridKt._size(grid)).isEqualTo(1);

        Workshop workshopAfterFilter = GridKt._get(grid, 0);
        Assertions.assertThat(workshopAfterFilter.getTitle()).isEqualTo("Java Full-Stack Entwicklung mit Vaadin");
    }
}
