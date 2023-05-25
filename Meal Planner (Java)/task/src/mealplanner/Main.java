package mealplanner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true){
            Utility.getInfoFromUser("What would you like to do (add, show, exit)?", scanner);
            String command = scanner.nextLine().toLowerCase();



            if (command.equals("exit"))
                break;

            switch (command) {
                case "add" :

                    break;

                case "show" :

                    break;


            }

        }



        Meal.MealBuilder mealBuilder = new Meal.MealBuilder();



        String mealType = Utility.getInfoFromUser("Which meal do you want to add (breakfast, lunch, dinner)?", scanner);
        String mealName = Utility.getInfoFromUser("Input the meal's name: ", scanner);
        String ingredients = Utility.getInfoFromUser("Input the ingredients: ", scanner);

        Meal meal = mealBuilder.addType(mealType).addName(mealName).addIngredients(ingredients).build();

        System.out.println(meal.toString());

        System.out.println("The meal has been added!");

        scanner.close();
    }


}