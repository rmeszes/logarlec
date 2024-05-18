package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.AirFreshener;
import com.redvas.app.ui.ItemsView;

import java.util.logging.Logger;

public class AirFreshenerView extends ItemsView implements ItemChangeListener {
    private static final Logger logger = App.getConsoleLogger(AirFreshenerView.class.getName());
    private final AirFreshener freshener;
    private int x;
    private int y;

    public AirFreshenerView(AirFreshener a, int x, int y) {
        super(a);
        this.freshener = a;
        this.x = x;
        this.y = y;
        this.itemImage = AirFreshenerImage;
    }


    @Override
    public void positionChanged(boolean isInRoom) {
    }

}
