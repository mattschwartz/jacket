package com.barelyconscious.jacket.common.gfx;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.*;

import java.util.*;

import static com.google.common.base.Preconditions.*;

public final class TextUtils {

    public static void drawCenterString(
        final TextGraphics textGraphics,
        final TerminalPosition startPos,
        final int width,
        final String text
    ) {
        checkArgument(text.length() <= width, "text is longer than width");

        var textStart = startPos.withRelativeColumn((width - text.length()) / 2);
        textGraphics.putString(textStart, text);
    }

    public static void drawCenterString(
        final TextGraphics textGraphics,
        final TerminalPosition startPos,
        final int width,
        final int height,
        final String text
    ) {
        drawCenterString(
            textGraphics,
            startPos,
            width,
            height,
            text,
            textGraphics.getForegroundColor());
    }

    public static void drawCenterString(
        final TextGraphics textGraphics,
        final TerminalPosition startPos,
        final int width,
        final int height,
        final String text,
        final TextColor fgColor
    ) {
        checkArgument(text.length() <= width, "text is longer than width");

        final var prevFgColor = textGraphics.getForegroundColor();
        final var prevBgColor = textGraphics.getBackgroundColor();

        textGraphics.setForegroundColor(fgColor);
        var textStart = startPos
            .withRelativeColumn((width - text.length()) / 2)
            .withRelativeRow(height / 2);
        textGraphics.putString(textStart, text);

        textGraphics.setForegroundColor(prevFgColor);
        textGraphics.setBackgroundColor(prevBgColor);
    }

    public static void drawCharacterWrappedString(
        final TextGraphics textGraphics,
        final TerminalPosition startpos,
        final int wrapWidth,
        final String str
    ) {
        var lines = wrapCharacters(str, wrapWidth, false);

        var pos = startpos;
        for (final String line : lines) {
            textGraphics.putString(pos, line);
            pos = pos.withRelativeRow(1);
        }
    }

    public static List<String> wrapCharacters(
        final String str,
        final int width,
        final boolean truncate
    ) {
        final Iterable<String> spliterator;
        if (truncate) {
            spliterator = Splitter.fixedLength(width)
                .split(str.substring(0, Math.min(str.length(), width)));
        } else {
            spliterator = Splitter.fixedLength(width).split(str);
        }

        return Lists.newArrayList(spliterator);
    }

    private TextUtils() {
    }
}
