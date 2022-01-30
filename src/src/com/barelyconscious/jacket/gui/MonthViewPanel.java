package com.barelyconscious.jacket.gui;

import com.googlecode.lanterna.gui2.*;

import java.util.HashMap;
import java.util.Map;

public class MonthViewPanel extends Panel {

    private final Map<Integer, String> notesByDay = new HashMap<>();

    public MonthViewPanel() {
        super(new GridLayout(2));

        notesByDay.put(1, null);
        notesByDay.put(2, null);
        notesByDay.put(3, null);
        notesByDay.put(4, null);
        notesByDay.put(5, null);
        notesByDay.put(6, "Appointment with doc");
        notesByDay.put(7, null);
        notesByDay.put(8, null);
        notesByDay.put(9, null);
        notesByDay.put(10, null);
        notesByDay.put(11, null);
        notesByDay.put(12, "Someone's birthday");
        notesByDay.put(13, null);
        notesByDay.put(14, null);
        notesByDay.put(15, null);
        notesByDay.put(16, null);
        notesByDay.put(17, null);
        notesByDay.put(18, null);
        notesByDay.put(19, "Kill time for no reason");
        notesByDay.put(20, "Do things you were supposed to do yesterday");
        notesByDay.put(21, null);
        notesByDay.put(22, null);
        notesByDay.put(23, null);
        notesByDay.put(24, null);
        notesByDay.put(25, null);
        notesByDay.put(26, null);
        notesByDay.put(27, null);
        notesByDay.put(28, null);
        notesByDay.put(29, null);
        notesByDay.put(30, null);
        notesByDay.put(31, null);

        addMonthNotes();
    }

    private void addMonthNotes() {
        addComponent(new TextBox("Month of January")
            .setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        for (final Map.Entry<Integer, String> entry : notesByDay.entrySet()) {
            addComponent(new TextBox(entry.getKey().toString()));
            if (entry.getValue() != null) {
                addComponent(new TextBox(entry.getValue()))
                    .setLayoutData(GridLayout.createHorizontallyFilledLayoutData());
            } else {
                addComponent(new TextBox("-"))
                    .setLayoutData(GridLayout.createHorizontallyFilledLayoutData());
            }
        }
    }
}
