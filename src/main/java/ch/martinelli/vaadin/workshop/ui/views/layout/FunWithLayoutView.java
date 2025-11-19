package ch.martinelli.vaadin.workshop.ui.views.layout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("fun")
public class FunWithLayoutView extends VerticalLayout {

    public FunWithLayoutView() {
// Root layout
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        // --- Top horizontal bar: alignment + spacing + padding ---
        HorizontalLayout topBar = new HorizontalLayout();
        topBar.setWidthFull();
        topBar.setPadding(true);
        topBar.setSpacing(true);
        topBar.setAlignItems(FlexComponent.Alignment.CENTER);
        topBar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Button left = new Button("Left");
        Button center = new Button("Center");
        Button right = new Button("Right");

        topBar.add(left, center, right);

        // --- Middle area: horizontal layout with 3 vertical columns ---
        HorizontalLayout content = new HorizontalLayout();
        content.setSizeFull();
        content.setPadding(true);
        content.setSpacing(true);
        content.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.STRETCH);

        // Column 1: default alignment, spacing on, no padding
        VerticalLayout column1 = new VerticalLayout();
        column1.setPadding(false);
        column1.setSpacing(true);
        column1.setWidthFull();
        column1.add(
                new Button("Top left"),
                new Button("Middle left"),
                new Button("Bottom left")
        );
        content.setFlexGrow(1, column1);

        // Column 2: center aligned
        VerticalLayout column2 = new VerticalLayout();
        column2.setPadding(false);
        column2.setSpacing(true);
        column2.setWidthFull();
        column2.setAlignItems(FlexComponent.Alignment.CENTER);
        column2.add(
                new Button("Top center"),
                new Button("Middle center"),
                new Button("Bottom center")
        );
        content.setFlexGrow(1, column2);

        // Column 3: right aligned
        VerticalLayout column3 = new VerticalLayout();
        column3.setPadding(false);
        column3.setSpacing(true);
        column3.setWidthFull();
        column3.setAlignItems(FlexComponent.Alignment.END);
        column3.add(
                new Button("Top right"),
                new Button("Middle right"),
                new Button("Bottom right")
        );
        content.setFlexGrow(1, column3);

        content.add(column1, column2, column3);

        // --- Bottom bar: buttons filling horizontally ---
        HorizontalLayout bottomBar = new HorizontalLayout();
        bottomBar.setWidthFull();
        bottomBar.setPadding(true);
        bottomBar.setSpacing(true);

        Button fill1 = new Button("Fill 1");
        Button fill2 = new Button("Fill 2");
        Button fill3 = new Button("Fill 3");

        // Make buttons fill equally
        fill1.setWidthFull();
        fill2.setWidthFull();
        fill3.setWidthFull();

        bottomBar.add(fill1, fill2, fill3);
        bottomBar.setFlexGrow(1, fill1, fill2, fill3);

        // Add everything to root
        add(topBar, content, bottomBar);

        // Middle content should take the available space
        setFlexGrow(0, topBar);
        setFlexGrow(1, content);
        setFlexGrow(0, bottomBar);
    }
}
