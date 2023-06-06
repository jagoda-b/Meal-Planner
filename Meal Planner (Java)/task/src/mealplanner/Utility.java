package mealplanner;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    public static String getInfoFromUser(String question, Scanner scanner) {
        System.out.println(question);
        return scanner.nextLine();
    }

    public static boolean validateMealType(String mealType) {

        for (MealCategory meal : MealCategory.values())
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
        Pattern p = Pattern.compile("^( ?[ A-Za-z] ?)(,[A-Z]+)*( ?[A-Za-z] ?(, ?[A-Za-z]+ ?)*)*$");
        Matcher m = p.matcher(ingredients);

        return m.matches();
    }

    public static Meal addCommand(Scanner scanner)  {
        Meal.MealBuilder mealBuilder = new Meal.MealBuilder();

        String mealType = Utility.getInfoFromUser("Which meal do you want to add (breakfast, lunch, dinner)?", scanner);
        while (!Utility.validateMealType(mealType)){
            mealType =  Utility.getInfoFromUser("Wrong meal category! Choose from: breakfast, lunch, dinner.", scanner);
        }


        String mealName = Utility.getInfoFromUser("Input the meal's name: ", scanner);
        while (!Utility.validateMealName(mealName)){
            mealName =  Utility.getInfoFromUser("Wrong format. Use letters only!", scanner);
        }


        String ingredients = Utility.getInfoFromUser("Input the ingredients: ", scanner);
        while (!Utility.validateIngredients(ingredients)){
            ingredients =  Utility.getInfoFromUser("Wrong format. Use letters only!", scanner);
        }

        return mealBuilder.addCategory(mealType).addName(mealName).addIngredients(ingredients).build();

    }

    public static void showCommand(Scanner scanner) {
        System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
        String mealCategory = scanner.nextLine();
    }


    private Utility() {}  //to prevent accidental instantiation


}
