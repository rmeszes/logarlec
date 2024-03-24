package com.redvas.app.skeleton;

import com.redvas.app.players.*;
import com.redvas.app.map.*;
import com.redvas.app.items.*;
import com.redvas.app.*;

import java.util.HashMap;


public class Helper {

    public static void useCase1() {
        System.out.println("Use case 1: Stepping into another Room:\n");
        Room room = new Room();
        room.transfer(new Undergraduate(), new Room());
    }

    public static void useCase2() {
        System.out.println("Use case 2: Picking up an item:\n");
        Player p = new Undergraduate();
        p.pickItem(0);
    }

    public static void useCase3() {
        System.out.println("Use case 3: Undegraduate picks up Logarléc");

        Logarlec logarlec = new Logarlec();

        logarlec.pickup(new Undergraduate());
    }

    public static void useCase4() {
        System.out.println("Use case 4: Player disposes of item:\n");

        new Undergraduate().disposeItem(0);
    }

    public static void useCase5() {
        System.out.println("Use case 5: Undergraduate drops out:\n");

        HashMap<Integer, Room> rooms = HashMap.newHashMap(2);
        rooms.put(1, new Room());
        rooms.put(2, new Room());

        Professor p = new Professor();

        rooms.get(1).transfer(p,rooms.get(2));
    }

    public static void useCase6() {
        System.out.println("use case 6 runs");
    }

    public static void useCase7() {
        System.out.println("use case 7 runs");
    }

    public static void useCase8() {
        System.out.println("use case 8 runs");
    }

    public static void useCase9() {
        System.out.println("use case 9 runs");
    }

    public static void useCase10() {
        System.out.println("use case 10 runs");
    }

    public static void useCase11() {
        System.out.println("use case 11 runs");
    }

    public static void useCase12() {
        System.out.println("use case 12 runs");
    }

    public static void useCase13() {
        System.out.println("use case 13 runs");
    }

    public static void useCase14() {
        System.out.println("use case 14 runs");
    }
}
