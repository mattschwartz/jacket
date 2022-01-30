package com.barelyconscious.jacket.gui;

import com.barelyconscious.jacket.data.JacketDAL;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class PageBrowserWindow extends BasicWindow {

    private final JacketDAL dal;

    private ComboBox<Integer> yearSelector;
    private ComboBox<Integer> monthSelector;
    private ComboBox<Integer> daySelector;

    public PageBrowserWindow(final TerminalSize initialSize, final JacketDAL dal) {
        super("Page Browser");
        checkArgument(dal != null, "dal is null");
        this.dal = dal;

        selectedYear = dal.listYears().get(0);
        selectedMonth = dal.listMonthsForYear(selectedYear).get(0);
        selectedDay = dal.listDaysForMonthAndYear(selectedYear, selectedMonth).get(0);

        Button button = new Button("Open Page");

        Panel tabbedPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        tabbedPanel.addComponent(yearPanel());
        tabbedPanel.addComponent(monthPanel());
        tabbedPanel.addComponent(dayPanel());

        Panel bottomPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        bottomPanel.addComponent(button);

        Panel content = new Panel(new LinearLayout(Direction.VERTICAL));
        content.addComponent(tabbedPanel);
        content.addComponent(bottomPanel);

        setComponent(content);
        setFixedSize(initialSize);
    }

    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;

    private void onYearChanged(int selectedIndex, int previousSelection, boolean changedByUserInteraction) {
        if (!changedByUserInteraction) {
            return;
        }
        selectedYear = dal.listYears().get(selectedIndex);
        selectedMonth = dal.listMonthsForYear(selectedYear).get(0);
        selectedDay = dal.listDaysForMonthAndYear(selectedYear, selectedMonth).get(0);

        monthSelector.clearItems();
        dal.listMonthsForYear(selectedYear).forEach(t -> monthSelector.addItem(t));
        monthSelector.setSelectedIndex(0);

        daySelector.clearItems();
        dal.listDaysForMonthAndYear(selectedYear, selectedMonth).forEach(t -> daySelector.addItem(t));
        daySelector.setSelectedIndex(0);
    }

    private void onMonthChanged(int selectedIndex, int previousSelection, boolean changedByUserInteraction) {
        if (!changedByUserInteraction) {
            return;
        }
        selectedMonth = dal.listMonthsForYear(selectedYear).get(selectedIndex);
        selectedDay = dal.listDaysForMonthAndYear(selectedYear, selectedMonth).get(0);

        daySelector.clearItems();
        dal.listDaysForMonthAndYear(selectedYear, selectedMonth).forEach(t -> daySelector.addItem(t));
        daySelector.setSelectedIndex(0);
    }

    private void onDayChanged(int selectedIndex, int previousSelection, boolean changedByUserInteraction) {
        if (!changedByUserInteraction) {
            return;
        }
        selectedDay = dal.listDaysForMonthAndYear(selectedYear, selectedMonth).get(selectedIndex);
        updateSelection();
    }

    private Panel yearPanel() {
        Panel res = new Panel();
        yearSelector = new ComboBox<>(dal.listYears())
            .addListener(this::onYearChanged)
            .setReadOnly(true)
            .setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1));

        res.addComponent(yearSelector);

        return res;
    }

    private Panel monthPanel() {
        Panel res = new Panel();
        List<Integer> months = dal.listMonthsForYear(selectedYear);
        System.out.println("Months: " + months + " for year: " + selectedYear);

        monthSelector = new ComboBox<>(dal.listMonthsForYear(this.selectedYear))
            .addListener(this::onMonthChanged)
            .setReadOnly(true)
            .setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1));

        res.addComponent(monthSelector);

        return res;

    }

    private Panel dayPanel() {
        Panel res = new Panel();
        daySelector = new ComboBox<>(dal.listDaysForMonthAndYear(selectedYear, selectedMonth))
            .addListener(this::onDayChanged)
            .setReadOnly(true)
            .setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1));

        res.addComponent(daySelector);

        return res;
    }

    private void updateSelection() {
        System.out.println("Updated year: " + selectedYear + " update month: " + selectedMonth + " updated day: "+ selectedDay);

        yearSelector.setSelectedItem(selectedYear);
        monthSelector.setSelectedItem(selectedMonth);
        daySelector.setSelectedItem(selectedDay);


    }
}
