package mealplanner.meal;

import java.sql.*;
import java.util.Map;

public class MealDAO {
    private Connection connection;

    public MealDAO(Connection connection) {
        this.connection = connection;
    }

    public void addMealtoDB(Meal meal) throws SQLException {
        PreparedStatement getAllMeals = connection.prepareStatement("SELECT * FROM meals", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet allMeals = getAllMeals.executeQuery();

        PreparedStatement getAllIngredients = connection.prepareStatement("SELECT * FROM ingredients", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet allIngredients = getAllIngredients.executeQuery();

        int mealID = 0;
        int ingredientID = 0;

        if(allMeals.next()){

            allMeals.last();
            allIngredients.last();
            mealID = allMeals.getInt("meal_id") + 1;
            ingredientID = allIngredients.getInt("ingredient_id") + 1;
        }


        PreparedStatement addMeal = connection.prepareStatement("INSERT INTO meals "
                + "(category, meal, meal_id)"
                + " VALUES (?, ?, ?) ");

        addMeal.setString(1, meal.getCategory());
        addMeal.setString(2, meal.getName());
        addMeal.setInt(3,mealID);

        addMeal.executeUpdate();

        for(String ingredient : meal.getIngredients()){
            PreparedStatement addIngredient = connection.prepareStatement("INSERT INTO ingredients "
                    + "(ingredient, ingredient_id, meal_id)"
                    + " VALUES (?, ?, ?) ");

            addIngredient.setString(1, ingredient);
            addIngredient.setInt(2, ingredientID);
            addIngredient.setInt(3, mealID);
            ingredientID++;

            addIngredient.executeUpdate();
        }

        System.out.println("The meal has been added!");

    }


    public  void showMealCategory(String mealCategory) throws SQLException {

        PreparedStatement getAllMeals = connection.prepareStatement("SELECT * FROM meals " +
                                                                        "LEFT JOIN ingredients " +
                                                                        "ON meals.meal_id = ingredients.meal_id " +
                                                                        "WHERE meals.category = ?;",
                                                                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        getAllMeals.setString(1, mealCategory);
        ResultSet allMeals = getAllMeals.executeQuery();


        if (!allMeals.isBeforeFirst()){
            System.out.println("No meals found.");
        }
        else {
            System.out.println("Category: " + mealCategory);
            System.out.println("");
        }


        while (allMeals.next()) {

            System.out.println("Name: " + allMeals.getString("meal"));

            int meal_id = allMeals.getInt("meal_id");
            System.out.println("Ingredients:");
            do{
                System.out.println(allMeals.getString("ingredient").strip());
            } while (allMeals.next() && allMeals.getInt("meal_id") == meal_id);
            allMeals.previous();

        }

        getAllMeals.close();

    }
    
    public Map<Integer, String> getMeals (String mealCategory) {
        
        Map<Integer, String> meals = null;
         
        try (PreparedStatement getMeals = connection.prepareStatement("SELECT * FROM meals " +
                                                                        "WHERE meals.category = ?;")){
            getMeals.setString(1, mealCategory);
            ResultSet allMeals = getMeals.executeQuery();
            while (allMeals.next()){
                meals.put(allMeals.getInt("meal_id"), allMeals.getString("meal"));
            }
            allMeals.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        return meals;
    }

}
