package ru.social.client;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import ru.social.server.DataService;
import ru.social.server.model.Contact;

import javax.inject.Inject;
import java.util.List;


@Theme("mytheme")
@CDIUI("")
@SuppressWarnings("serial")
public class MyUI extends UI {

    @Inject
    private CDIViewProvider viewProvider;

    @Inject
    private JaasAccessControl control;

    @Inject
    private DataService service;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        //navigator.addView("login", LoginView.class);
        //navigator.addView("View", MainView.class);

        if (control.isUserSignedIn()) {
            navigator.navigateTo("View");
        } else {
            navigator.navigateTo("login");
        }
        /*VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        HorizontalLayout tableLayout = new HorizontalLayout();
        tableLayout.setWidth("100%");
        PagedTable pagedTable = new PagedTable("Cation");
        pagedTable.setContainerDataSource(getContainer());

        tableLayout.addComponent(pagedTable);
        tableLayout.setWidth("100%");

        HorizontalLayout layout = pagedTable.createControls();
        layout.setSpacing(true);
        layout.setWidthUndefined();

        mainLayout.addComponent(pagedTable);
        mainLayout.addComponent(layout);

        mainLayout.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);

        setContent(mainLayout);*/
    }

    /*public BeanItemContainer getContainer() {
        List<Contact> contacts = service.getContactDAO().findAll();
        BeanItemContainer<Contact> container = new BeanItemContainer<Contact>(Contact.class, contacts);

        return container;
    }*/
}
