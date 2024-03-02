import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.io.File;

public class Exercises {
    protected ArrayList<Exercise> exercises;

    Exercises() throws IOException, ParseException {
        Properties prop = new Properties();
        prop.load(new FileInputStream("app.config"));
        Scanner sc = new Scanner(new File(prop.getProperty("src_dir") + "/resources/exercises.txt"));
        exercises = parse(sc);
    }
    Exercises(String path) throws FileNotFoundException, ParseException {
        Scanner sc = new Scanner(new File(path));
        exercises = parse(sc);
    }

    private ArrayList<Exercise> parse(Scanner sc) throws ParseException {
        ArrayList<Exercise> exercises = new ArrayList<>();

        StringBuilder file = new StringBuilder();
        while (sc.hasNextLine())
            file.append(sc.nextLine());
        String[] arr = file.toString().split("\\|");
        for (int i = 0; i < arr.length/4; i++) {
            exercises.add(new Exercise(Integer.parseInt(arr[i*4]), arr[i*4 + 1], (JSONObject) new JSONParser().parse(arr[i*4 + 2]), arr[i*4 + 3]));
        }

        return exercises;
    }

    Exercise get(int id) {
        return exercises.get(id);
    }

    int length() {
        return exercises.size();
    }
}
