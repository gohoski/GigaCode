import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@WebServlet("/exercise")
public class ExerciseServlet extends HttpServlet {
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
            int id = database.getExercisesCount() - 1;
            Exercise exercise = database.getExercise(id);
            req.setAttribute("type", exercise.type);
            req.setAttribute("data", exercise.data);
            req.setAttribute("id", id);
            req.getRequestDispatcher("/webapp/exercise.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "application/json");
        try {
            Exercise exercise = database.getExercise(Integer.parseInt(req.getParameter("id")));
            ArrayList<String> classes = new ArrayList<>();
            for (Object o : exercise.data) {
                classes.add(req.getParameter(((JSONObject) o).getString("name")));
            }
            JSONObject json = new JSONObject();
            String[] result = exercise.checkAnswer(classes);
            json.put("success", true);
            json.put("stdout", result[0]);
            String stderr = "";
            try {
                stderr = result[1].split("Exception in thread \"main\" ")[1].split(":")[0];
            } catch(Exception ignored) {
                try {
                    stderr = result[1].split("Error: ")[1].split("\n")[0];
                } catch(Exception ignored1) {}
            }
            json.put("stderr", stderr);
            resp.getWriter().append(json.toString());
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("success", false);
            json.put("errorMessage", e.getLocalizedMessage());
            json.put("errorTrace", e.getStackTrace());
            resp.getWriter().append(json.toString());
        }
    }

    private int generateRandom(int start, int end, ArrayList<Integer> excludeRows) {
        Random rand = new Random();
        int range = end - start + 1;

        int random = rand.nextInt(range) + 1;
        while (excludeRows.contains(random)) {
            random = rand.nextInt(range) + 1;
        }

        return random;
    }
}
