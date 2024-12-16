package ch.martinelli.vaadin.workshop.ui.views;


import ch.martinelli.vaadin.workshop.security.SecurityContext;
import ch.martinelli.vaadin.workshop.ui.views.about.AboutView;
import ch.martinelli.vaadin.workshop.ui.views.dashboard.DashboardView;
import ch.martinelli.vaadin.workshop.ui.views.helloworld.HelloWorldView;
import ch.martinelli.vaadin.workshop.ui.views.participant.ParticipantView;
import ch.martinelli.vaadin.workshop.ui.views.workshop.WorkshopView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.Authentication;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private final AuthenticationContext authenticationContext;
    private H1 viewTitle;

    public MainLayout(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassNames("view-toggle");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames("view-title");

        Header header = new Header(toggle, viewTitle);
        header.addClassNames("view-header");
        return header;
    }

    private Component createDrawerContent() {
        H2 appName = new H2("Vaadin Workshop");
        appName.addClassNames("app-name");

        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(appName,
                createNavigation(), createFooter());
        section.addClassNames("drawer-section");
        return section;
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Dashboard", DashboardView.class, VaadinIcon.DASHBOARD.create()));
        nav.addItem(new SideNavItem("Workshops", WorkshopView.class, VaadinIcon.NOTEBOOK.create()));
        nav.addItem(new SideNavItem("Participants", ParticipantView.class, VaadinIcon.USER.create()));
        nav.addItem(new SideNavItem("Hello World", HelloWorldView.class, VaadinIcon.GLOBE.create()));
        nav.addItem(new SideNavItem("About", AboutView.class, VaadinIcon.FILE.create()));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("app-nav-footer");

        if (SecurityContext.getAuthentication().isPresent()) {
            Authentication authentication = SecurityContext.getAuthentication().get();
            Div logout = new Div();
            logout.getStyle().set("padding", "10px");
            logout.getStyle().set("cursor", "pointer");
            logout.add(("Logout (" + authentication.getName() + ")"));
            logout.addClickListener(e -> authenticationContext.logout());
            layout.add(logout);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
