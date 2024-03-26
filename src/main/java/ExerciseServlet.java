import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/exercise")
public class ExerciseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Exercise exercise = new Exercises().get(0);
            req.setAttribute("type", exercise.type);
            req.setAttribute("data", exercise.data);
            req.getRequestDispatcher("/webapp/exercise.jsp").forward(req, resp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Exercise exercise = new Exercises().get(Integer.parseInt(req.getParameter("id")));
            ArrayList<String> classes = new ArrayList<>();
            for (int i = 0; i < exercise.data.size(); i++)
                classes.add(req.getParameter((String) ((JSONObject) exercise.data.get(i)).get("name")));
            System.out.println(classes);
            resp.setHeader("Content-Type", "application/json");
            JSONObject json = new JSONObject();
            String[] result = exercise.checkAnswer(classes);
            json.put("stdout", result[0]);
            json.put("stderr", result[1]);
            resp.getWriter().append(json.toJSONString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
