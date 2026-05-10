import java.util.List;
import java.util.Random;

/**
 * Game mode in which, for each valid play, there is at most one
 * elimination reward that depends on the total number of symbols
 * eliminated in that play.
 *
 * Scoring rules (SimpleScoreGame):
 *  - Each valid play always gives {@link #PLAY_SCORE} points (10).
 *  - The list {@code eliminated} contains, for that play,
 *    the number of symbols eliminated in each elimination phase.
 *  - We compute the total eliminated symbols in the play:
 *        total = sum(eliminated)
 *    and, if total >= {@link #SIZE_OF_PIECE} (3), it gives:
 *        {@link #BASE_ELIM_POINTS} + (total - SIZE_OF_PIECE) * {@link #EXTRA_ELIM_POINTS}.
 */
public class SingleScoreGame extends AbstractGame {

    /**
     * Creates a {@code SimpleScoreGame} with the given configuration.
     *
     * @param r      number of rows in the grid
     * @param c      number of columns in the grid
     * @param diff   difficulty level
     * @param empty  symbol that represents an empty cell
     * @param values symbols that may appear in the grid/pieces
     * @param gen    random generator to use
     * @param elim   eliminator strategy
     * @param acc    accomodator strategy
     * @requires r > 0 && c > 0
     * @requires diff >= 0 && diff <= r
     * @requires empty != null && values != null
     * @requires gen != null && elim != null && acc != null
     */
    public SingleScoreGame(int r, int c, int diff,
                           Symbol empty, Symbol[] values,
                           Random gen, Eliminator elim, Accomodator acc) {
        super(r, c, diff, empty, values, gen, elim, acc);
        this.playPoints = 0;
        this.elimPoints = 0;
    }

    /**
     * Updates the score after a play, using the total number of
     * symbols eliminated in that play.
     *
     * @param eliminated list with the number of symbols eliminated
     *                   in each elimination phase
     */
    @Override
    public void registerPlayScore(List<Integer> eliminated) {
        this.playPoints += PLAY_SCORE;

        if (eliminated == null || eliminated.isEmpty()) {
            return;
        }

        int total = 0;
        for (Integer e : eliminated) {
            if (e != null) {
                total += e.intValue();
            }
        }

        if (total >= SIZE_OF_PIECE) {
            int extra = total - SIZE_OF_PIECE;
            this.elimPoints += BASE_ELIM_POINTS + extra * EXTRA_ELIM_POINTS;
        }
    }

    /**
     * Returns the sum of play points and elimination points.
     *
     * @return total score of this game
     */
    @Override
    public int score() {
        return this.playPoints + this.elimPoints;
    }

    /**
     * The game ends when there is no more space for a new piece.
     *
     * @return {@code true} if the game is finished, {@code false} otherwise
     */
    @Override
    public boolean finished() {
        return super.finished();
    }

    /**
     * Textual representation of this game mode: grid, current piece
     * and total score (plays + eliminations).
     *
     * @return textual description of the game state
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Current grid: \n");
        sb.append(area.currentGrid()).append("\n");

        sb.append("Current piece: \n");
        sb.append(currentPiece());

        sb.append("Score: ")
          .append(score())
          .append("\n");

        return sb.toString();
    }
}
