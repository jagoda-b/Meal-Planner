package mealplanner.shoppinglist;

import mealplanner.Utility;
import mealplanner.meal.MealDAO;
import mealplanner.plan.PlanDAO;

import java.io.File;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ShoppingList {
    private MealDAO mealDAO;
    private PlanDAO planDAO;





    public void save(Scanner scanner){
        String fileName = Utility.getInfoFromUser("Input a filename:", scanner);
        File shoppingList = new File(fileName);


    }

    private Map<String, Integer> getIngredients(){
        Map<String,Integer> ingredients = new TreeMap<>();


        return ingredients;
    }

}
