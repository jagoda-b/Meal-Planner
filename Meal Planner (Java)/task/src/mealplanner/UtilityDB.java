package mealplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilityDB {
    private final String dbUrl = "jdbc:postgresql:meals_db";
    private final String user = "postgres";
    private final String pass = "1111";

    Connection connection = null;

    protected Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(dbUrl, user, pass);
            connection.setAutoCommit(true);
        }
        createTables();
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }

    public void createTables() {

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS meals(category VARCHAR(64), meal VARCHAR(64), meal_id INTEGER);");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ingredients(ingredient VARCHAR(1024), ingredient_id INT, meal_id INTEGER);");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS plan(meal_option VARCHAR(1024), category VARCHAR(64), meal_id INTEGER);");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
