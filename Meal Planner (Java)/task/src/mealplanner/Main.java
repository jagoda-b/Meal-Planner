package mealplanner;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Meal.MealBuilder mealBuilder = new Meal.MealBuilder();

    Scanner scanner = new Scanner(System.in);

    String mealType = getInfoFromUser("Which meal do you want to add (breakfast, lunch, dinner)?", scanner);
    String mealName = getInfoFromUser("Input the meal's name: ", scanner);
    String ingredients = getInfoFromUser("Input the ingredients: ", scanner);

    Meal meal = mealBuilder.addType(mealType).addName(mealName).addIngredients(ingredients).build();

    System.out.println(meal.toString());

    System.out.println("The meal has been added!");

    scanner.close();
  }

  private static String getInfoFromUser(String question, Scanner scanner) {
    System.out.println(question);
    return scanner.nextLine();
  }
}