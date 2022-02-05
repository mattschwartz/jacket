package com.barelyconscious.jacket.common.gfx;

import com.googlecode.lanterna.*;

public final class JacketColors {

    public static final TextColor.RGB THEME_ORANGE;
    public static final TextColor.RGB THEME_BLUE;
    public static final TextColor.RGB THEME_PALE_WHEAT;
    public static final TextColor.RGB THEME_DARK_GREY;
    public static final TextColor.RGB THEME_WHITE;

    static {
        THEME_ORANGE = new TextColor.RGB(200, 75, 49);
        THEME_BLUE = new TextColor.RGB(70, 91, 124);
        THEME_PALE_WHEAT = new TextColor.RGB(236, 219, 186);
        THEME_DARK_GREY = new TextColor.RGB(25, 25, 25);
        THEME_WHITE = new TextColor.RGB(142, 131, 112);
    }

    private JacketColors(){}
}
