package com.barelyconscious.jacket.gui;

import com.barelyconscious.jacket.common.gfx.windows.ATextWindow;
import com.barelyconscious.jacket.data.model.JacketPage;
import lombok.experimental.SuperBuilder;

import java.io.IOException;

@SuperBuilder
public class DayEditorWindow extends ATextWindow {

    private final JacketPage jacketPage;

    @Override
    public void draw() throws IOException {

    }
}
