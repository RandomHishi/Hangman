package ph.edu.dlsu.lbycpob.game;

public class Hangman {
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
}
