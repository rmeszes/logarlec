package com.redvas.app.ui.items;

public interface ItemChangeListener {
    // amikor a tárgy helyzete változik (szobából felveszi valaki, leteszi valaki)
    void positionChanged(boolean isInRoom);
}
