import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
            /*database.setExercise(0,"scheme",new JSONArray("[{\n" +
                    "    \"name\":\"Animal\",\n" +
                    "    \"variables\":[\n" +
                    "        {\n" +
                    "            \"name\": \"name\",\n" +
                    "            \"type\": \"String\",\n" +
                    "            \"modifier\": \"protected\"\n" +
                    "        },{\n" +
                    "            \"name\": \"calories\",\n" +
                    "            \"type\": \"int\",\n" +
                    "            \"modifier\": \"protected\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"functions\":[\n" +
                    "        {\n" +
                    "            \"name\":\"eat\",\n" +
                    "            \"modifier\":\"protected\",\n" +
                    "            \"variables\":[\n" +
                    "                {\n" +
                    "                    \"name\": \"calories\",\n" +
                    "                    \"type\": \"int\"\n" +
                    "                }, {\n" +
                    "                    \"name\": \"calories\",\n" +
                    "                    \"type\": \"int\"\n" +
                    "                },{\n" +
                    "                    \"name\": \"calories\",\n" +
                    "                    \"type\": \"int\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    ]\n" +
                    "},{\n" +
                    "      \"name\":\"Cat\",\n" +
                    "      \"variables\":[\n" +
                    "          {\n" +
                    "              \"name\": \"name\",\n" +
                    "              \"type\": \"String\",\n" +
                    "              \"modifier\": \"protected\"\n" +
                    "          },{\n" +
                    "              \"name\": \"calories\",\n" +
                    "              \"type\": \"int\",\n" +
                    "              \"modifier\": \"protected\"\n" +
                    "          }\n" +
                    "      ],\n" +
                    "      \"functions\":[\n" +
                    "          {\n" +
                    "              \"name\":\"eat\",\n" +
                    "              \"modifier\":\"protected\",\n" +
                    "              \"variables\":[\n" +
                    "                  {\n" +
                    "                      \"name\": \"calories\",\n" +
                    "                      \"type\": \"int\"\n" +
                    "                  }, {\n" +
                    "                      \"name\": \"calories\",\n" +
                    "                      \"type\": \"int\"\n" +
                    "                  },{\n" +
                    "                      \"name\": \"calories\",\n" +
                    "                      \"type\": \"int\"\n" +
                    "                  }\n" +
                    "              ]\n" +
                    "          }\n" +
                    "      ]\n" +
                    "},{\n" +
                    "       \"name\":\"Tiger\",\n" +
                    "       \"variables\":[\n" +
                    "           {\n" +
                    "               \"name\": \"name\",\n" +
                    "               \"type\": \"String\",\n" +
                    "               \"modifier\": \"protected\"\n" +
                    "           },{\n" +
                    "               \"name\": \"calories\",\n" +
                    "               \"type\": \"int\",\n" +
                    "               \"modifier\": \"protected\"\n" +
                    "           }\n" +
                    "       ],\n" +
                    "       \"functions\":[\n" +
                    "           {\n" +
                    "               \"name\":\"eat\",\n" +
                    "               \"modifier\":\"protected\",\n" +
                    "               \"variables\":[\n" +
                    "                   {\n" +
                    "                       \"name\": \"calories\",\n" +
                    "                       \"type\": \"int\"\n" +
                    "                   }, {\n" +
                    "                       \"name\": \"calories\",\n" +
                    "                       \"type\": \"int\"\n" +
                    "                   },{\n" +
                    "                       \"name\": \"calories\",\n" +
                    "                       \"type\": \"int\"\n" +
                    "                   }\n" +
                    "               ]\n" +
                    "           }\n" +
                    "       ]\n" +
                    "}]"), "public class Main {\n" +
                    "    public static void main(String args[]) {\n" +
                    "        new Cat().main(null);\n" +
                    "        new Animal().main(null);\n" +
                    "        new Tiger().main(null);\n" +
                    "        System.out.print(\"SUCCESS:TRUE!\");\n" +
                    "    }\n" +
                    "}");*/
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
        try {
            Exercise exercise = database.getExercise(Integer.parseInt(req.getParameter("id")));
            ArrayList<String> classes = new ArrayList<>();
            for (Object o : exercise.data) {
                classes.add(req.getParameter(((JSONObject) o).getString("name")));
            }
            resp.setHeader("Content-Type", "application/json");
            JSONObject json = new JSONObject();
            String[] result = exercise.checkAnswer(classes);
            json.put("stdout", result[0]);
            json.put("stderr", result[1].split("Exception in thread \"main\" ")[1].split(":")[0]);
            resp.getWriter().append(json.toString());
        } catch (SQLException e) {
            e.printStackTrace();
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
