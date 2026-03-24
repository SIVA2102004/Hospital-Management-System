import java.sql.*;

public class TestDB {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
            System.out.println("✅ MySQL Connection Successful!");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
