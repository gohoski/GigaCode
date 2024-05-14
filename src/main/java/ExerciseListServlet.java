import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/exercise/list")
public class ExerciseListServlet extends HttpServlet {
    Database database = null;
    {
        try {
            database = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ArrayList<Exercise> exercises = database.getExercises();
            ArrayList<String> types = new ArrayList<>();
            ArrayList<JSONArray> data = new ArrayList<>();
            for (Exercise exercise: exercises) {
                types.add(exercise.type);
                data.add(exercise.data);
            }
            req.setAttribute("types", types);
            req.setAttribute("data", data);
            req.getRequestDispatcher("/webapp/exerciseList.jsp").forward(req, resp);
        } catch (SQLException ignored) {

        }
    }
}
