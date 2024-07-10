import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameFrame extends JFrame {
    private JTextArea hangmanText;
    private JTextArea usedLettersText;
    private JLabel guessedWordLabel;
    private JLabel messageLabel;
    private JTextField inputField;
    private JPanel bottomPanel;
    private int width, height;
    private static final Color LIGHT_BLUE = new Color(51, 204, 255);

    public GameFrame(int width, int height) {
        this.width = width;
        this.height = height;

        setTitle("Hangman");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Area for hangman display
        hangmanText = new JTextArea(10, 20);
        hangmanText.setEditable(false);
        hangmanText.setFont(new Font("Arial", Font.PLAIN, 40));
        hangmanText.setForeground(LIGHT_BLUE);
        hangmanText.setBackground(Color.DARK_GRAY);
        add(new JScrollPane(hangmanText), BorderLayout.CENTER);

        // Area for guessed word
        guessedWordLabel = new JLabel(" ");
        guessedWordLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        add(guessedWordLabel, BorderLayout.NORTH);

        // Input message and field
        messageLabel = new JLabel("Enter a letter: ");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        inputField = new JTextField();

        // Bottom panel includes input message and field
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(messageLabel, BorderLayout.WEST);
        bottomPanel.add(inputField, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Used letters txt
        usedLettersText = new JTextArea();
        usedLettersText.setEditable(false);
        usedLettersText.setFont(new Font("Arial", Font.PLAIN, 25));
        usedLettersText.setForeground(Color.MAGENTA);
        usedLettersText.setBackground(Color.WHITE);
        add(new JScrollPane(usedLettersText), BorderLayout.WEST);
    }

    public void updateHangman(String hangmanState) {
        String[] lines = hangmanState.split("\n");
        String f = String.format("%50s", " ");

        for (int i = 0; i < lines.length; i++){
            String s = String.format("%s%s\n", f, lines[i]);
            if (i == 0)
                hangmanText.setText(s);
            else
                hangmanText.append(s);
        }
    }

    public void updateGuessedWord(String guessedWord) {
        String s = String.format("%50s %s", "Word: ", guessedWord);

        guessedWordLabel.setText(s);
    }

    public void updateMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setForeground(color);
    }

    public void updateUsedLetters(String usedLetters) {
        usedLettersText.setText(usedLetters);
    }

    public String getInputText() {
        return inputField.getText();
    }

    public void setInputText(String text) {
        inputField.setText(text);
    }

    public void addInputActionListener(ActionListener listener) {
        inputField.addActionListener(listener);
    }

    public void setInputFieldEditable(boolean editable) {
        inputField.setEditable(editable);
    }

    public void showFrame() {
        setVisible(true);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}