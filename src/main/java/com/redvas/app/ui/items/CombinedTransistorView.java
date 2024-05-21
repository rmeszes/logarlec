package com.redvas.app.ui.items;

import com.redvas.app.items.CombinedTransistor;

public class CombinedTransistorView extends ItemsView implements ItemChangeListener {
    private final CombinedTransistor ctransistor;
    public CombinedTransistorView(CombinedTransistor ctransistor) {
        super(ctransistor);
        this.ctransistor = ctransistor;
        this.itemImage = TransistorImage;
        ctransistor.setListener(this);
    }
}
