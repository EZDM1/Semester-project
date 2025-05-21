package db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {   
    private static final String driver = "jdbc:sqlserver://localhost:1433";
    private static final String databaseName = ";databaseName=qb_aps";
    private static String userName = "; user=sa";
    private static String password = ";password=E1j2a3z4!220122";
    private static String encryption = ";encrypt=false";
   
    private DatabaseMetaData dma;
    private static Connection con;
    private static DBConnection instance = null;

    private DBConnection() {
        String url = driver + databaseName + userName + password + encryption;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Load af class ok");
        }
        catch(Exception e) {
            System.out.println("Can not find the driver");
            System.out.println(e.getMessage());
        }
        
        try {
            con = DriverManager.getConnection(url);
            con.setAutoCommit(true);
            dma = con.getMetaData();
            System.out.println("Connection to " + dma.getURL());
            System.out.println("Driver " + dma.getDriverName());
            System.out.println("Database product name " + dma.getDatabaseProductName());
        }
        catch(Exception e) {
            System.out.println("Problems with the connection to the database");
            System.out.println(e.getMessage());
            System.out.println(url);
        }
    }
   
    public static void closeConnection() {
        try {
            con.close();
            System.out.println("The connection is closed");
        }
        catch (Exception e) {
            System.out.println("Error trying to close the database " + e.getMessage());
        }
    }
		
    public Connection getConnection() {
        return con;
    }
    
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
    
    public static void startTransaction() {
        try {
            con.setAutoCommit(false);
        } catch(SQLException e) {
            System.out.println("Start transaction failure");
            System.out.println(e.getMessage());
        }
    }
    
    public static void commitTransaction() throws SQLException {
        try {
            con.commit();
        }
        catch(SQLException e) {
            System.out.println("Commit transaction failure");
            System.out.println(e.getMessage());
        } finally {
            con.setAutoCommit(true);
        }
    }
    
    public static void rollbackTransaction() throws SQLException { 
        try {
            con.rollback();
        }
        catch(Exception e) {
            System.out.println("Rollback transaction failure");
            System.out.println(e.getMessage());
        } finally {
            con.setAutoCommit(true);
        }
    }
}