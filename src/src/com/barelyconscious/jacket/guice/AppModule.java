package com.barelyconscious.jacket.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class AppModule extends AbstractModule {

    private final boolean isCursorVisible;

    public AppModule(final boolean isCursorVisible) {
        this.isCursorVisible = isCursorVisible;
    }

    @Provides
    @Singleton
    Screen providesScreen(Terminal terminal) {
        try {
            return new TerminalScreen(terminal);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Provides
    @Singleton
    Terminal providesTerminal() {
        try {
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            terminal.enterPrivateMode();
            terminal.clearScreen();
            terminal.setCursorVisible(false);
            return terminal;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
