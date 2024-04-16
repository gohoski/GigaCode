import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import org.json.JSONArray;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class Exercise {
    public int id;
    public String type;
    public JSONArray data;
    public String test;
    private static Runtime runtime;
    private static String[] allowedClasses;
    JavaProjectBuilder builder = new JavaProjectBuilder();

    public Exercise(int id, String type, JSONArray data, String test, String[] allowedClasses) {
        this.id = id;
        this.type = type;
        this.data = data;
        this.test = test;
        Exercise.allowedClasses = allowedClasses;
        runtime = Runtime.getRuntime();
    }

    public String[] checkAnswer(ArrayList<String> classes) throws IOException {
        for (int i = 0; i < classes.size(); i++) {
            String currentClass = classes.get(i);
            writeToFile("./temp/" + data.getJSONObject(i).getString("name"), currentClass);
            for (String allowedClass: allowedClasses) {
                if (currentClass.contains(allowedClass + ".") ||
                currentClass.contains(allowedClass + " ") ||
                currentClass.contains(allowedClass + ";")) {

                }
            }
            JavaClass javaClass = builder.addSource(new StringReader(currentClass)).getClasses().get(0);
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
        Process proc = runtime.exec(cmd);

        Scanner input = new Scanner(proc.getInputStream()).useDelimiter("\\A");
        result[0] += input.hasNext() ? input.next() : "";

        Scanner error = new Scanner(proc.getErrorStream()).useDelimiter("\\A");
        result[1] += error.hasNext() ? error.next() : "";

        return result;
    }
}