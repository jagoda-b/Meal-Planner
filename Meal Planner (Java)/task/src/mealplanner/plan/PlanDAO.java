package mealplanner.plan;

import mealplanner.Utility;
import mealplanner.meal.MealDAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PlanDAO {
    private Connection connection;
    private MealDAO mealDAO;
    private Map<String, Integer> XXingredients = new TreeMap<>();
    private List<String> days = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
    private List<String> mealcategories = List.of("Breakfast", "Lunch", "Dinner");
    private boolean isReady = false;


    public PlanDAO(Connection connection, MealDAO mealDAO) {
        this.connection = connection;
        this.mealDAO = mealDAO;
    }


    protected void deletePlan() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE TABLE plan")){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createNewPlan() throws SQLException {
        deletePlan();

        for (String day : days) {
            System.out.println(day);
            for (String mealCategory : mealcategories ) {
                createPlanForDay(day, mealCategory.toLowerCase(), new Scanner(System.in));
            }

            System.out.println(String.format("Yeah! We planned the meals for %s. %n", day));
        }

        printPlan();
    }

    public void createPlanForDay(String day, String mealCategory, Scanner scanner) {
        Map<Integer, String> mealsFromCategory = getMealsFromCategory(mealCategory);
        printMeals(mealsFromCategory);
        String question = String.format("Choose the %s for %s from the list above:", mealCategory, day);
        String chosenMeal = Utility.getInfoFromUser(question, scanner);
        int mealId = getMealID(chosenMeal, mealsFromCategory);
        while (mealId < 0 ) {
            chosenMeal = Utility.getInfoFromUser("This meal doesn’t exist. Choose a meal from the list above.", scanner);
            mealId = getMealID(chosenMeal, mealsFromCategory);
        }
        addMealtoPlan(chosenMeal, mealCategory, mealId, day);
       // collectIngredients(mealId);

    }

    private void addMealtoPlan(String mealName, String mealCategory, int mealId, String day) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO plan " +
                                                                                "(meal_option, category, meal_id, day)" +
                                                                                "VALUES ( ?, ?, ?, ? );")) {

            preparedStatement.setString(1, mealName);
            preparedStatement.setString(2, mealCategory);
            preparedStatement.setInt(3, mealId);
            preparedStatement.setString(4, day);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Map<Integer, String> getMealsFromCategory(String mealsCategory) {
        return mealDAO.getMeals(mealsCategory);
    }

    private void printMeals(Map<Integer, String> mealsFromCategory) {
        TreeSet<String> meals = new TreeSet<>(mealsFromCategory.values());
        for (String meal : meals) {
            System.out.println(meal);
        }
    }

    private int getMealID(String meal, Map<Integer, String> mealsFromCategory) {
        if(!mealsFromCategory.containsValue(meal)){
            return -1;
        } else {
            return mealsFromCategory.keySet()
                    .stream()
                    .filter(key -> meal.equals(mealsFromCategory.get(key)))
                    .findFirst().get();
        }
    }

    private String getPlanFromDB(String day, String mealcategory){
        ResultSet resultSet ;
        String mealName = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM plan " +
                                                                                   "WHERE day = ? AND category = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE )) {
            preparedStatement.setString(1, day);
            preparedStatement.setString(2, mealcategory.toLowerCase());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                mealName = resultSet.getString("meal_option");
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            return mealName;
        }

    }

    private List<String> getWholePlanFromDB(){
        ResultSet resultSet ;
        List<String> meals = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM plan ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE )) {
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                meals.add(resultSet.getString("meal_option"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            return meals;
        }

    }

    public void printPlan() {

        for (String day : days) {
            System.out.println(day);
            for (String mealCategory : mealcategories ) {

                String mealName = getPlanFromDB(day, mealCategory);
                System.out.println(String.format("%s: %s ", mealCategory, mealName));

            }
            System.out.println("");
        }
    }

    private void collectIngredients(int mealID){

        List<String> mealIngredients = mealDAO.getIngredients(mealID);

        for (String ingredient : mealIngredients ) {
            if(XXingredients.containsKey(ingredient)) {
                XXingredients.replace(ingredient, XXingredients.get(ingredient) + 1);

            }
            else {
                XXingredients.put(ingredient, 1);

            }
        }

    }

    public void prepareIngredients() {
        List<String> plannedMeals = getWholePlanFromDB();

        for (String meal: plannedMeals) {
            int mealID = mealDAO.getMealID(meal);
            collectIngredients(mealID);
        }

    }

    public String printIngredients(){
        StringBuilder sb = new StringBuilder();
        prepareIngredients();

        for (Map.Entry<String, Integer> ingredient : XXingredients.entrySet()) {
            sb.append(ingredient.getKey());
            if (ingredient.getValue() > 1 ) {
                sb.append(" ").append("x").append(ingredient.getValue());
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public Boolean isReady() {

        ResultSet resultSet ;

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS recordCount FROM plan ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE )) {
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int size = resultSet.getInt("recordCount");
            if (size > 0 ){
                isReady = true;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            return isReady;
        }
    }

}
