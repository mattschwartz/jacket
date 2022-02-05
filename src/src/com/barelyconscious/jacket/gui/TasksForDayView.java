package com.barelyconscious.jacket.gui;

import com.barelyconscious.jacket.common.gfx.*;
import com.barelyconscious.jacket.common.gfx.components.*;
import com.barelyconscious.jacket.data.model.*;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;

import java.io.*;
import java.util.*;

import static com.barelyconscious.jacket.common.gfx.JacketColors.*;

public class TasksForDayView {
    public TasksForDayView() {
    }

    public void printPage(final JacketPage page) throws IOException {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            terminal.enterPrivateMode();
            terminal.setBackgroundColor(THEME_DARK_GREY);
            try (Screen screen = new TerminalScreen(terminal)) {
                screen.startScreen();
                screen.setCursorPosition(null);

                final var textGraphics = screen.newTextGraphics();
                textGraphics.setForegroundColor(THEME_BLUE);
                screen.clear();
                drawFrame(terminal, textGraphics);

                TextWindow.builder(textGraphics, terminal)
                    .title(" Viewing " + page.getDate() + " ")
                    .fullScreen(true)
                    .centered(true)
                    .draw();

                if (page.getTasks() == null) {
                    TextUtils.drawCenterString(
                        textGraphics,
                        new TerminalPosition(0, 0),
                        terminal.getTerminalSize().getColumns(),
                        terminal.getTerminalSize().getRows(),
                        " There are no tasks today. ",
                        THEME_WHITE
                    );
                } else {
                    var prevFgColor = textGraphics.getForegroundColor();
                    textGraphics.setForegroundColor(THEME_PALE_WHEAT);

                    var pos = new TerminalPosition(1, 1);
                    for (var task : page.getTasks()) {
                        TextUtils.drawCharacterWrappedString(
                            textGraphics, pos,
                            terminal.getTerminalSize().getColumns() - 3,
                            task.toString());
                        pos = pos.withRelativeRow(1);
                    }
                    textGraphics.setForegroundColor(prevFgColor);
                }

                AppUXHelper.drawAppInfo(textGraphics, terminal);
                AppUXHelper.drawTools(textGraphics, terminal, new HashMap<>() {{
                    put("e", "edit");
                    put("h", "help");
                    put("q", "quit");
                }});

                screen.refresh();
                screen.readInput();
            }
        }
    }

    private void drawFrame(final Terminal terminal, final TextGraphics textGraphics) throws IOException {
        final var terminalSize = terminal.getTerminalSize();

        final var startPos = new TerminalPosition(0, 0);
        final var width = terminalSize.getColumns() - 2;
        final var height = terminalSize.getRows() - 1;

        ShapeUtils.drawBox(
            textGraphics,
            startPos,
            width,
            height);
    }
}
