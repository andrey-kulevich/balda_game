import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class BaldaGame {
    public static void main(String[] args) throws FileNotFoundException {

        Dictionary _dict = new Dictionary();
        try {
            _dict.load("./dictionaries/customDictionary.txt");
        } catch (FileNotFoundException ignored) { }

        System.out.println(_dict.hasWord("1"));
        System.out.println(_dict.hasWord("1"));
        System.out.println(_dict.hasWord("hey"));
    }
}
