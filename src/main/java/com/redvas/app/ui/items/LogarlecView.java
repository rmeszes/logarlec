package com.redvas.app.ui.items;

import com.redvas.app.items.Logarlec;

public class LogarlecView extends ItemsView implements ItemChangeListener {
    private final Logarlec logarlec;

    public LogarlecView(Logarlec l) {
        super(l);
        this.logarlec = l;
        this.itemImage = LogarlecImage;
        l.setListener(this);
    }


}
