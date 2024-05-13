package com.redvas.app.ui;

import java.awt.image.BufferedImage;

public class UITool {
    public static int fitWidth2AspectRatio(BufferedImage img, int height) {
        return img.getWidth() / img.getHeight() * height;
    }

    public static int fitHeight2AspectRatio(BufferedImage img, int width) {
        return img.getHeight() / img.getWidth() * width;
    }
}
