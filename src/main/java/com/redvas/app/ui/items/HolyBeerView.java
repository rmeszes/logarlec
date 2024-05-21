package com.redvas.app.ui.items;

import com.redvas.app.items.HolyBeer;

public class HolyBeerView extends ItemsView implements ItemChangeListener {
    private final HolyBeer beer;

    public HolyBeerView(HolyBeer holyBeer) {
        super(holyBeer);
        this.beer = holyBeer;
        this.itemImage = HolyBeerImage;
        holyBeer.setListener(this);
    }

}
