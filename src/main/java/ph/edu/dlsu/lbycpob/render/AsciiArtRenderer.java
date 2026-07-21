package ph.edu.dlsu.lbycpob.render;

import ph.edu.dlsu.lbycpob.repository.ClasspathWordRepository;
import ph.edu.dlsu.lbycpob.utils.ClasspathResources;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Default {@link HangmanRenderer}: reads pre-drawn ASCII art files that are
 * bundled inside the application (as classpath resources, e.g.
 * {@code /game-assets/hangman-art/display3.txt}) and prints them to the
 * console.
 *
 * <p>The actual "open this classpath resource and read its lines" work is
 * delegated to {@link ClasspathResources}, the same helper
 * {@link ClasspathWordRepository} uses for the bundled word lists - so this
 * class only contains the part that's actually specific to drawing the
 * hangman picture: validating the guess count and printing the lines.
 */
public final class AsciiArtRenderer implements HangmanRenderer {

    private static final int MIN_GUESSES_REMAINING = 0;
    private static final int MAX_GUESSES_REMAINING = 8;
    private final String resourceBasePath;

    /**
     * @param resourceBasePath classpath folder containing displayN.txt
     * files, e.g. {@code "/game-assets/hangman-art"}
     */
    public AsciiArtRenderer(String resourceBasePath) {
        Objects.requireNonNull(resourceBasePath, "resourceBasePath must not be null");
        if (resourceBasePath.isBlank()) {
            throw new IllegalArgumentException("resourceBasePath must not be blank");
        }
        this.resourceBasePath = resourceBasePath.endsWith("/")
                ? resourceBasePath.substring(0, resourceBasePath.length() - 1)
                : resourceBasePath;
    }

    @Override
    public void render(int guessesRemaining) throws IOException {
        if (guessesRemaining < MIN_GUESSES_REMAINING || guessesRemaining > MAX_GUESSES_REMAINING) {
            throw new IllegalArgumentException(
                    "guessesRemaining must be between " + MIN_GUESSES_REMAINING
                            + " and " + MAX_GUESSES_REMAINING + ", got " +
                            guessesRemaining);
        }
        String resourcePath = resourceBasePath + "/display" + guessesRemaining + ".txt";
        List<String> lines = ClasspathResources.readLines(resourcePath);
        for (String line : lines) {
            System.out.println(line);
        }
    }
}