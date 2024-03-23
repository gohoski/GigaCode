import org.json.simple.JSONArray;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Exercise {
    public int id;
    public String type;
    public JSONArray data;
    public String test;

    public Exercise(int id, String type, JSONArray data, String test) {
        this.id = id;
        this.type = type;
        this.data = data;
        this.test = test;
    }

    public boolean checkAnswer(String[] classes) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileOutputStream("/result/Main.java", false));
        printWriter.write(test);
        printWriter.close();
        Runtime.getRuntime().exec(new String[]{"javac", "/result/Main.java"});
        return true;
    }
}