package com.barelyconscious.jacket.gui;

import com.barelyconscious.jacket.data.model.*;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;

import java.io.*;

public class TasksForDayView {
    public TasksForDayView() {
    }

    public void printPage(final JacketPage page) throws IOException {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            try (Screen screen = new TerminalScreen(terminal)) {
                screen.startScreen();
                screen.setCursorPosition(null);

                final var textGraphics = screen.newTextGraphics();
                textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
                textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
                drawFrame(terminal, textGraphics);

                var pos = new TerminalPosition(1, 1);
                final int numTasks;
                if (page.getTasks() == null) {
                    numTasks = 0;
                } else {
                    numTasks = page.getTasks().size();
                }

                textGraphics.putString(pos, page.getDate().toString());
                pos = pos.withRelativeRow(1).withColumn(1);
                textGraphics.putString(pos, "Tasks: " + numTasks);
                pos = pos.withRelativeRow(1).withColumn(1);
                textGraphics.putString(pos, "=======");
                pos = pos.withRelativeRow(1).withColumn(1);

                if (page.getTasks() != null) {
                    for (var task : page.getTasks()) {
                        textGraphics.putString(pos, task.toString());
                        pos = pos.withRelativeRow(1).withColumn(1);
                    }
                }

                textGraphics.putString(pos, "=======");

                screen.refresh();
                screen.readInput();
            }
        }
    }

    private void drawFrame(final Terminal terminal, final TextGraphics textGraphics) throws IOException {
        TerminalPosition labelBoxTopLeft = new TerminalPosition(0, 0);
        TerminalSize labelBoxSize = terminal.getTerminalSize()
            .withRelativeColumns(-2)
            .withRelativeRows(-2);

//        textGraphics.fillRectangle(labelBoxTopLeft, labelBoxSize, ' ');
        textGraphics.drawLine(
            labelBoxTopLeft,
            labelBoxTopLeft,
            Symbols.DOUBLE_LINE_HORIZONTAL);
        textGraphics.drawLine(
            labelBoxTopLeft.withRelativeRow(2).withRelativeColumn(1),
            labelBoxTopLeft.withRelativeRow(2).withRelativeColumn(labelBoxSize.getColumns() - 2),
            Symbols.DOUBLE_LINE_HORIZONTAL);
    }
}
