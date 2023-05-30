package mealplanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Meal> meals = new ArrayList<>();
        boolean menuFlag = true;

        while (menuFlag){
            String command = Utility.getInfoFromUser("What would you like to do (add, show, exit)?", scanner);


            switch (command) {
                case "add" :
                    Utility.addCommand(scanner, meals);
                    break;

                case "show" :
                    Utility.showCommand(meals);
                    break;

                case "exit" :
                    System.out.println("Bye!");
                    menuFlag = false;
            }

        }

        scanner.close();
    }





}