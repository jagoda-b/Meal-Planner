package mealplanner;


import mealplanner.meal.MealDAO;
import mealplanner.plan.PlanDAO;

import java.util.Scanner;
import java.sql.*;



public class Main {
    public static void main(String[] args) throws SQLException {
        Database database = new Database();
        Connection connection = database.getConnection();
        MealDAO mealDAO = new MealDAO(connection);
        PlanDAO planDAO = new PlanDAO(connection, mealDAO );



        Scanner scanner = new Scanner(System.in);

        Utility.menu(mealDAO, planDAO, scanner);

        scanner.close();
        database.closeConnection();

    }




}