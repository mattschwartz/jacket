package com.barelyconscious.jacket.gui;


import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;

public class ExampleWindow2 extends BasicWindow {

    public ExampleWindow2() {
        super("My Window!");
        Panel horizontalPanel = new Panel();
        horizontalPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        Panel leftPanel = new Panel();
        Panel middlePanel = new Panel();
        Panel rightPanel = new Panel();
//
//        middlePanel.addComponent(Borders.singleLineBevel("Panel Title"));
//        rightPanel.addComponent(Borders.doubleLineBevel());

        leftPanel.addComponent(new TextBox("Left!"));
        leftPanel.addComponent(new TextBox("Left!"));
        leftPanel.addComponent(new TextBox("Left!"));
        leftPanel.addComponent(new TextBox("Left!"));
        leftPanel.addComponent(new TextBox("Left!"));
        middlePanel.addComponent(new TextBox("Middle!"));
        rightPanel.addComponent(new TextBox("Right!"));
        rightPanel.addComponent(new TextBox("Right!"));
        rightPanel.addComponent(new TextBox("Right!"));
        rightPanel.addComponent(new TextBox("Right!"));
//
        horizontalPanel.addComponent(leftPanel);
        horizontalPanel.addComponent(middlePanel);
        horizontalPanel.addComponent(rightPanel);

        // This ultimately links in the panels as the window content
        setComponent(horizontalPanel);
    }
}
