package com.redvas.app.skeleton;

import com.redvas.app.*;

import java.util.HashMap;
import java.util.Map;

public class Runner {

    private static final Map<Integer, String> useCases;

    static {
        useCases = HashMap.newHashMap(14);
        useCases.put(1,"Move rooms");
        useCases.put(2,"Pickup item");
        useCases.put(3,"Picku up Logarléc");
        useCases.put(4,"Dispose item");
        useCases.put(5,"Undegraduate dropout");
        useCases.put(6,"Use item");
        useCases.put(7,"Merge rooms");
        useCases.put(8,"Rooms fall apart");
        useCases.put(9,"Door vanishing");
        useCases.put(10,"Door appears");
        useCases.put(11,"Paralyze professors");
        useCases.put(12,"Undergraduate fainting");
        useCases.put(13,"Generate Labyrinth");
        useCases.put(14,"Step to next round");
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
