package mealplanner.plan;

import mealplanner.Utility;
import mealplanner.meal.MealDAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class PlanDAO {
    Connection connection;
    MealDAO mealDAO;
    private List<String> days = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
    private List<String> mealcategories = List.of("breakfast", "lunch", "dinner");


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

    public void createNewPlan() {
        deletePlan();

        for (String day : days) {
            System.out.println(day);
            for (String mealCategory : mealcategories ) {
                createPlanForDay(day, mealCategory, new Scanner(System.in));
            }

            System.out.println(String.format("Yeah! We planned the meals for %s. %n", day));
        }

    }

    public void createPlanForDay(String day, String mealCategory, Scanner scanner) {
        Map<Integer, String> mealsFromCategory = getMealsFromCategory(mealCategory);
        printMeals(mealsFromCategory);
        String question = String.format("Choose the %s for %s from the list above:", mealCategory, day);
        String chosenMeal = Utility.getInfoFromUser(question, scanner);
        int mealId = getMealID(chosenMeal, mealsFromCategory);
        while (mealId < 0 ) {
            chosenMeal = Utility.getInfoFromUser(question, scanner);
            mealId = getMealID(chosenMeal, mealsFromCategory);
        }
        addMealtoPlan(chosenMeal, mealCategory, mealId, day);

    }

    public void addMealtoPlan(String mealName, String mealCategory, int mealId, String day) {
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




}
