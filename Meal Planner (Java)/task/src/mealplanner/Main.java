package mealplanner;


import java.util.Scanner;
import java.sql.*;



public class Main {
    public static void main(String[] args) throws SQLException {
        MealDAO mealDAO = new MealDAO();
        mealDAO.getConnection();
        Scanner scanner = new Scanner(System.in);

        Utility.menu(mealDAO, scanner);

        scanner.close();
        mealDAO.closeConnection();

    }




}