package com.redvas.app.ui;

import java.awt.image.BufferedImage;

public class UITool {
    public static int fitWidth2AspectRatio(BufferedImage img, int height) {
        return (int)((double)img.getWidth() / img.getHeight() * height);
    }

    public static int fitHeight2AspectRatio(BufferedImage img, int width) {
        return (int)((double)img.getHeight() / img.getWidth() * width);
    }
}
