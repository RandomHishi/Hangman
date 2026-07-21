package ph.edu.dlsu.lbycpob;
import ph.edu.dlsu.lbycpob.game.Hangman;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * Hello resource files! [EXAMPLE ONLY]
 * src/main/java/ph/edu/dlsu/lbycpob/App.java
 */
public class App
{
    public static void main( String[] args )
    {
        try (InputStream input = App.class.getResourceAsStream("/game-assets/hangman-art/display0.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(input)))
        {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}