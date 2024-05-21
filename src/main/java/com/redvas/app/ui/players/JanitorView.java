package com.redvas.app.ui.players;

import com.redvas.app.players.Janitor;
import com.redvas.app.ui.players.listeners.PlayerChangeListener;

public class JanitorView extends PlayerView implements PlayerChangeListener {
    private Janitor janitor;
    public JanitorView(Janitor janitor) {
        super(janitor);
        this.janitor = janitor;
        this.myImage = janitorImage;
    }

    @Override
    public void faintedChanged(boolean isFainted) {
        if(isFainted)
            this.myImage = faintedjanitorImage;
        else
            this.myImage = janitorImage;
        repaint();
    }
}
