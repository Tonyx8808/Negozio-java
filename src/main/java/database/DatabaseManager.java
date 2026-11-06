package database;

import java.sql.*;

public class DatabaseManager {

    private static String baseUrl = "jdbc:mysql://localhost:3307/";
    private static String user = "root";
    private static String password = "root";

    public static Connection getConnection(String nomeDatabase) {

        String url = baseUrl + nomeDatabase;

        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            IO.println("Connessione al database " + nomeDatabase + " riuscita!");

            return connection;

        } catch (SQLException e) {

            IO.println("Qualcosa è andato storto");
            e.printStackTrace();

            return null;
        }
    }
}