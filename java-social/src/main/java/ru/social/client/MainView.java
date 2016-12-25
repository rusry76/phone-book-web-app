package ru.social.client;

import com.vaadin.cdi.*;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import ru.social.server.DataService;
import ru.social.server.model.Contact;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;


@CDIView("View")
@NormalViewScoped
public class MainView extends VerticalLayout implements View {
    @Inject
    private ContactForm contactForm;

    @Inject
    private HeaderLayout header;

    private PagedTable tableOfContacts = new PagedTable();
    private TextField filtering = new TextField();
    private Button addBtn = new Button("Добавить");

    private HorizontalLayout filteringLayout = new HorizontalLayout();
    private HorizontalLayout tableHorizLayout = new HorizontalLayout();

    @Inject
    private DataService service;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        initFilteringLayout();
        initTableLayout();

        final Panel headerPanel = new Panel();
        headerPanel.setContent(header);
        headerPanel.addStyleName("header_panel");

        final HorizontalLayout controls = tableOfContacts.createControls();

        addComponent(headerPanel);
        addComponent(filteringLayout);
        addComponent(tableHorizLayout);
        addComponent(controls);

        setComponentAlignment(controls, Alignment.MIDDLE_CENTER);
    }

    public MainView() {
    }

    public void initTableLayout() {
        tableHorizLayout.setSizeFull();
        tableHorizLayout.setSpacing(true);
        tableHorizLayout.setMargin(true);
        tableHorizLayout.setImmediate(true);

        initTableOfContacts();

        tableHorizLayout.addComponent(tableOfContacts);
        tableHorizLayout.addComponent(contactForm);

        tableHorizLayout.setExpandRatio(tableOfContacts, 2f);

        tableHorizLayout.setComponentAlignment(tableOfContacts, Alignment.MIDDLE_LEFT);
        tableHorizLayout.setComponentAlignment(contactForm, Alignment.MIDDLE_RIGHT);
    }

    public void initTableOfContacts() {
        tableOfContacts.setContainerDataSource(initContainerOfContacts());
        tableOfContacts.setVisibleColumns("firstName", "lastName", "phoneNumber");
        tableOfContacts.setColumnHeader("firstName", "<b>" + "Имя" + "</b>");
        tableOfContacts.setColumnHeader("lastName", "<b>" + "Фамилия" + "</b>");
        tableOfContacts.setColumnHeader("phoneNumber", "<b>" + "Телелфон" + "</b>");
        tableOfContacts.setSizeFull();
        //tableOfContacts.setPageLength(15);

        contactForm.setVisible(false);

        tableOfContacts.addValueChangeListener(event -> {
            if (event.getProperty().getValue() != null) {
                Contact contact = (Contact) event.getProperty().getValue();
                contactForm.setVisible(true);
                contactForm.setContact(contact);
            } else {
                contactForm.setVisible(false);
            }
        });
    }

    public void initAddContactButton() {
        addBtn.setClickShortcut(ShortcutAction.KeyCode.I);
        addBtn.addClickListener(clickEvent -> {
            tableOfContacts.select(null);
            contactForm.setContact(new Contact());
        });
    }

    public void initFilteringLayout() {
        initFilter();
        initAddContactButton();

        filteringLayout.setWidth("100%");
        filteringLayout.setHeightUndefined();
        filteringLayout.setMargin(true);
        filteringLayout.addComponent(addBtn);
        filteringLayout.addComponent(filtering);

        filteringLayout.setExpandRatio(filtering, 2f);

        filteringLayout.setComponentAlignment(addBtn, Alignment.MIDDLE_LEFT);
        filteringLayout.setComponentAlignment(filtering, Alignment.MIDDLE_CENTER);
    }

    public void initFilter() {
        filtering.setInputPrompt("Поиск...");
        filtering.setWidth("30%");
        filtering.addTextChangeListener(textChangeEvent -> {
            if (!textChangeEvent.getText().isEmpty()) {
                tableOfContacts.setContainerDataSource(new BeanItemContainer<>(Contact.class,
                        service.getContactDAO().findAll(textChangeEvent.getText(), 0, 0)));
                tableOfContacts.setVisibleColumns("firstName", "lastName", "phoneNumber");
            } else {
                updateTable();
            }
        });
    }

    public BeanItemContainer initContainerOfContacts() {
        List<Contact> contacts = service.getContactDAO().findAll(filtering.getValue(), 0, 0);

        BeanItemContainer<Contact> container = new BeanItemContainer<Contact>(Contact.class, contacts); //container.addAll(contacts);
        return container;
    }

    public void updateTable() {
        List<Contact> contacts = service.getContactDAO().findAll(filtering.getValue(), 0, 0);
        tableOfContacts.setContainerDataSource(new BeanItemContainer<>(Contact.class, contacts));
        tableOfContacts.setVisibleColumns("firstName", "lastName", "phoneNumber");
    }
}
