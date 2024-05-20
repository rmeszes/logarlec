package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.WetWipe;

import java.util.logging.Logger;

public class WetWipeView extends ItemsView implements ItemChangeListener {
    private final WetWipe wipe;

    public WetWipeView(WetWipe wetWipe, int x, int y) {
        super(wetWipe);
        this.wipe = wetWipe;
        this.itemImage = WetWipeImage;
    }


    @Override
    public void isInRoom(boolean isInRoom) {
        if (isInRoom)
            this.setOpaque(true);
        else
            this.setOpaque(false);
    }
}
