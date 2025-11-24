package ch.martinelli.vaadin.workshop.ui.views.workshop;

import ch.martinelli.vaadin.workshop.ui.views.PlaywrightIT;
import com.microsoft.playwright.Locator;
import in.virit.mopo.GridPw;
import in.virit.mopo.Mopo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class WorkshopViewIT extends PlaywrightIT {

    @Test
    void select_row_and_change_title() {
        page.navigate("http://localhost:%d".formatted(localServerPort));
        var mopo = new Mopo(page);

        GridPw gridPw = new GridPw(page);
        // Caution! This test runs in the browser and depending on the view port not all rows are rendered.
        Assertions.assertThat(gridPw.getRenderedRowCount()).isGreaterThan(1);

        GridPw.RowPw row = gridPw.getRow(0);
        Assertions.assertThat(row.getCell(0).innerText()).isEqualTo("ATDD mit Spring Boot & Karate");

        row.select();
        mopo.waitForConnectionToSettle();

        Locator title = page.locator("vaadin-text-field")
                .filter(new Locator.FilterOptions().setHasText("Title"))
                .locator("input");
        Assertions.assertThat(title.inputValue()).isEqualTo("ATDD mit Spring Boot & Karate");

        title.fill("Test");

        page.locator("vaadin-button").filter(new Locator.FilterOptions().setHasText("Save")).click();
        mopo.waitForConnectionToSettle();

        GridPw.RowPw rowAfterSave = gridPw.getRow(0);
        Assertions.assertThat(rowAfterSave.getCell(0).innerText()).isEqualTo("Test");
    }


    @Test
    void filter() {
        page.navigate("http://localhost:%d".formatted(localServerPort));
        var mopo = new Mopo(page);

        GridPw gridPw = new GridPw(page);
        // Caution! This test runs in the browser and depending on the view port not all rows are rendered.
        Assertions.assertThat(gridPw.getRenderedRowCount()).isGreaterThan(1);

        Locator inputField = page.locator("vaadin-text-field > input").first();
        inputField.fill("Vaadin");
        inputField.blur();

        mopo.waitForConnectionToSettle();

        Assertions.assertThat(gridPw.getRenderedRowCount()).isEqualTo(1);

        GridPw.RowPw row = gridPw.getRow(0);
        Assertions.assertThat(row.getCell(0).innerText()).isEqualTo("Java Full-Stack Entwicklung mit Vaadin");
    }

}
