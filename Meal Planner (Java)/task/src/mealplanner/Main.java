package mealplanner;


import mealplanner.Meal.MealDAO;

import java.util.Scanner;
import java.sql.*;



public class Main {
    public static void main(String[] args) throws SQLException {
        MealDAO mealDAO = new MealDAO();
        UtilityDB utilityDB = new UtilityDB();
        utilityDB.getConnection();
        Scanner scanner = new Scanner(System.in);

        Utility.menu(mealDAO, scanner);

        scanner.close();
        utilityDB.closeConnection();

    }




}