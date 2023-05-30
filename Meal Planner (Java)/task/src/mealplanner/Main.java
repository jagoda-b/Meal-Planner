package mealplanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Meal> meals = new ArrayList<>();

        while (true){
            String command = Utility.getInfoFromUser("What would you like to do (add, show, exit)?", scanner);

            if (command.equals("exit"))
                break;

            switch (command) {
                case "add" :
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

                    System.out.println(newMeal.toString());
                    meals.add(newMeal);
                    System.out.println("The meal has been added!");
                    break;

                case "show" :


                    for ( Meal meal : meals) {
                        System.out.println(meal.toString());
                    }

                    break;


            }

        }










        scanner.close();
    }


}