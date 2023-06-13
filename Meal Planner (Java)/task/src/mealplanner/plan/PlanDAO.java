package mealplanner.plan;

import mealplanner.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PlanDAO {
    Connection connection;

    public void getConnection(Connection connection) {
        this.connection = connection;
    }

    public void deletePlan() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE TABLE plan")){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createNewPlan() {
        deletePlan();

    }

    public void createPlanForDay(String day, String mealCategory, Scanner scanner) {
        String question = String.format("Choose the %s for %s from the list above:", mealCategory, day);
        Utility.getInfoFromUser(question, scanner);
    }


}
