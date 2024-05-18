package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.TVSZ;
import com.redvas.app.ui.ItemsView;

import java.util.logging.Logger;

public class TVSZView extends ItemsView implements ItemChangeListener {
    private static final Logger logger = App.getConsoleLogger(TVSZView.class.getName());
    private final TVSZ tvsz;
    private int x;
    private int y;

    public TVSZView(TVSZ tvsz, int x, int y) {
        super(tvsz);
        this.tvsz = tvsz;
        this.x = x;
        this.y = y;
        this.itemImage = TVSZImage;
    }


    @Override
    public void positionChanged(boolean isInRoom) {

    }
}
