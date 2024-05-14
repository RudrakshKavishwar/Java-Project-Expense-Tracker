import java.sql.*;

public class DatabaseManager {
    static final String DB_URL = "jdbc:mysql://localhost/java_rud";
    static final String USER = "java_rud";
    static final String PASS = "@qwertyuiop1407";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void createTables() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String createExpenseTableSQL = "CREATE TABLE IF NOT EXISTS Expense (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "description VARCHAR(255) NOT NULL," +
                    "amount DECIMAL(10, 2) NOT NULL" +
                    ")";
            stmt.executeUpdate(createExpenseTableSQL);
            System.out.println("Expense table created successfully");

            String createAnotherTableSQL = "CREATE TABLE IF NOT EXISTS AnotherTable (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "expense_id INT," +
                    "FOREIGN KEY (expense_id) REFERENCES Expense(id)" +
                    ")";
            stmt.executeUpdate(createAnotherTableSQL);
            System.out.println("AnotherTable created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
