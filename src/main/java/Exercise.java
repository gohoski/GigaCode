import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;
import org.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Exercise {
    public int id;
    public String type;
    public JSONArray data;
    public String test;
    private static Runtime runtime;
    private static String[] illegalClasses;
    JavaProjectBuilder builder = new JavaProjectBuilder();

    public Exercise(int id, String type, JSONArray data, String test, String[] illegalClasses) {
        this.id = id;
        this.type = type;
        this.data = data;
        this.test = test;
        Exercise.illegalClasses = illegalClasses;
        runtime = Runtime.getRuntime();
    }

    public String[] checkAnswer(ArrayList<String> classes) throws Exception {
        //System.out.println(classes);
        String tempId = NanoIdUtils.randomNanoId();
        File dir = new File("./temp/" + tempId);
        dir.mkdirs();
        for (int i = 0; i < classes.size(); i++) {
            String currentClass = classes.get(i);
            writeToFile("./temp/" + tempId + "/" + data.getJSONObject(i).getString("name"), currentClass);
            JavaSource javaSource = builder.addSource(new StringReader(currentClass));
            List<String> imports = javaSource.getImports();
            for (String illegalClass: illegalClasses) {
                String[] illegalClassUses = new String[]{
                        illegalClass + ".",
                        illegalClass + ";",
                        illegalClass + "(",
                        illegalClass + " ",
                        illegalClass + "="
                };
                if (stringContains(currentClass, illegalClassUses) ||
                    stringContains(imports.toString(), illegalClassUses)) {
                    FileUtils.deleteDirectory(dir);
                    throw new Exception("Недопустимые классы");
                }
            }
        }
        writeToFile("./temp/" + tempId + "/Main", this.test);
        String[] compilationResult = execCmd("javac ./temp/" + tempId + "/*.java");
        System.out.println(Arrays.toString(compilationResult));
        if (compilationResult[1].length() > 1) {
            FileUtils.deleteDirectory(dir);
            return compilationResult;
        }
        String[] result = execCmd("java -cp ./temp/" + tempId + "/ Main");
        FileUtils.deleteDirectory(dir);
        return result;
    }

    @Override
    public String toString() {
        return new JSONObject(this).toString();
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

    private static boolean stringContains(String inputStr, String[] items) {
        return Arrays.stream(items).anyMatch(inputStr::contains);
    }
    private static boolean stringContains(String inputStr, List<String> items) {
        return items.stream().anyMatch(inputStr::contains);
    }
}