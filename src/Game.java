import javax.swing.Timer;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class Game {
    private String currentWord;
    private char[] guessedWord;
    private int incorrectGuesses;
    private final int maxIncorrectGuesses = 6;
    private ArrayList<Character> guessedLetters;
    private GameFrame gameFrame;
    private Dictionary dict;

    private final static Color DARK_GREEN = new Color(0, 153, 0);
    private final static String[] hangmanStates = {
            "+--------+\n|----------|\n             |\n             |\n             |\n             |",
            "+--------+\n|----------|\nO          |\n             |\n             |\n             |",
            "+--------+\n|----------|\nO          |\n|            |\n             |\n             |",
            "+--------+\n|----------|\nO          |\n/|           |\n             |\n             |",
            "+--------+\n|----------|\nO          |\n/|\\          |\n             |\n             |",
            "+--------+\n|----------|\nO          |\n/|\\          |\n/            |\n             |",
            "+--------+\n|----------|\nO          |\n/|\\          |\n/ \\          |\n             |"
    };

    public Game(Dictionary dict) {
        this.dict = dict;
        this.guessedLetters = new ArrayList<>();
        this.gameFrame = new GameFrame(800, 600);
    }

    private void initialize() {
        currentWord = dict.getNextWord();

        incorrectGuesses = 0;
        guessedLetters.clear();
        guessedWord = new char[currentWord.length()];
        for (int i = 0; i < guessedWord.length; i++)
            guessedWord[i] = '_';

        gameFrame.setInputFieldEditable(false);
        displayGuessedWord();
        displayHangman();
        displayUsedLetters();
        gameFrame.setInputFieldEditable(true);
    }

    private void restart(boolean won) {
        if (won) {
            // this guess resulted in game win for the player
            gameFrame.updateMessage("Correct! The word was: " + currentWord + ". Onto the next word...",
                    DARK_GREEN);

            gameFrame.setInputFieldEditable(false);
            Timer timer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    initialize();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
        else {
            gameFrame.updateMessage("Incorrect. The word was: " + currentWord + ". Onto the next word...",
                    Color.RED);
            initialize();
        }
    }

    public void run() {
        initialize();

        gameFrame.addInputActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = gameFrame.getInputText();
                char guess = input.charAt(0);

                gameFrame.setInputText("");
                if (guessLetter(guess))
                    gameFrame.updateMessage("Correct guess!", DARK_GREEN);
                else
                    gameFrame.updateMessage("Incorrect guess.", Color.RED);
                if (isGameOver())
                    restart(isGameWon());
            }
        });

        gameFrame.showFrame();
    }

    public void displayHangman() {
        gameFrame.updateHangman(hangmanStates[incorrectGuesses]);
    }

    public void displayGuessedWord() {
        if (guessedWord != null) {
            StringBuilder strong = new StringBuilder();
            for (char c : guessedWord) {
                strong.append(c).append(" ");
            }
            gameFrame.updateGuessedWord(strong.toString());
        }
    }

    public boolean guessLetter(char letter) {
        guessedLetters.add(letter);
        displayUsedLetters();

        boolean correct = false;

        for (int i = 0; i < currentWord.length(); i++) {
            if (currentWord.charAt(i) == letter) {
                guessedWord[i] = letter;
                correct = true;
            }
        }
        if (!correct)
            incorrectGuesses++;
        displayHangman();
        displayGuessedWord();
        return correct;
    }

    public void displayUsedLetters() {
        StringBuilder usedLetters = new StringBuilder();
        for (char c : guessedLetters) {
            usedLetters.append(c).append(" \n");
        }
        gameFrame.updateUsedLetters(usedLetters.toString());
    }

    public boolean isGameWon() {
        for (char c : guessedWord) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    public boolean isGameLost() {
        return incorrectGuesses >= maxIncorrectGuesses;
    }

    public boolean isGameOver() {
        return isGameLost() || isGameWon();
    }
}
