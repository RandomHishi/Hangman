package ph.edu.dlsu.lbycpob.game;
import ph.edu.dlsu.lbycpob.render.AsciiArtRenderer;
import ph.edu.dlsu.lbycpob.render.HangmanRenderer;
import ph.edu.dlsu.lbycpob.repository.ClasspathWordRepository;
import ph.edu.dlsu.lbycpob.repository.WordRepository;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Random;

public class Hangman {
    private static final int MAX_GUESSES = 8;
    private static final int INITIAL_GUESSES = 8;
    private final Scanner scanner = new Scanner(System.in);
    private final HangmanRenderer renderer;
    private static final String[] DEFAULT_WORDS = {
            "JAVA", "HANGMAN", "COMPUTER", "KEYBOARD", "PROGRAM", "ALGORITHM"
    };
    private final Random random = new Random();
    private final WordRepository wordRepository;
    public void intro() {
        int totalWidth = 65;
        String[] lines = {
                "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",
                "Welcome to Hangman!",
                "I will think of a random word while you try to guess its letters.",
                "Every time you guess a letter that isn't in my word,",
                "a new body part of the hanging man appears.",
                "Good luck!!!",
                "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
        };
        for (String line : lines) {
            int padding = (totalWidth - line.length()) / 2;

            StringBuilder spaces = new StringBuilder();
            for (int i = 0; i < padding; i++) {
                spaces.append(" ");
            }
            System.out.println(spaces.toString() + line);
        }
        System.out.println();
    }
    public void run() {
        intro();
        String secretWord = getRandomWord("words.txt");
        playOneGame(secretWord);
    }
    public int playOneGame(String secretWord) {
        int guessesLeft = INITIAL_GUESSES;
        StringBuilder guessedLetters = new StringBuilder();
        while (guessesLeft > 0) {
            displayHangman(guessesLeft);
            String currentHint = createHint(secretWord, guessedLetters.toString());
            if (!currentHint.contains("-")) {
                System.out.println("You guessed the word: " + secretWord);
                System.out.println("You win!");
                return guessesLeft;
            }
            System.out.println("Secret word: " + currentHint);
            System.out.println("Your guesses: " + guessedLetters);
            System.out.println("Guesses left: " + guessesLeft);
            // Delegate input reading and validation to readGuess
            char guess = readGuess(guessedLetters.toString());
            guessedLetters.append(guess);
            if (secretWord.indexOf(guess) != -1) {
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect.");
                guessesLeft--;
            }
        }
        displayHangman(0);
        System.out.println("You lost! The secret word was: " + secretWord);
        return 0;
    }
    public char readGuess(String guessedLetters) {
        while (true) {
            System.out.print("Your guess? ");
            String input = scanner.nextLine().trim().toUpperCase();
            // Check if it's a single letter from A-Z
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Type a single letter from A-Z.");
                continue;
            }
            char guess = input.charAt(0);

            // Check if already guessed
            if (guessedLetters.indexOf(guess) != -1) {
                System.out.println("You already guessed that letter.");
                continue;
            }
            return guess;
        }
    }
    public String createHint(String secretWord, String guessedLetters) {
        StringBuilder hint = new StringBuilder();
        for (int i = 0; i < secretWord.length(); i++) {
            char ch = secretWord.charAt(i);
            if (guessedLetters.indexOf(String.valueOf(ch)) != -1) {
                hint.append(ch);
            } else {
                hint.append("-");
            }
        }
        return hint.toString();
    }
    public Hangman() {
        this("/game-assets/hangman-art");
    }
    public Hangman(String resourceBasePath) {
        this.renderer = new AsciiArtRenderer(resourceBasePath);
    }
    public void displayHangman(int guessCount) {
        if (guessCount < 0 || guessCount > MAX_GUESSES) {
            throw new IllegalArgumentException(
                    "guessCount must be between 0 and " + MAX_GUESSES + ", got " + guessCount
            );
        }
        assert guessCount >= 0 && guessCount <= MAX_GUESSES : "guessCount out of range after validation - this is a bug";

        try {
            renderer.render(guessCount);
        } catch (IOException e) {
            throw new RuntimeException("Could not display the hangman picture.", e);
        }
    }
    public String getRandomWord(String filename) {
        Objects.requireNonNull(filename, "filename must not be null");
        if (filename.isBlank()) {
            throw new IllegalArgumentException("filename must not be blank");
        }
        try {
            return wordRepository.getRandomWord(filename);
        } catch (IOException e) {
            // Recovery: a missing/empty/unreadable word file should not
            // crash the whole program. Tell the player clearly (using the
            // exception's own message - no custom exception type needed),
            // then fall back to a small built-in word list.
            System.out.println("Could not load words from \"" + filename + "\": " + e.getMessage());
            System.out.println("Using a built-in default word instead.");
            return DEFAULT_WORDS[random.nextInt(DEFAULT_WORDS.length)];
        }
    }
}