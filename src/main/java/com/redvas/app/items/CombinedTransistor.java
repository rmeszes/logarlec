package com.redvas.app.items;

import com.redvas.app.players.Player;

public class CombinedTransistor extends Item{

    @Override
    public void use(){
        // use
    }

    @Override
    public void pickup(Player who) {
        super.pickup(who);
    }

    @Override
    public void dispose() {
        // dispose
    }

    private void setDisposedPair(Transistor transistor){
        // something
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "Combined Transistor";
    }
}
