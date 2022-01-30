package com.barelyconscious.jacket;

import com.barelyconscious.jacket.data.impl.FileSystemJacketDAL;
import com.barelyconscious.jacket.gui.ExampleWindow;
import com.barelyconscious.jacket.gui.ExampleWindow2;
import com.barelyconscious.jacket.gui.PageBrowserWindow;
import com.barelyconscious.jacket.guice.AppModule;
import com.google.inject.Injector;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import static com.google.inject.Guice.createInjector;

@Log4j2
public class Main {

    public static void main(String[] args) {
        Injector injector = createInjector(new AppModule(false));
        try (Terminal terminal = injector.getInstance(Terminal.class)) {
            try (Screen screen = new TerminalScreen(terminal)) {
                screen.startScreen();

                final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
//                textGUI.setTheme(customTheme());
                textGUI.addWindowAndWait(new PageBrowserWindow(screen.getTerminalSize().withRows(3).withRelativeColumns(-6), new FileSystemJacketDAL()));
//                textGUI.addWindowAndWait(new ExampleWindow().DoThing(screen, textGUI));
            } catch (IOException e) {
                log.error(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Theme customTheme() {
        boolean activeIsBold = false;
        TextColor baseForeground = TextColor.ANSI.BLACK;
        TextColor baseBackground = TextColor.ANSI.GREEN;
        TextColor editableForeground = TextColor.ANSI.WHITE;
        TextColor editableBackground = TextColor.ANSI.BLACK;
        TextColor selectedForeground = TextColor.ANSI.GREEN;
        TextColor selectedBackground = TextColor.ANSI.BLACK;
        TextColor guiBackground = TextColor.ANSI.BLACK;

        return SimpleTheme.makeTheme(
            activeIsBold,
            baseForeground,
            baseBackground,
            editableForeground,
            editableBackground,
            selectedForeground,
            selectedBackground,
            guiBackground
        );
    }

    private static void doRandomColor(Terminal terminal, Screen screen) throws IOException {
        screen.setCursorPosition(null);
        Random random = new Random();
        TerminalSize terminalSize = screen.getTerminalSize();
        for (int column = 0; column < terminalSize.getColumns(); column++) {
            for (int row = 0; row < terminalSize.getRows(); row++) {
                screen.setCharacter(column, row, new TextCharacter(
                    ' ',
                    TextColor.ANSI.DEFAULT,
                    // This will pick a random background color
                    TextColor.ANSI.values()[random.nextInt(TextColor.ANSI.values().length)]));
            }
        }

        while (true) {
            screen.refresh();
            KeyStroke keyStroke = screen.pollInput();
            if (keyStroke != null && (keyStroke.getKeyType() == KeyType.Escape || keyStroke.getKeyType() == KeyType.EOF)) {
                return;
            }
            TerminalSize newSize = screen.doResizeIfNecessary();
            if (newSize != null) {
                terminalSize = newSize;
            }

            // Increase this to increase speed
            final int charactersToModifyPerLoop = 1;
            for (int i = 0; i < charactersToModifyPerLoop; i++) {
                TerminalPosition cellToModify = new TerminalPosition(
                    random.nextInt(terminalSize.getColumns()),
                    random.nextInt(terminalSize.getRows()));
                TextColor.ANSI color = TextColor.ANSI.values()[random.nextInt(TextColor.ANSI.values().length)];

                TextCharacter characterInBackBuffer = screen.getBackCharacter(cellToModify);
                characterInBackBuffer = characterInBackBuffer.withBackgroundColor(color);
                characterInBackBuffer = characterInBackBuffer.withCharacter(' ');   // Because of the label box further down, if it shrinks
                screen.setCharacter(cellToModify, characterInBackBuffer);
            }

            String sizeLabel = "Terminal Size: " + terminalSize;
            TerminalPosition labelBoxTopLeft = new TerminalPosition(1, 1);
            TerminalSize labelBoxSize = new TerminalSize(sizeLabel.length() + 2, 3);
            TerminalPosition labelBoxTopRightCorner = labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 1);
            TextGraphics textGraphics = screen.newTextGraphics();
            //This isn't really needed as we are overwriting everything below anyway, but just for demonstrative purpose
            textGraphics.fillRectangle(labelBoxTopLeft, labelBoxSize, ' ');

            textGraphics.drawLine(
                labelBoxTopLeft.withRelativeColumn(1),
                labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 2),
                Symbols.DOUBLE_LINE_HORIZONTAL);
            textGraphics.drawLine(
                labelBoxTopLeft.withRelativeRow(2).withRelativeColumn(1),
                labelBoxTopLeft.withRelativeRow(2).withRelativeColumn(labelBoxSize.getColumns() - 2),
                Symbols.DOUBLE_LINE_HORIZONTAL);

            textGraphics.setCharacter(labelBoxTopLeft, Symbols.DOUBLE_LINE_TOP_LEFT_CORNER);
            textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(1), Symbols.DOUBLE_LINE_VERTICAL);
            textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(2), Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER);
            textGraphics.setCharacter(labelBoxTopRightCorner, Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER);
            textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(1), Symbols.DOUBLE_LINE_VERTICAL);
            textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(2), Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);

            textGraphics.putString(labelBoxTopLeft.withRelative(1, 1), sizeLabel);

            screen.refresh();
            Thread.yield();
        }
    }
}
