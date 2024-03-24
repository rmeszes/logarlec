package com.redvas.app.skeleton;

import com.redvas.app.*;

public class Runner {

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
        System.out.println("1. ...");
        System.out.println("2. ...");
        System.out.println("3. ...");
        System.out.println("4. ...");
        System.out.println("5. ...");

        String input;
        input = App.reader.nextLine();

        switch(input) {
            case "1" -> firstTestCase();
            case "2" -> secondTestCase();
            case "3" -> thirdTestCase();
            case "4" -> fourthTestCase();
            case "5" -> fifthTestCase();

            case "exit" -> {
                return;
            }

            default -> System.out.println("No test case selected. Try again");
        }
        System.out.println("Selected test case over, please choose a new one, or type \"exit\"");

        run();
    }

    private void firstTestCase() {
    }
    private void secondTestCase() {
    }
    private void thirdTestCase() {
    }
    private void fourthTestCase() {
    }
    private void fifthTestCase() {
    }
}
