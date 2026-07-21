package ph.edu.dlsu.lbycpob.repository;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Reads one word per non-blank line from a real file on the user's
 * filesystem and picks one at random.
 * <p>Uses {@code java.nio.file.Path}/{@code Files} (the modern, recommended
 * way to do file I/O in Java) instead of the older {@code java.io.File}
 * class, and reads the file inside a try-with-resources block so the
 * {@link BufferedReader} is always closed automatically, even if something
 * goes wrong while reading.
 */
public final class FileWordRepository implements WordRepository {
    private final Random random;
    public FileWordRepository() {
        this(new Random());
    }
    /**
     * @param random source of randomness for picking a word
     */
    public FileWordRepository(Random random) {
        this.random = Objects.requireNonNull(random, "random must not be null");
    }
    @Override
    public String getRandomWord(String filename) throws IOException {
        Objects.requireNonNull(filename, "filename must not be null");
        if (filename.isBlank()) {
            throw new IllegalArgumentException("filename must not be blank");
        }
        Path path = Path.of(filename);
        if (!Files.exists(path)) {
            throw new IOException("Word list file does not exist: " + filename);
        }
        if (!Files.isRegularFile(path) || !Files.isReadable(path)) {
            throw new IOException("Word list file is not a readable file: " + filename);
        }
        List<String> words = new ArrayList<>();
        // try-with-resources: the BufferedReader is closed automatically
        // when this block ends, even if readLine() throws.
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    words.add(trimmed.toUpperCase());
                }
            }
        }
        if (words.isEmpty()) {
            throw new IOException("Word list file contains no words: " + filename);
        }
        return words.get(random.nextInt(words.size()));
    }
}