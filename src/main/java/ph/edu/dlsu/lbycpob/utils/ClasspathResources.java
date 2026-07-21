package ph.edu.dlsu.lbycpob.utils;
import ph.edu.dlsu.lbycpob.render.AsciiArtRenderer;
import ph.edu.dlsu.lbycpob.repository.ClasspathWordRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * One small, focused job: read every line of a text file that is bundled
 * inside the application as a classpath resource - i.e. packaged inside the
 * jar and found with {@code getResourceAsStream}, as opposed to a real file
 * sitting on the user's filesystem.
 *
 * <p>Both {@link AsciiArtRenderer} (hangman pictures) and
 * {@link ClasspathWordRepository} (the built-in word lists) need this exact
 * same handful of lines of low-level I/O code, so it lives here once
 * instead of being copy-pasted into both classes. This is Single
 * Responsibility at work: "how do I read a bundled text resource" is its
 * own concern, separate from "what do I do with those lines once I have
 * them" (draw a picture vs. pick a random word).
 */
public final class ClasspathResources {
    private ClasspathResources() {
// Utility class - never instantiated.

    }

/** Reads every line of the classpath resource at {@code resourcePath}
 * (e.g. {@code "/game-assets/words/test.txt"}).
 *
 * @throws IOException if the resource does not exist or cannot be read
 */
public static List<String> readLines(String resourcePath) throws IOException {
    Objects.requireNonNull(resourcePath, "resourcePath must not be null");
// try-with-resources: both streams are closed automatically, even
// if readLine() throws.
    try (InputStream input =
                 ClasspathResources.class.getResourceAsStream(resourcePath)) {
        if (input == null) {
            throw new IOException("Resource not found on the classpath: " +
                    resourcePath);
        }
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new
                InputStreamReader(input, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
}