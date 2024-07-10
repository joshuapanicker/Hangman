public class Hangman {
    public static void main(String[] args) {
        Dictionary dict = new Dictionary("words.txt");
        dict.load();

        Game game = new Game(dict);
        game.run();
    }
}
