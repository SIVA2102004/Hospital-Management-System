import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // ✅ Update these if your database name or credentials differ
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db?serverTimezone=UTC";
    private static final String USER = "root";     // default MySQL user in XAMPP
    private static final String PASSWORD = "";     // leave blank unless you set a password

    // ✅ Get database connection safely
    public static Connection getConnection() {
        try {
            // ✅ Load MySQL JDBC Driver manually
            Class.forName("com.mysql.cj.jdbc.Driver");

            // ✅ Establish connection
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to MySQL successfully!");
            return conn;

        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL JDBC Driver not found! Make sure the connector JAR is in your classpath.");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("❌ Database connection failed! Check your MySQL server or credentials.");
            e.printStackTrace();
        }

        return null;
    }

    // ✅ Test connection manually
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("✅ Database connected successfully (Test OK)!");
        } else {
            System.out.println("❌ Connection failed. Please verify database settings.");
        }
    }
}
