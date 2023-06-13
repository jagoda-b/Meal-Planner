package mealplanner;


import mealplanner.meal.MealDAO;
import mealplanner.plan.PlanDAO;

import java.util.Scanner;
import java.sql.*;



public class Main {
    public static void main(String[] args) throws SQLException {
        UtilityDB utilityDB = new UtilityDB();
        Connection connection = utilityDB.getConnection();
        MealDAO mealDAO = new MealDAO(connection);
        PlanDAO planDAO = new PlanDAO(connection);



        Scanner scanner = new Scanner(System.in);

        Utility.menu(mealDAO, scanner);

        scanner.close();
        utilityDB.closeConnection();

    }




}