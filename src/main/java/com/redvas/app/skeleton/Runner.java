package com.redvas.app.skeleton;

import com.redvas.app.*;

import java.util.HashMap;
import java.util.Map;

public class Runner {

    private static final Map<Integer, String> useCases;

    static {
        useCases = HashMap.newHashMap(14);
        useCases.put(1,"Szobába átlépés");
        useCases.put(2,"Tárgy felvétele");
        useCases.put(3,"Logarléc felvétele");
        useCases.put(4,"Tárgy letétele (Dispose)");
        useCases.put(5,"Hallgató kibukik (Drop out)");
        useCases.put(6,"Tárgy használata");
        useCases.put(7,"Szobák egyesülése");
        useCases.put(8,"Szoba osztódása");
        useCases.put(9,"Ajtók eltűnése");
        useCases.put(10,"Ajtók megjelenése");
        useCases.put(11,"Megbénulás (paralyze)");
        useCases.put(12,"Eszméletvesztés (faint)");
        useCases.put(13,"Labirintus generálása");
        useCases.put(14,"Játék léptetése");
    }

    public static void main(String[] args) {
        Runner r = new Runner();
        r.run();
        System.out.println("exiting runner..");
    }

    public Runner() {
        System.out.println("Skeleton runner app\n-------------------\n");
    }

    private void run() {
        System.out.println("Please choose from test cases, or type \"exit\": (enter number)");
        useCases.forEach((o1,o2) -> {
            System.out.println(o1 + ". " + o2);
        });

        String input;
        input = App.reader.nextLine();

        switch(input) {
            case "1" -> Helper.useCase1();
            case "2" -> Helper.useCase2();
            case "3" -> Helper.useCase3();
            case "4" -> Helper.useCase4();
            case "5" -> Helper.useCase5();
            case "6" -> Helper.useCase6();
            case "7" -> Helper.useCase7();
            case "8" -> Helper.useCase8();
            case "9" -> Helper.useCase9();
            case "10" -> Helper.useCase10();
            case "11" -> Helper.useCase11();
            case "12" -> Helper.useCase12();
            case "13" -> Helper.useCase13();
            case "14" -> Helper.useCase14();

            case "exit" -> {
                return;
            }

            default -> System.out.println("No test case selected. Try again");
        }
        System.out.println("Selected test case over, please choose a new one, or type \"exit\"");

        run();
    }
}
