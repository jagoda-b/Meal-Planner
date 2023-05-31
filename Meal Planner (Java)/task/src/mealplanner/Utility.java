package mealplanner;

import java.sql.*;
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
        Pattern p = Pattern.compile("^( ?[ A-Za-z] ?)(,[A-Z]+)*( ?[A-Za-z] ?(, ?[A-Za-z]+ ?)*)*$");
        Matcher m = p.matcher(ingredients);

        return m.matches();
    }

    public static void showCommand(Connection connection) throws SQLException {
        PreparedStatement getAllMeals = connection.prepareStatement("SELECT * FROM meals");
        ResultSet allMeals = getAllMeals.executeQuery();


        if (allMeals.wasNull()){
            System.out.println("No meals saved. Add a meal first.");
        }

        while (allMeals.next()) {
            System.out.println("Category: " + allMeals.getString("category"));
            System.out.println("Name: " + allMeals.getString("meal"));

            PreparedStatement getIngredients = connection.prepareStatement("SELECT * FROM ingredients WHERE meal_id = ?");
            getIngredients.setInt(1, allMeals.getInt("meal_id"));
            ResultSet ingredients = getIngredients.executeQuery( );

            while (ingredients.next()){
                System.out.println(ingredients.getString("ingredient"));
            }


        }

    }

    public static void addCommand(Scanner scanner, List<Meal> meals) {
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

        Meal newMeal = mealBuilder.addType(mealType).addName(mealName).addIngredients(ingredients).build();

        meals.add(newMeal);
        System.out.println("The meal has been added!");

    }

    private Utility() {}  //to prevent accidental instantiation

}
