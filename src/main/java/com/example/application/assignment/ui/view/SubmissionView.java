package com.example.application.assignment.ui.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "submission", layout = MainLayout.class)
@PageTitle("Submission")
@Menu(title = "Submissions", icon = "vaadin:upload", order = 1)
@PermitAll
public class SubmissionView extends VerticalLayout {

    public SubmissionView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(createTabSheet());
    }

    private TabSheet createTabSheet() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        tabSheet.add("Dashboard",
                new Div(new Text("This is the Dashboard tab content")));
        tabSheet.add("Payment",
                new Div(new Text("This is the Payment tab content")));
        tabSheet.add("Shipping",
                new Div(new Text("This is the Shipping tab content")));

        tabSheet.addThemeVariants(TabSheetVariant.LUMO_BORDERED);
        return tabSheet;
    }
}