package test.assignment;

import java.util.*;

public class Application {

    public static void main(String[] args) {

        if (Arrays.asList(args).contains("--help")) {
            System.out.println("--help : show this menu");
            System.out.println("--password secretPassword get the next clue");
            System.exit(0);
        }

        Map<String, String> argumentMap = getProperties(args);
        String password = argumentMap.get("password");
        if (password != null && password.equals("BASILISK")) {
            System.out.println("Welcome parselmouth hissssss!");
            System.out.println("Your next clue is on youtube at request param watch -> v=KDXziQFJrsw");
            System.out.println("You will note the exact time in IST of this historic moment.");
            System.out.println("Run this same program with argument --time MM/dd/yyyy:HH:mm");
            System.exit(0);
        }

        String time = argumentMap.get("time");
        if (time.equals("09/24/2014:07:40")) {
            System.out.println("Submit your roll number followed by the name of this historic mission " +
                    "to hogwarts-engineering/assignment_thr33 -> refer to previous lectures on how to do this.");
        }
    }

    static Map<String, String> getProperties(String[] args) {
        Map<String, String> properties = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("--")) {
                properties.put(args[i].replaceAll("--", ""),
                        args[i + 1]);
                i++;
            }
        }
        return properties;
    }

}
