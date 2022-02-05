package com.barelyconscious.jacket.common.gfx.components;


import com.barelyconscious.jacket.common.gfx.*;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.*;
import com.googlecode.lanterna.terminal.*;
import lombok.*;

import java.io.*;

import static com.barelyconscious.jacket.common.gfx.JacketColors.THEME_ORANGE;

@Builder
public class TextWindow {

    private final String title;
    private final String content;
    private final TerminalPosition startingAtPos;
    private final int windowWidth;
    private final int windowHeight;
    private final boolean isFullScreen;
    private final boolean isCentered;

    private final TextGraphics textGraphics;
    private final Terminal terminal;

    public void draw() throws IOException {
        TerminalPosition startPos;
        final int windowWidth;
        final int windowHeight;
        final TerminalSize termSize;

        if (isFullScreen) {
            termSize = terminal.getTerminalSize();
            startPos = new TerminalPosition(0, 0);
            windowWidth = termSize.getColumns();
            windowHeight = termSize.getRows();
        } else if (startingAtPos != null) {
            startPos = startingAtPos;
            windowWidth = this.windowWidth;
            windowHeight = this.windowHeight;
        } else {
            throw new RuntimeException("either isFullScreen or startingAtPos must be specified");
        }

        ShapeUtils.drawBox(
            textGraphics,
            startPos,
            windowWidth,
            windowHeight);

        if (title != null) {
            if (isCentered) {
                TextUtils.drawCenterString(
                    textGraphics,
                    startPos,
                    windowWidth,
                    title);
            } else {
                textGraphics.putString(
                    startPos,
                    title);
            }
        }
        if (content != null) {
            var prevBgColor = textGraphics.getBackgroundColor();
            textGraphics.setBackgroundColor(THEME_ORANGE);

            var contentStart = startPos.withRelativeColumn(1).withRelativeRow(1);
            var lines = content.split("\n");

            for (final var line : lines) {
                if (isCentered) {
                    TextUtils.drawCenterString(
                        textGraphics,
                        contentStart,
                        windowWidth,
                        line);
                } else {
                    textGraphics.putString(contentStart, line);
                }
                contentStart = contentStart.withRelativeRow(1);
            }
            textGraphics.setBackgroundColor(prevBgColor);
        }
    }
}
