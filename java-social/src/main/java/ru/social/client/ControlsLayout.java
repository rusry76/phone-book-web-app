package ru.social.client;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class ControlsLayout extends HorizontalLayout {

    private Label resultsPerPageLabel = new Label("Items per page:");
    private ComboBox itemsPerPageSelect = new ComboBox();
    private Label pageLabel = new Label("Page:&nbsp;", ContentMode.HTML);
    private Button firstBtn = new Button("<<");
    private Button previousBtn = new Button("<");
    private Button nextBtn = new Button(">");
    private Button lastBtn = new Button(">>");
    private final Label currentPageLabel = new Label();
    private final Label separatorLabel = new Label("&nbsp;/&nbsp;", ContentMode.HTML);
    private final Label totalPagesLabel = new Label();

    public ControlsLayout() {
        initResultsPerPageLabel();
        initComboBox();
        initFirstBtn();
        initPreviousBtn();
        initPageLabel();
        initCurrentPageLabel();
        initSeparatorLabel();
        initTotalPagesLabel();
        initNextBtn();
        initLastBtn();

        HorizontalLayout pageSize = new HorizontalLayout();
        pageSize.setSpacing(true);

        HorizontalLayout pageManagement = new HorizontalLayout();
        pageManagement.setSpacing(false);

        pageSize.addComponent(resultsPerPageLabel);
        pageSize.addComponent(itemsPerPageSelect);
        pageManagement.addComponent(firstBtn);
        pageManagement.addComponent(previousBtn);
        pageManagement.addComponent(pageLabel);
        pageManagement.addComponent(currentPageLabel);
        pageManagement.addComponent(separatorLabel);
        pageManagement.addComponent(totalPagesLabel);
        pageManagement.addComponent(nextBtn);
        pageManagement.addComponent(lastBtn);

        addComponents(pageSize, pageManagement);
    }

    private void initResultsPerPageLabel() {
        resultsPerPageLabel.setStyleName(ValoTheme.LABEL_LARGE);
    }

    private void initComboBox() {
        itemsPerPageSelect.addItem(5);
        itemsPerPageSelect.addItem(10);
        itemsPerPageSelect.addItem(25);
        itemsPerPageSelect.addItem(50);
        itemsPerPageSelect.addItem(100);

        itemsPerPageSelect.setNullSelectionAllowed(false);
        itemsPerPageSelect.setWidth("100px");
        itemsPerPageSelect.select(10);

        resultsPerPageLabel.addValueChangeListener(valueChangeEvent -> {
            //table.update(valueChangeEvent.getProperty().getValue());
            Notification.show(valueChangeEvent.toString());
        });
    }

    private void initPreviousBtn() {
        previousBtn.setStyleName(ValoTheme.BUTTON_LINK);
        previousBtn.addClickListener(clickEvent -> {
            Notification.show("Previous button clicked!");
        });
    }

    private void initFirstBtn() {
        firstBtn.setStyleName(ValoTheme.BUTTON_LINK);
        firstBtn.addClickListener(clickEvent -> {
            Notification.show("First button clicked!");
        });
    }

    private void initNextBtn() {
        nextBtn.setStyleName(ValoTheme.BUTTON_LINK);
        nextBtn.addClickListener(clickEvent -> {

        });
    }

    private void initLastBtn() {
        lastBtn.setStyleName(ValoTheme.BUTTON_LINK);
        lastBtn.addClickListener(clickEvent -> {
            Notification.show("Last button clicked!");
        });
    }

    private void initPageLabel() {
        pageLabel.setStyleName(ValoTheme.LABEL_LARGE);
    }

    private void initCurrentPageLabel() {
        currentPageLabel.setStyleName(ValoTheme.LABEL_LARGE);
        currentPageLabel.setValue("1");
        currentPageLabel.setWidth(null);

        currentPageLabel.addValueChangeListener(valueChangeEvent -> {
            Notification.show(valueChangeEvent.toString());
        });
    }

    private void initSeparatorLabel() {
        separatorLabel.setStyleName(ValoTheme.LABEL_LARGE);
        separatorLabel.setWidth(null);
    }

    private void initTotalPagesLabel() {
        totalPagesLabel.setStyleName(ValoTheme.LABEL_LARGE);
        totalPagesLabel.setValue("5");
        totalPagesLabel.setWidth(null);

        totalPagesLabel.addValueChangeListener(valueChangeEvent -> {
            Notification.show(valueChangeEvent.toString());
        });
    }


}
