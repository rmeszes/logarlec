package com.redvas.app.ui;

import java.awt.*;

public abstract class View {
    protected int x, y, z;
    public abstract void draw(Graphics2D g);
}
