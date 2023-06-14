package mealplanner.shoppinglist;

import mealplanner.Utility;

import java.io.File;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ShoppingList {


    public void save(Scanner scanner){
        String fileName = Utility.getInfoFromUser("Input a filename:", scanner);
        File shoppingList = new File(fileName);


    }

    private Map<String, Integer> getIngredients(){
        Map<String,Integer> ingredients = new TreeMap<>();


        return ingredients;
    }

}
