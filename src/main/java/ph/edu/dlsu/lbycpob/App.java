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
        Hangman hangman = new Hangman();
        hangman.run();
    }
}