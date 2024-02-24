import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.parser.ParseException;
import java.io.IOException;

@WebServlet("/exercise")
public class ExerciseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("test", new Exercises().get(0).test);
            req.getRequestDispatcher("/webapp/exercise.jsp").forward(req, resp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
