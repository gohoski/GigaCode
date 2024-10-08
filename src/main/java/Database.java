import org.json.JSONArray;
import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;

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
                "id INTEGER NOT NULL PRIMARY KEY, " +
                "type TEXT, " +
                "classes JSON NOT NULL, " +
                "test TEXT" +
                ")");
    }

    private Database(String dbPath) throws SQLException {
        Database.dbPath = dbPath;
        new Database();
    }

    public Exercise getExercise(int id) throws SQLException {
        PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM \"exercises\" WHERE \"id\" = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery(); rs.next();
        return new Exercise(id, rs.getString("type"), new JSONArray(rs.getString("classes")), rs.getString("test"),
                new String[]{"Runtime", "ProcessBuilder", "Process", "Properties"});
    }

    public int setExercise(int id, String type, JSONArray classes, String test) throws SQLException {
        PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO exercises(`id`, `type`, `classes`, `test`) " +
                "VALUES (?, ?, json(?), ?)");
        stmt.setInt(1, id);
        stmt.setString(2, type);
        stmt.setString(3, classes.toString());
        stmt.setString(4, test);
        return stmt.executeUpdate();
    }

    public int getExercisesCount() throws SQLException {
        ResultSet rs = this.connection.createStatement().executeQuery("SELECT count(id) AS total FROM exercises");
        rs.next();return rs.getInt("total");
    }

    public ArrayList<Exercise> getExercises() throws SQLException {
        ResultSet rs = this.connection.createStatement().executeQuery("SELECT id, type, classes, test FROM exercises");
        ArrayList<Exercise> exercises = new ArrayList<>();
        while (rs.next())
            exercises.add(new Exercise(rs.getInt("id"), rs.getString("type"), new JSONArray(rs.getString("classes")), rs.getString("test"), new String[]{"Runtime", "ProcessBuilder", "Process", "Properties"}));
        return exercises;
    }
}
