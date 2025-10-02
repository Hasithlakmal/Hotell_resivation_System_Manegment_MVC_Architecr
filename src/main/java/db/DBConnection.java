package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instence;
    private final Connection connection;

    private DBConnection() throws SQLException {

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation","root","1234");


    }

    public Connection getConnection() {
        return connection;
    }

    public static DBConnection getDbConnection_instence() throws SQLException {

        if (instence == null) {

            instence = new DBConnection();
        }

        return instence;
    }

}
