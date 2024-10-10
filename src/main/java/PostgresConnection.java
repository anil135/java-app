import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgresConnection {
    public static void main(String[] args) {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Hello, connected to the database!");
            
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM test");

            while (resultSet.next()) {
                System.out.println("Data: " + resultSet.getString("data_column"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Sorry, database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
