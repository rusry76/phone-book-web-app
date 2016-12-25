package ru.social.client;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import javax.inject.Inject;

@CDIView("login")
public class LoginView extends VerticalLayout implements View {
    private Panel loginPanel = new Panel("Авторизация");
    private FormLayout loginLayout = new FormLayout();
    private TextField login = new TextField("Логин: ");
    private PasswordField password = new PasswordField("Пароль: ");
    private Button ok = new Button("Войти");
    private Button cancel = new Button("Отмена");
    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    @Inject
    private JaasAccessControl control;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        createLoginPanel();

        setSizeFull();
        setMargin(true);
        setSpacing(true);

        addComponent(loginPanel);

        setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
    }

    private void createLoginPanel() {
        loginPanel.setCaption("Авторизация");

        createLoginField();
        createPasswordFiled();
        createButtonsLayout();

        loginLayout.addComponent(login);
        loginLayout.addComponent(password);
        loginLayout.addComponent(buttonsLayout);
        loginLayout.setSizeUndefined();
        loginLayout.setSpacing(true);
        loginLayout.setMargin(true);

        loginPanel.setContent(loginLayout);
        loginPanel.setSizeUndefined();
    }

    private void createLoginField() {
        login.setIcon(FontAwesome.USER);
    }

    private void createPasswordFiled() {
        password.setIcon(FontAwesome.KEY);
    }

    private void createButtonsLayout() {
        createOkBtn();
        createCancelBtn();

        buttonsLayout.setSpacing(true);
        buttonsLayout.addComponent(ok);
        buttonsLayout.addComponent(cancel);
    }

    private void createOkBtn() {
        ok.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        ok.addClickListener(clickEvent -> {
            try {
                JaasAccessControl.login(login.getValue(), password.getValue());
                Notification.show("Добро пожаловать, " + login.getValue() + "!");
                getUI().getNavigator().navigateTo("View");
            } catch (Exception e) {
                Notification.show("Неверный логин или пароль!");
            }
        });
    }

    private void createCancelBtn() {
        cancel.addClickListener(clickEvent -> {
            login.clear();
            password.clear();
        });
    }
}
