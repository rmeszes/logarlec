package com.redvas.app.ui.items;

import com.redvas.app.items.TVSZ;

public class TVSZView extends ItemsView implements ItemChangeListener {
    private final TVSZ tvsz;

    public TVSZView(TVSZ tvsz) {
        super(tvsz);
        this.tvsz = tvsz;
        this.itemImage = TVSZImage;
        tvsz.setListener(this);
    }
}
