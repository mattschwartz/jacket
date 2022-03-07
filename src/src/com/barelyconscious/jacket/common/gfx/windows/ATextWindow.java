package com.barelyconscious.jacket.common.gfx.windows;

import com.googlecode.lanterna.graphics.*;
import com.googlecode.lanterna.terminal.*;
import lombok.experimental.*;

import java.io.IOException;

@SuperBuilder
public abstract class ATextWindow {

    protected final TextGraphics textGraphics;
    protected final Terminal terminal;

    public abstract void draw() throws IOException;
}
