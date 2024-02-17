import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class Exercises {
    Scanner sc;

    Exercises() throws FileNotFoundException {
        sc = new Scanner(new File("../resources/exercises.txt"));
    }
    Exercises(String path) throws FileNotFoundException {
        sc = new Scanner(new File(path));
    }
}
