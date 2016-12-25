package ru.social.client;


import com.vaadin.cdi.NormalViewScoped;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import ru.social.server.DataService;
import ru.social.server.model.Contact;

import javax.inject.Inject;

@NormalViewScoped
public class ContactForm extends FormLayout {
    private TextField firstName = new TextField("Имя: ");
    private TextField lastName = new TextField("Фамилия: ");
    private TextField phoneNumber = new TextField("Телефон");

    private Button saveBtn = new Button("Сохранить");
    private Button deleteBtn = new Button("Удалить");

    @Inject
    private DataService service;

    @Inject
    private DeleteWarningWindow warningWindow;

    @Inject
    private MainView mainView;

    private Contact contact;

    public ContactForm() {
        createFirstNameTextField();
        createLastNameTextField();
        createPhoneNumberTextField();
        createSaveBtn();
        createDeleteBtn();

        final HorizontalLayout btnLayout = new HorizontalLayout();
        btnLayout.setSpacing(true);
        btnLayout.addComponent(saveBtn);
        btnLayout.addComponent(deleteBtn);

        addComponent(firstName);
        addComponent(lastName);
        addComponent(phoneNumber);
        addComponent(btnLayout);

        setSizeUndefined();
    }

    protected void setContact(Contact contact) {
        this.contact = contact;

        if (contact.isPersisted()) {
            firstName.setValue(contact.getFirstName());
            lastName.setValue(contact.getLastName());
            phoneNumber.setValue(contact.getPhoneNumber());
        }

        if (!contact.isPersisted()) {
            firstName.setValue("");
            lastName.setValue("");
            phoneNumber.setValue("");
        }

        //BeanFieldGroup.bindFieldsUnbuffered(contact, this);
        setVisible(true);
        deleteBtn.setVisible(contact.isPersisted());
    }

    private void createSaveBtn() {
        saveBtn.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        saveBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        saveBtn.addClickListener(clickEvent -> {
            phoneNumber.setConverter(Long.class);
            String tfValue = phoneNumber.getValue();
            try {
                Long convertValue = (Long) phoneNumber.getConvertedValue();
                actionSelector();
                phoneNumber.setConverter(String.class);
            } catch (Converter.ConversionException e) {
                Notification.show("Используются запрещенные символы: " + tfValue);
                phoneNumber.setConverter(String.class);
            }
        });
    }

    private void createDeleteBtn() {
        deleteBtn.setClickShortcut(ShortcutAction.KeyCode.DELETE);
        deleteBtn.addStyleName(ValoTheme.BUTTON_DANGER);

        deleteBtn.addClickListener(clickEvent -> {
            createWarningWindow();
        });
    }

    protected void actionSelector() {
        if (isTextFieldNotEmpty()) {
            if (contact.isPersisted()) {
                getValuesFromTextFiels();
                editHandler();
            }

            if (!contact.isPersisted()) {
                getValuesFromTextFiels();
                saveHandler();
            }
        } else {
            Notification.show("Форма содержит незаполненные поля");
        }
    }

    private void getValuesFromTextFiels() {
        contact.setFirstName(firstName.getValue());
        contact.setLastName(lastName.getValue());
        contact.setPhoneNumber(phoneNumber.getValue());
    }

    private boolean isTextFieldNotEmpty() {
        if (firstName.getValue().isEmpty() || lastName.getValue().isEmpty() || phoneNumber.getValue().isEmpty()) {
            return false;
        }
        return true;
    }

    protected void editHandler() {
        service.getContactDAO().edit(contact);
        mainView.updateTable();
        setVisible(false);

        Notification.show("Контакт, "
                + contact.getFirstName()
                + " " + contact.getLastName()
                + " тел. " + contact.getPhoneNumber()
                + ", изменен.");
    }

    protected void saveHandler() {
        service.getContactDAO().add(contact);
        mainView.updateTable();
        setVisible(false);

        Notification.show("Новый контакт, "
                + contact.getFirstName()
                + " " + contact.getLastName()
                + " тел. " + contact.getPhoneNumber()
                + ", добавлен.");
    }

    protected void deleteHandler() {
        service.getContactDAO().remove(contact.getId());
        warningWindow.close();
        mainView.updateTable();
        setVisible(false);

        Notification.show("Контакт, "
                + contact.getFirstName()
                + " " + contact.getLastName()
                + " тел. " + contact.getPhoneNumber()
                + ", удален.");
    }

    private void createFirstNameTextField() {
        firstName.selectAll();
        firstName.setInputPrompt("Введите имя...");
        firstName.setMaxLength(25);
        firstName.setWidth("220px");
    }

    private void createLastNameTextField() {
        lastName.setInputPrompt("Введите фамилию...");
        lastName.setMaxLength(25);
        lastName.setWidth("220px");
    }

    private void createPhoneNumberTextField() {
        phoneNumber.setInputPrompt("Введите телефон...");
        phoneNumber.setMaxLength(11);
        phoneNumber.setWidth("220px");
    }

    private void createWarningWindow() {
        warningWindow.setWidth("25%");
        warningWindow.setHeight("20%");
        warningWindow.setCaption("Предупреждение");
        warningWindow.center();
        warningWindow.setWindowMode(WindowMode.NORMAL);
        warningWindow.setDraggable(false);
        warningWindow.setClosable(true);
        warningWindow.setResizable(false);

        UI.getCurrent().addWindow(warningWindow);
    }
}
