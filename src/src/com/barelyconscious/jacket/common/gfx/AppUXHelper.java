package com.barelyconscious.jacket.common.gfx;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.*;
import com.googlecode.lanterna.terminal.*;

import java.io.*;
import java.util.*;

public final class AppUXHelper {

    public static void drawTools(
        final TextGraphics textGraphics,
        final Terminal terminal,
        final Map<String, String> toolsDescriptions
    ) throws IOException {
        var sb = new StringBuilder();

        for (final var tool : toolsDescriptions.entrySet()) {
            var commandName = tool.getKey();
            var description = tool.getValue();

            sb.append(" ")
                .append(commandName)
                .append(": ")
                .append(description)
                .append(" ");
        }

        var startPos = new TerminalPosition(
            2,
            terminal.getTerminalSize().getRows()-1);

        var toolbeltStr = sb.toString();

        textGraphics.setCharacter(
            startPos,
            Symbols.DOUBLE_LINE_T_LEFT);

        textGraphics.setCharacter(
            startPos.withRelativeColumn(toolbeltStr.length() + 1),
            Symbols.DOUBLE_LINE_T_RIGHT);

        startPos = startPos.withRelativeColumn(1);
        var prevFgColor = textGraphics.getForegroundColor();

        textGraphics.putString(startPos, toolbeltStr);
        textGraphics.setForegroundColor(prevFgColor);
    }

    public static void drawAppInfo(
        final TextGraphics textGraphics,
        final Terminal terminal
    ) throws IOException {
        final var text = " jacket v0.1 ";
        final var termSize = terminal.getTerminalSize();
        final var startPos = new TerminalPosition(
            termSize.getColumns() - text.length() - 2,
            termSize.getRows() - 1);

        textGraphics.putString(
            startPos,
            text);
    }
}
