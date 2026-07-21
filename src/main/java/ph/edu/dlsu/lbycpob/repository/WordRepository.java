package ph.edu.dlsu.lbycpob.repository;
import ph.edu.dlsu.lbycpob.game.Hangman;
import java.io.IOException;
/**
 * Something that can hand back a random secret word.
 *
 * <p>This is the one collaborator worth hiding behind an interface in this
 * project: today words come from a text file, but tomorrow they could come
 * from an array, a database, or a web service - and {@link Hangman} should
 * not have to change at all when that happens. That is the Open/Closed
 * Principle (new word sources can be added without touching {@code Hangman})
 * and the Dependency Inversion Principle ({@code Hangman} depends on this
 * interface, not on file-reading code) working together.
 *
 * <p>{@code throws IOException} is the same, ordinary checked exception you
 * already use for any file reading in Java - no custom exception classes
 * needed.
 */
public interface WordRepository {
    /**
     * Returns one random, upper-cased word read from {@code filename}.
     *
     * @throws IOException if the file does not exist, cannot be read, or
     * contains no usable words
     */
    String getRandomWord(String filename) throws IOException;
}