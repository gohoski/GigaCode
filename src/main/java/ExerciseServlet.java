import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
            catchException(e, resp);
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
            catchException(e, resp);
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Scanner scanner = new Scanner(req.getInputStream(), "UTF-8");
            String jsonData = scanner.useDelimiter("\\A").next();
            scanner.close();
            JSONObject json = new JSONObject(jsonData);
            System.out.println(json.getString("type"));
            int id = json.getInt("id");
            database.setExercise(id == -1 ? database.getExercisesCount() : id,
                    json.getString("type"),
                    json.getJSONArray("classes"),
                    json.getString("test"));
            JSONObject result = new JSONObject();
            result.put("success", true);
            resp.getWriter().append(result.toString());
        } catch (SQLException e) {
            catchException(e, resp);
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

    private void catchException(Exception e, HttpServletResponse resp) throws IOException {
        JSONObject json = new JSONObject();
        json.put("success", false);
        json.put("errorMessage", e.getLocalizedMessage());
        json.put("errorTrace", e.getStackTrace());
        resp.getWriter().append(json.toString());
    }
}
