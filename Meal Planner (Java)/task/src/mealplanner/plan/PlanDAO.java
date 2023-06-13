package mealplanner.plan;

import mealplanner.Utility;
import mealplanner.meal.Meal;
import mealplanner.meal.MealDAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
            System.out.println("\n" + day);
            for (String mealCategory : mealcategories ) {
                createPlanForDay(day, mealCategory, new Scanner(System.in));
            }
        }

    }

    public void createPlanForDay(String day, String mealCategory, Scanner scanner) {
        Map<Integer, String> mealsFromCategory = getMealsFromCategory(mealCategory);
        printMeals(mealsFromCategory);
        String question = String.format("Choose the %s for %s from the list above:", mealCategory, day);
        String chosenMeal = Utility.getInfoFromUser(question, scanner);
    }

//    public void addMealtoPlan(Meal meal, String mealCategory) {
//        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO plan " +
//                                                                                "(meal_option, category, meal_id)" +
//                                                                                "VALUES ( ?, ?, ? );")) {
//
//            preparedStatement.setString(1, meal.getName());
//            preparedStatement.setString(2, mealCategory);
//            preparedStatement.setInt(3, meals.get(meal));
//
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    private Map<Integer, String> getMealsFromCategory(String mealsCategory) {
        return mealDAO.getMeals(mealsCategory);
    }

    private void printMeals(Map<Integer, String> mealsFromCategory) {
        for (String meal : mealsFromCategory.values()) {
            System.out.println(meal);
        }
    }




}
