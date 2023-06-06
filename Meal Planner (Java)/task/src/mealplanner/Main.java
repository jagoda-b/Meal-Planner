package mealplanner;


import java.util.Scanner;
import java.sql.*;



public class Main {
    public static void main(String[] args) throws SQLException {
        MealDAO mealDAO = new MealDAO();
        mealDAO.getConnection();
        Scanner scanner = new Scanner(System.in);

        boolean menuFlag = true;

        while (menuFlag){
            String command = Utility.getInfoFromUser("What would you like to do (add, show, exit)?", scanner);

            switch (command) {
                case "add" :
                    Meal meal = Utility.addCommand(scanner);
                    mealDAO.addMealtoDB(meal);
                    break;

                case "show" :
                    mealDAO.showFromDB();
                    break;

                case "exit" :
                    System.out.println("Bye!");
                    menuFlag = false;
            }

        }

        scanner.close();
        mealDAO.closeConnection();

    }





}