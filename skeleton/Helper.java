package com.redvas.app.skeleton;

import com.redvas.app.players.*;
import com.redvas.app.map.*;
import com.redvas.app.items.*;
import com.redvas.app.*;

import java.util.HashMap;


public class Helper {
    /** UseCase 1
     * undergraduate steps into another room succesfully
     */
    public static void useCase1() {
        System.out.println("Use case 1: Stepping into another Room:\n");
        Room room = new Room();
        room.transfer(new Undergraduate(), new Room());
    }

    /** UseCase 2
     *  undergraduate picks up the item that is identified with
     *  index 0
     */
    public static void useCase2() {
        System.out.println("Use case 2: Picking up an item:\n");
        Player p = new Undergraduate();
        p.pickItem(0);
    }

    /** UseCase 3
     * undergraduate picks up the Logarlec
     * pickup calls pickLogarlec()
     * that calls Game.undergraduateVictory()
     *
     */
    public static void useCase3() {
        System.out.println("Use case 3: Undergraduate picks up Logarléc");

        Logarlec logarlec = new Logarlec();

        logarlec.pickup(new Undergraduate());
    }

    /** UseCase 4
     * player disposes of an item
     * that is identified by index 0
     */
    public static void useCase4() {
        System.out.println("Use case 4: Player disposes of item:\n");

        new Undergraduate().disposeItem(0);
    }

    /** UseCase5
     * undergraduate drops out
     * two rooms are generated, and a professor who steps inside
     * the newly generated rooms
     *
     */
    public static void useCase5() {
        System.out.println("Use case 5: Undergraduate drops out:\n");

        HashMap<Integer, Room> rooms = HashMap.newHashMap(2);
        rooms.put(1, new Room());
        rooms.put(2, new Room());

        Professor p = new Professor();

        rooms.get(1).transfer(p,rooms.get(2));
    }

    /** UseCase6
     * undergraduate uses an item
     * that is identified by index 0
     */
    public static void useCase6() {
        System.out.println("Use case 6: Undergraduate uses an item");

        new Undergraduate().useItem(0);
    }

    /** UseCase7
     * two newly generated rooms are merged
     */
    public static void useCase7() {
        System.out.println("Use case 7: Merging rooms");

        new Room().mergeWithRoom(new Room());
    }

    /** UseCase8
     * a room is divided into two rooms
     */
    public static void useCase8() {
        System.out.println("Use case 8: Room divides");

        new Room().divide();
    }

    /** UseCase9
     * a door appears and it is not vanished
     */
    public static void useCase9() {
        System.out.println("Use case 9: Door appears");

        new Door().setVanished(false);
    }

    /** UseCase10
     * a door is created and then vanishes
     */
    public static void useCase10() {
        System.out.println("Use case 10: Door vanishes");

        new Door().setVanished(true);
    }

    /** UseCase11
     * someone used a wet wipe that paralyzed the professors
     */
    public static void useCase11() {
        System.out.println("Use case 11: Paralyze professors");

        new WetWipe().use();
    }

    /** UseCase12
     * undergraduate stepped inside a room that caused them to faint
     */
    public static void useCase12() {
        System.out.println("Use case 12: Undergraduates faint");

        new Room().transfer(new Undergraduate(),new Room());
    }

    /** UseCase14
     * labyrinth is created
     */
    public static void useCase13() {
        System.out.println("Use case 13: Generate Labyrinth");

        new Labyrinth();
    }

    /** UseCase14
     *  new round
     */
    public static void useCase14() {
        System.out.println("Use case 14: New round");

        new Game().playRound();
    }
}
