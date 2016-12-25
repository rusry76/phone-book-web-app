package ru.social.client;

import com.vaadin.cdi.ViewScoped;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.inject.Inject;

@ViewScoped
public class DeleteWarningWindow extends Window {
    private Label warning = new Label();
    private Button ok = new Button("Да");
    private Button cancel = new Button("Отмена");

    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    @Inject
    private ContactForm contactForm;

    public DeleteWarningWindow() {

        createWarningLabel();
        createConfirmBtn();
        createCancelButton();
        createButtonsLayout();

        final VerticalLayout warningLayout = new VerticalLayout();
        warningLayout.setSizeFull();
        warningLayout.setMargin(true);
        warningLayout.addComponent(warning);
        warningLayout.addComponent(buttonsLayout);

        warningLayout.setComponentAlignment(warning, Alignment.MIDDLE_CENTER);
        warningLayout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);

        setContent(warningLayout);
    }

    private void createWarningLabel() {
        warning.addStyleName(ValoTheme.LABEL_FAILURE);
        warning.setSizeFull();
        warning.setValue("Вы пытаетесь удалить запись. Продолжить?");
    }

    private void createConfirmBtn() {
        ok.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        ok.addClickListener(clickEvent -> {
            contactForm.deleteHandler();
        });
    }

    private void createCancelButton() {
        cancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
        cancel.addClickListener(clickEvent -> {
            this.close();
        });
    }

    private void createButtonsLayout() {
        buttonsLayout.setSizeUndefined();
        buttonsLayout.setSpacing(true);
        buttonsLayout.addComponent(ok);
        buttonsLayout.addComponent(cancel);
    }
}
