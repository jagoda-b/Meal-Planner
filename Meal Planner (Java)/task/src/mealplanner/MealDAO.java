package mealplanner;

import java.sql.*;

public class MealDAO {

    private final String dbUrl = "jdbc:postgresql:meals_db";
    private final String user = "postgres";
    private final String pass = "1111";

    private Connection connection;


    protected Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(dbUrl, user, pass);
            connection.setAutoCommit(true);
        }
        createTables();
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }

    public void createTables() {

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS meals(category VARCHAR(64), meal VARCHAR(64), meal_id INTEGER);");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ingredients(ingredient VARCHAR(1024), ingredient_id INT, meal_id INTEGER);");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        PreparedStatement getAllMeals = connection.prepareStatement("SELECT * FROM meals WHERE category = ?");
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

            PreparedStatement getIngredients = connection.prepareStatement("SELECT * FROM ingredients WHERE meal_id = ?");
            getIngredients.setInt(1, allMeals.getInt("meal_id"));
            ResultSet ingredients = getIngredients.executeQuery();
            System.out.println("Ingredients:");
            while (ingredients.next()){
                System.out.println(ingredients.getString("ingredient").strip());
            }

        }

        getAllMeals.close();

    }

}
