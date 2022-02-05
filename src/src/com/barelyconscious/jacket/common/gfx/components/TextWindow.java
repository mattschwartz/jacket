package com.barelyconscious.jacket.common.gfx.components;


import com.barelyconscious.jacket.common.gfx.*;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.*;
import com.googlecode.lanterna.terminal.*;

import java.io.*;
import java.util.*;

public class TextWindow {

    private final String title;
    private final TerminalPosition startingAtPos;
    private final int windowWidth;
    private final int windowHeight;
    private final boolean isFullScreen;
    private final boolean isCentered;

    private final TextGraphics textGraphics;
    private final Terminal terminal;

    public TextWindow(
        final String title,
        final TerminalPosition startingAtPos,
        final int windowWidth,
        final int windowHeight,
        final boolean isFullScreen,
        final boolean isCentered,
        final TextGraphics textGraphics,
        final Terminal terminal
    ) throws IOException {
        this.isFullScreen = isFullScreen;
        this.isCentered = isCentered;
        this.textGraphics = textGraphics;
        this.terminal = terminal;
        this.title = Objects.requireNonNullElse(title, "");

        TerminalPosition startPos;
        if (isFullScreen) {
            final var termPos = terminal.getTerminalSize();
            startPos = new TerminalPosition(0, 0);
            this.windowWidth = termPos.getColumns();
            this.windowHeight = termPos.getRows();
        } else if (startingAtPos != null) {
            startPos = startingAtPos;
            this.windowWidth = windowWidth;
            this.windowHeight = windowHeight;
        } else {
            throw new RuntimeException("either isFullScreen or startingAtPos must be specified");
        }

        this.startingAtPos = startPos;
    }

    public void draw() {
        ShapeUtils.drawBox(
            textGraphics,
            startingAtPos,
            windowWidth,
            windowHeight);

        if (isCentered) {
            TextUtils.drawCenterString(
                textGraphics,
                startingAtPos,
                windowWidth,
                title);
        } else {
            textGraphics.putString(
                startingAtPos,
                title);
        }
    }

    public static class TextWindowBuilder {

        private String title;
        private TerminalPosition startingAtPos;
        private int windowWidth;
        private int windowHeight;
        private boolean isFullScreen;
        private boolean isCentered;

        public TextWindowBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public TextWindowBuilder fullScreen(final boolean isFullScreen) {
            this.isFullScreen = isFullScreen;
            return this;
        }

        public TextWindowBuilder centered(final boolean isCentered) {
            this.isCentered = isCentered;
            return this;
        }

        public TextWindowBuilder startingAt(final TerminalPosition position) {
            this.startingAtPos = position;
            return this;
        }

        public TextWindowBuilder windowWidth(final int width) {
            this.windowWidth = width;
            return this;
        }

        public TextWindowBuilder windowHeight(final int height) {
            this.windowHeight = height;
            return this;
        }

        public static TextWindowBuilder builder(final TextGraphics textGraphics, final Terminal terminal) {
            return new TextWindowBuilder(textGraphics, terminal);
        }

        public void draw() throws IOException {
            new TextWindow(
                this.title,
                this.startingAtPos,
                this.windowWidth,
                this.windowHeight,
                this.isFullScreen,
                this.isCentered,
                this.textGraphics,
                this.terminal
            ).draw();
        }

        private final TextGraphics textGraphics;
        private final Terminal terminal;

        private TextWindowBuilder(final TextGraphics textGraphics, final Terminal terminal) {
            this.textGraphics = textGraphics;
            this.terminal = terminal;
        }
    }

    public static TextWindow.TextWindowBuilder builder(final TextGraphics textGraphics, final Terminal terminal) {
        return TextWindow.TextWindowBuilder.builder(textGraphics, terminal);
    }
}
