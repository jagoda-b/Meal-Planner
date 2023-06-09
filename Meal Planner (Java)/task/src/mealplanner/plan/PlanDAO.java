package mealplanner.plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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


}
