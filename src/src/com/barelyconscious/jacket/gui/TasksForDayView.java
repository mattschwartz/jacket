package com.barelyconscious.jacket.gui;

import com.barelyconscious.jacket.common.gfx.*;
import com.barelyconscious.jacket.common.gfx.components.*;
import com.barelyconscious.jacket.data.*;
import com.barelyconscious.jacket.data.model.*;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;

import java.io.*;
import java.time.*;
import java.util.*;

import static com.barelyconscious.jacket.common.gfx.JacketColors.*;
import static com.google.common.base.Preconditions.*;

public class TasksForDayView {

    private final JacketDAL jacketDAL;

    private final char YEAR_BWD = 'Y';
    private final char YEAR_FWD = 'y';

    private final char MONTH_BWD = 'M';
    private final char MONTH_FWD = 'm';

    private final char DAY_BWD = 'D';
    private final char DAY_FWD = 'd';

    private final char QUIT = 'q';
    private final char EDIT = 'e';
    private final char HELP = 'h';

    private boolean isHelpVisible = true;

    public TasksForDayView(final JacketDAL jacketDAL) {
        checkArgument(jacketDAL != null, "jacketDAL is null");
        this.jacketDAL = jacketDAL;
    }

    public void startOnPage(final LocalDate localDate) throws IOException {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            terminal.enterPrivateMode();
            terminal.setBackgroundColor(THEME_DARK_GREY);
            try (Screen screen = new TerminalScreen(terminal)) {
                screen.startScreen();
                screen.setCursorPosition(null);

                var datePointer = localDate;

                while (true) {
                    final var page = getPageDefaultToday(datePointer);
                    renderPage(screen, terminal, page);

                    final KeyStroke keyStroke = screen.readInput();

                    if (keyStroke.getKeyType() == KeyType.EOF) {
                        break;
                    }

                    if (keyStroke.getCharacter() == null) {
                        System.out.println("Ignoring: " + keyStroke);
                        continue;
                    }

                    datePointer = switch (keyStroke.getCharacter()) {
                        case DAY_FWD -> datePointer.plusDays(1);
                        case DAY_BWD -> datePointer.plusDays(-1);
                        case MONTH_FWD -> datePointer.plusMonths(1);
                        case MONTH_BWD -> datePointer.plusMonths(-1);
                        case YEAR_FWD -> datePointer.plusYears(1);
                        case YEAR_BWD -> datePointer.plusYears(-1);
                        default -> datePointer;
                    };
                    if (keyStroke.getCharacter() == QUIT) {
                        break;
                    }
                    if (keyStroke.getCharacter() == EDIT) {
                        System.out.println("Edit pressed");
                    }
                    if (keyStroke.getCharacter() == HELP) {
                        isHelpVisible = !isHelpVisible;
                    }
                }
            }
        }
    }

    private JacketPage getPageDefaultToday(final LocalDate date) {
        final var page = jacketDAL.getJacketPage(date);
        if (page == null) {
            return new JacketPage(date, new ArrayList<>());
        }
        return page;
    }

    private void renderPage(
        final Screen screen,
        final Terminal terminal,
        final JacketPage page
    ) throws IOException {
        final var textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(THEME_BLUE);
        screen.clear();
        final var date = page.getDate();

        TextWindow.builder()
            .textGraphics(textGraphics)
            .terminal(terminal)
            .title(" Viewing " + date + " ")
            .isFullScreen(true)
            .isCentered(true)
            .build()
            .draw();

        if (page.getTasks() == null || page.getTasks().isEmpty()) {
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
                var sb = new StringBuilder();

                sb.append(task.getTaskSymbol())
                    .append("\t")
                    .append(task.getText());

                TextUtils.drawCharacterWrappedString(
                    textGraphics, pos,
                    terminal.getTerminalSize().getColumns() - 3,
                    sb.toString());
                pos = pos.withRelativeRow(1);
            }
            textGraphics.setForegroundColor(prevFgColor);
        }

        AppUXHelper.drawAppInfo(textGraphics, terminal);
        if (isHelpVisible) {
            var sb = new StringBuilder();
            var helpString = sb
                .append(DAY_FWD).append(": ")
                .append("next day ")
                .append(DAY_BWD).append(": ")
                .append("prev day ")
                .append(MONTH_FWD).append(": ")
                .append("next month ")
                .append(MONTH_BWD).append(": ")
                .append("prev month\n")
                .append(YEAR_FWD).append(": ")
                .append("next year ")
                .append(YEAR_BWD).append(": ")
                .append("prev year")
                .append("h: close")
                .toString();
            var termSize = terminal.getTerminalSize();
            TextWindow.builder()
                .textGraphics(textGraphics)
                .terminal(terminal)
                .startingAtPos(new TerminalPosition(
                    0,
                    termSize.getRows() - 4
                ))
                .windowWidth(termSize.getColumns() - 1)
                .windowHeight(3)
                .title(" extended help ")
                .isCentered(true)
                .content(helpString)
                .build()
                .draw();
        } else {
            AppUXHelper.drawTools(textGraphics, terminal, new HashMap<>() {{
                put("e", "edit");
                put("h", "toggle help");
                put("q", "quit");
            }});
        }

        screen.refresh();
    }
}
