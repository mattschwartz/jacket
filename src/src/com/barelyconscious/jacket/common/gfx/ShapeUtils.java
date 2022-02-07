package com.barelyconscious.jacket.common.gfx;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.*;

public final class ShapeUtils {


    public static void drawBox(
        final TextGraphics textGraphics,
        final TerminalPosition startPos,
        final int width,
        final int height
    ) {
        drawBox(textGraphics, startPos, width, height,
            Symbols.DOUBLE_LINE_HORIZONTAL, Symbols.DOUBLE_LINE_VERTICAL);
    }

    public static void drawBox(
        final TextGraphics textGraphics,
        final TerminalPosition startPos,
        final int width,
        final int height,
        final char horizontalChar,
        final char verticalChar
    ) {
        // Draw top/bottom horizontal bars
        {
            final TerminalPosition topBarEndPos = startPos
                .withRelativeColumn(width);

            final TerminalPosition bottomBarStartPos = startPos
                .withRelativeRow(height);
            final TerminalPosition bottomBarEndPos = startPos
                .withRelativeRow(height)
                .withRelativeColumn(width);

            // Draw top bar
            textGraphics.drawLine(startPos, topBarEndPos, horizontalChar);
            // Draw bottom bar
            textGraphics.drawLine(bottomBarStartPos, bottomBarEndPos, horizontalChar);
        }

        // Drop left/right vertical bars
        {
            var topLeftPos = startPos;
            var topRightPos = startPos.withRelativeColumn(width);

            for (int x = topLeftPos.getRow(); x < startPos.getRow() + height; ++x) {
                topLeftPos = topLeftPos.withRelativeRow(1);
                topRightPos = topRightPos.withRelativeRow(1);

                textGraphics.setCharacter(topLeftPos, verticalChar);
                textGraphics.setCharacter(topRightPos, verticalChar);
            }
        }

        // Draw Corners
        {
            textGraphics.setCharacter(
                startPos,
                Symbols.DOUBLE_LINE_TOP_LEFT_CORNER);
            textGraphics.setCharacter(
                startPos.withRelativeColumn(width),
                Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER);

            textGraphics.setCharacter(
                startPos.withRelativeRow(height),
                Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER);
            textGraphics.setCharacter(
                startPos.withRelativeColumn(width).withRelativeRow(height),
                Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);

        }
    }

    private ShapeUtils() {
    }
}
