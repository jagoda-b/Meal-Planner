package mealplanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.*;



public class Main {
    public static void main(String[] args) throws SQLException {
        String DB_URL = "jdbc:postgresql:meals_db";
        String USER = "postgres";
        String PASS = "1111";

        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);

        Statement statement = connection.createStatement();


        ResultSet rs = statement.executeQuery("SELECT * FROM meals");
        while (rs.next()) {
            System.out.println("category = " + rs.getString("category"));
            System.out.println("meal = " + rs.getString("meal"));
            System.out.println("meal_id = " + rs.getInt("meal_id"));
        }
        statement.close();
        connection.close();


        Scanner scanner = new Scanner(System.in);
        List<Meal> meals = new ArrayList<>();
        boolean menuFlag = true;

        while (menuFlag){
            String command = Utility.getInfoFromUser("What would you like to do (add, show, exit)?", scanner);


            switch (command) {
                case "add" :
                    Utility.addCommand(scanner, meals);
                    break;

                case "show" :
                    Utility.showCommand(meals);
                    break;

                case "exit" :
                    System.out.println("Bye!");
                    menuFlag = false;
            }

        }

        scanner.close();
    }





}