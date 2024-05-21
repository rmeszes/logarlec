package com.redvas.app.ui.items;

import com.redvas.app.App;
import com.redvas.app.items.AirFreshener;
import com.redvas.app.ui.players.listeners.ProfessorChangeListener;

import java.util.logging.Logger;

public class AirFreshenerView extends ItemsView implements ItemChangeListener {
    private final AirFreshener freshener;

    public AirFreshenerView(AirFreshener a) {
        super(a);
        this.freshener = a;
        this.itemImage = AirFreshenerImage;
        freshener.setListener(this);
    }

}
