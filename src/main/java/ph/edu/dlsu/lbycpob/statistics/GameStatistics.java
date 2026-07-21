package ph.edu.dlsu.lbycpob.statistics;
import java.util.Locale;

/**
 * Holds the running statistics for one program session: games played,
 * games won, and the best (highest) number of guesses remaining at the end
 * of any single game.
 */
public record GameStatistics(int gamesPlayed, int gamesWon, int bestGuessesRemaining) {
    public GameStatistics {
        if (gamesPlayed < 0) {
            throw new IllegalArgumentException("gamesPlayed must be >= 0, got " + gamesPlayed);
        }
        if (gamesWon < 0 || gamesWon > gamesPlayed) {
            throw new IllegalArgumentException(
                    "gamesWon must be between 0 and gamesPlayed (" + gamesPlayed + "), got " + gamesWon);
        }
        if (bestGuessesRemaining < 0) {
            throw new IllegalArgumentException("bestGuessesRemaining must be >= 0, got " + bestGuessesRemaining);
        }
    }
    public static GameStatistics empty() {
        return new GameStatistics(0, 0, 0);
    }
    public GameStatistics withGame(boolean won, int guessesRemaining) {
        if (guessesRemaining < 0) {
            throw new IllegalArgumentException("guessesRemaining must be >= 0, got " + guessesRemaining);
        }
        int newBest = (gamesPlayed == 0) ? guessesRemaining : Math.max(bestGuessesRemaining, guessesRemaining);
        return new GameStatistics(gamesPlayed + 1, gamesWon + (won ? 1 : 0), newBest);
    }
    public double winPercentage() {
        return (gamesPlayed == 0) ? 0.0 : (gamesWon * 100.0) / gamesPlayed;
    }
    public String formattedWinPercentage() {
        return String.format(Locale.ROOT, "%.1f%%", winPercentage());
    }
}
