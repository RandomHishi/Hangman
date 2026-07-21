package ph.edu.dlsu.lbycpob.render;
import ph.edu.dlsu.lbycpob.game.Hangman;
import java.io.IOException;
/**
 * Something that can draw the hangman picture for a given number of
 * guesses remaining. Kept separate from {@link Hangman} so the drawing
 * style (plain text today; maybe a graphical window someday) can change
 * independently of the game rules - Single Responsibility and Open/Closed.
 */
public interface HangmanRenderer {
    /**
     * Draws the hangman art for {@code guessesRemaining} (0-8).
     *
     * @throws IOException if the art cannot be located or read
     */
    void render(int guessesRemaining) throws IOException;
}