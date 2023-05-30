package mealplanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    public static String getInfoFromUser(String question, Scanner scanner) {
        System.out.println(question);
        return scanner.nextLine();
    }

    public static boolean validateMealType(String mealType) {

        for (MealType meal : MealType.values())
            if (meal.name().equalsIgnoreCase(mealType)) {
                return true;
            }
        return false;
    }

    public static boolean validateMealName(String mealName) {
        Pattern p = Pattern.compile("^[ A-Za-z]+$");
        Matcher m = p.matcher(mealName);

        return m.matches();
    }

    public static boolean validateIngredients(String ingredients) {
        //TO DO
        return false;
    }

}
