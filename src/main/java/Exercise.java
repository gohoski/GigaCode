import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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

    public String[] checkAnswer(ArrayList<String> classes) throws IOException {
        for (int i = 0; i < classes.size(); i++) {
            writeToFile("./temp/" + ((JSONObject) data.get(i)).get("name"),
                    classes.get(i));
        }
        writeToFile("./temp/Main", this.test);
        execCmd("javac ./temp/*.java");
        return execCmd("java -cp ./temp/ Main");
    }

    private static void writeToFile(String path, String content) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(path + ".java", false));
        printWriter.write(content);
        printWriter.close();
    }

    private static String[] execCmd(String cmd) throws IOException {
        String[] result = new String[2];
        Arrays.fill(result, "");
        Process proc = Runtime.getRuntime().exec(cmd);

        Scanner input = new Scanner(proc.getInputStream()).useDelimiter("\\A");
        result[0] += input.hasNext() ? input.next() : "";

        Scanner error = new Scanner(proc.getErrorStream()).useDelimiter("\\A");
        result[1] += error.hasNext() ? error.next() : "";
        return result;
    }
}