package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.TVSZ;

import java.util.logging.Logger;

public class TVSZView extends ItemsView implements ItemChangeListener {
    private final TVSZ tvsz;

    public TVSZView(TVSZ tvsz) {
        super(tvsz);
        this.tvsz = tvsz;
        this.itemImage = TVSZImage;
    }


    @Override
    public void isInRoom(boolean isInRoom) {
        if (isInRoom)
            this.setOpaque(true);
        else
            this.setOpaque(false);
    }
}
