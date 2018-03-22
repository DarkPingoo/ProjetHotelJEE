package eseo.sw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Database ourInstance = null;

    public static Database getInstance() {
        if(ourInstance == null) {
            ourInstance = new Database();
        }
        return ourInstance;
    }

    private final String connectURL = "jdbc:mysql://localhost/Hotel?user=user&password=user";

    /**
     * Singleton qui permet d'initialiser une seule et unique fois le driver
     */
    private Database() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(this.connectURL);
    }
}
