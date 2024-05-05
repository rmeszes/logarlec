package com.redvas.app.items;

import com.redvas.app.map.rooms.Room;
import com.redvas.app.players.Janitor;
import com.redvas.app.players.Player;
import com.redvas.app.players.ProximityListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

public class WetWipe extends Item implements ProximityListener {
    private int worksFor = 5;
    public WetWipe(Integer id, Room whichRoom, Boolean isListener) {
        super(id, whichRoom, isListener);
    }
    public WetWipe(Integer id, Room whichRoom) {
        super(id, whichRoom, false);
    }

    /** gives protection from profs FOR 5 rounds
     *
     */
    protected WetWipe(Integer id, Player owner) {
        super(id, owner);
    }

    /**
     * using it means the item subscribes to the proximity of the room
     * this proximity will make the professors paralyzed
     */
    @Override
    public void use() {
        logger.fine(() -> this + " is being used...");
        owner.where().subscribeToProximity(this);
        whichRoom = owner.where();
        destroy();
    }

    /**
     *
     * @param players: list of players in the room, only professors implement the paralyze function
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

    /**
     *
     * @param newcomer: the player that just entered the room
     *                if it is a professor, he gets paralyzed immediately
     */
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
    /**
     *
     * @param proximity: list of players who are in the room by the beginning of the round
     */
    @Override
    public void proximityInitially(List<Player> proximity) {
        paralyzeProfessors(proximity);
    }

    @Override
    public int listenerPriority() {
        return 0;
    }

    /** It does nothing, Janitor DOES NOT affect the wetwipe
     *
     * @param by
     */
    @Override
    public void getAffected(Janitor by){
        logger.finest(() -> this + " getAffected(by janitor)");
    }

    /**
     * It does nothing, AirFreshener DOES NOT affect the wetwipe
     * @param by
     */
    @Override
    public void getAffected(AirFreshener by) {

        logger.finest(() -> this + " getAffected(by airFreshener)");
    }

    @Override
    public void affect(ProximityListener listener) {
        logger.finest(() -> this + " affect");
    }

    @Override
    public Element savePhantomListenerXML(Document document) {
        Element listener = document.createElement("phantom_listener");
        listener.setAttribute("type", getClass().getName());
        return listener;
    }
}
