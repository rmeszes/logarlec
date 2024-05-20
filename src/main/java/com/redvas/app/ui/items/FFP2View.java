package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.FFP2;

import java.util.logging.Logger;

public class FFP2View extends ItemsView implements ItemChangeListener {
    private final FFP2 ffp2;

    public FFP2View(FFP2 ffp2) {
        super(ffp2);
        this.ffp2 = ffp2;
        this.itemImage = FFP2Image;
    }


    @Override
    public void isInRoom(boolean isInRoom) {
        if (isInRoom)
            this.setOpaque(true);
        else
            this.setOpaque(false);
        repaint();
    }
}
