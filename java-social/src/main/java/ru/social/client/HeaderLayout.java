package ru.social.client;

import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.servlet.ServletException;


public class HeaderLayout extends HorizontalLayout {
    private Button logoutBtn = new Button("Logout");

    public HeaderLayout() {
        initLogoutBtn();
        initHeaderLayout();
    }

    private void initLogoutBtn() {
        logoutBtn.setStyleName(ValoTheme.BUTTON_LINK);
        logoutBtn.addClickListener(clickEvent -> {
            try {
                JaasAccessControl.logout();
                getUI().getPage().reload();
            } catch (ServletException e) {
                e.printStackTrace();
            }
        });
    }

    private void initHeaderLayout() {
        addComponent(logoutBtn);
        setSpacing(true);
        setWidth("100%");
        setHeightUndefined();

        setAlignment();
    }

    private void setAlignment() {
        setComponentAlignment(logoutBtn, Alignment.MIDDLE_RIGHT);
    }
}
