import org.json.simple.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Exercise {
    public int id;
    public String type;
    public JSONObject data;
    public String[] tests;

    public Exercise(int id, String type, JSONObject data, String[] tests) {
        this.id = id;
        this.type = type;
        this.data = data;
        this.tests = tests;
    }

    public boolean checkAnswer(String[] classes) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileOutputStream("/result/Main.java", false));
        printWriter.write(tests[0]);
        printWriter.close();
        Runtime.getRuntime().exec("javac /result/Main.java");
        return true;
    }
}
