package mealplanner.shoppinglist;

import mealplanner.Utility;
import mealplanner.meal.MealDAO;
import mealplanner.plan.PlanDAO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class ShoppingList {


    public static void save(PlanDAO planDAO, Scanner scanner){
        String fileName = Utility.getInfoFromUser("Input a filename:", scanner);

        BufferedWriter output = null;
        try {
            File file = new File(fileName);
            output = new BufferedWriter(new FileWriter(file));
            output.write(prepareIngredients(planDAO));
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) {
                try {
                    output.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Saved!");

    }

    private static String prepareIngredients(PlanDAO planDAO){

        return planDAO.printIngredients();
    }

}
