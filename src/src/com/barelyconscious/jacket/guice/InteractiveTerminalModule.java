package com.barelyconscious.jacket.guice;

import com.google.inject.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;

import java.io.*;

public class InteractiveTerminalModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        install(new AppModule());
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
