package com.barelyconscious.jacket.common.gfx.windows;

import com.googlecode.lanterna.graphics.*;
import com.googlecode.lanterna.terminal.*;
import lombok.*;

@Builder
public abstract class ATextWindow {

    protected final TextGraphics textGraphics;
    protected final Terminal terminal;
}
