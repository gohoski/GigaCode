import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static String dbPath = "jdbc:sqlite:gigacode.db";
    private static Database instance = null;

    public static synchronized Database getInstance() throws SQLException {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    private Connection connection;

    private Database() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(dbPath);
        this.connection.createStatement().execute("CREATE TABLE IF NOT EXISTS exercises (" +
                "id integer NOT NULL PRIMARY KEY," +
                "test string");
        this.connection.createStatement().execute("CREATE TABLE IF NOT EXISTS classes (" +
                "exerciseId integer NOT NULL PRIMARY KEY," +
                "classes NOT NULL");
    }

    private Database(String dbPath) throws SQLException {
        Database.dbPath = dbPath;
        new Database();
    }

    public getExercise()
}
