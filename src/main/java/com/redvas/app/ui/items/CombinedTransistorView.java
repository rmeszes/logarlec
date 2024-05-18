package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.CombinedTransistor;
import com.redvas.app.ui.ItemsView;

import java.util.logging.Logger;

public class CombinedTransistorView extends ItemsView implements ItemChangeListener {
    private static final Logger logger = App.getConsoleLogger(CombinedTransistorView.class.getName());
    private final CombinedTransistor ctransistor;
    private int x;
    private int y;

    public CombinedTransistorView(CombinedTransistor ctransistor, int x, int y) {
        super(ctransistor);
        this.ctransistor = ctransistor;
        this.x = x;
        this.y = y;
        this.itemImage = TransistorImage;
    }
    @Override
    public void positionChanged(boolean isInRoom) {
    }
}
