import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Dictionary {
    private ArrayList<String> words;
    private String currentWord;
    private String dictFileName;

    public Dictionary(String dictFileName) {
        words = new ArrayList<>();
        this.dictFileName = dictFileName;
    }

    public void load() {
        try {
            Scanner fileScanner = new Scanner(new File(dictFileName));
            while (fileScanner.hasNext()) {
                words.add(fileScanner.next());
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public String getNextWord() {
        Random rand = new Random();
        int index = rand.nextInt(words.size());

        currentWord = words.get(index);
        return currentWord;
    }

    public String getCurrentWord() {
        return currentWord;
    }
}