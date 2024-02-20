import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;

public class Exercises {
    HashMap<Integer, Exercise> exercises;

    Exercises() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("../resources/exercises.txt"));

    }
    Exercises(String path) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(path));
    }

    private HashMap<Integer, Exercise> parse(Scanner sc) {
        HashMap<Integer, Exercise> hashmap = new HashMap<>();

        String file = "";
        while (sc.hasNextLine()){
            file += sc.nextLine();
        }
        String[] arr = file.split("\\|");
        for (int i = 0; i < arr.length; i++) {

        }

        return hashmap;
    }

    Exercise get(int id) {

    }
}
