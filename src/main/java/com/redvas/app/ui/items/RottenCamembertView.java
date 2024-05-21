package com.redvas.app.ui.items;

import com.redvas.app.items.RottenCamembert;

public class RottenCamembertView extends ItemsView implements ItemChangeListener {
    private final RottenCamembert camembert;

    public RottenCamembertView(RottenCamembert rottenCamembert) {
        super(rottenCamembert);
        this.camembert = rottenCamembert;
        this.itemImage = RottenCamembertImage;
        rottenCamembert.setListener(this);
    }


}
