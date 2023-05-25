package mealplanner;

import java.util.Scanner;

public class Utility {
    public static String getInfoFromUser(String question, Scanner scanner) {
        System.out.println(question);
        return scanner.nextLine();
    }

}
