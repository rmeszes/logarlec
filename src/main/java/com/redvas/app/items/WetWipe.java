package com.redvas.app.items;

import com.redvas.app.map.Room;
import com.redvas.app.players.Janitor;
import com.redvas.app.players.Player;
import com.redvas.app.players.ProximityListener;

import java.util.List;

public class WetWipe extends Item implements ProximityListener {
    private int worksFor = 5;

    protected WetWipe(Room whichRoom) {
        super(whichRoom);
    }

    /** gives protection from profs FOR 5 rounds
     *
     */
    @Override
    public void use() {
        logger.fine(() -> this + " is being used...");
        super.use();
        owner.where().subscribeToProximity(this);
        whichRoom = owner.where();
        owner = null;
    }

    /**
     *
     * @param players
     */
    private void paralyzeProfessors(List<Player> players) {
        for (Player p : players) {
                p.paralyze();
        }
    }

    /** removes from owners inventory
     * then puts it on the floor of the room
     *
     */
    @Override
    public void dispose() {
        owner.removeFromInventory(this);
        whichRoom.addItem(this);
        owner = null;
    }

    /**
     * @return Az Item neve
     */
    @Override
    public String toString() {
        return "Wet Wipe";
    }

    @Override
    public void proximityChanged(Player newcomer) {
        newcomer.paralyze();
    }

    /** a proximitasaban levo jatekosokra meghivja a paralyze-t
     * es ugyanekkor elkezd szaradni a wetwipe, 5 kor mulva pedig megsemmisul
     * @param proximity
     */
    @Override
    public void proximityEndOfRound(List<Player> proximity) {
        if(worksFor > 0){
            for(Player p : proximity){
                p.paralyze();
                worksFor--;
            }
        }
        else{
            this.destroy();
        }
    }

    @Override
    public void proximityInitially(List<Player> proximity) {
        paralyzeProfessors(proximity);
    }

    @Override
    public int listenerPriority() {
        return 0;
    }

    @Override
    public void getAffected(Janitor by) {
        //It does nothing, Janitor DOES NOT affect the wetwipe
        logger.finest(() -> this + " getAffected(by janitor)");
    }

    @Override
    public void getAffected(AirFreshener by) {
        //It does nothing, AirFreshener DOES NOT affect the wetwipe
        logger.finest(() -> this + " getAffected(by airFreshener)");
    }

    @Override
    public void affect(ProximityListener listener) {
        //TODO
        logger.finest(() -> this + " affect");
    }
}
