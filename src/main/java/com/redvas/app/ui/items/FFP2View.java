package com.redvas.app.ui.items;

import com.redvas.app.items.FFP2;

public class FFP2View extends ItemsView implements ItemChangeListener {
    private final FFP2 ffp2;

    public FFP2View(FFP2 ffp2) {
        super(ffp2);
        this.ffp2 = ffp2;
        this.itemImage = FFP2Image;
        ffp2.setListener(this);
    }

}
