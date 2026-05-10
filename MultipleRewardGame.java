import java.util.List;
import java.util.Random;

/**
 * Game mode in which each elimination sequence gives a separate reward.
 *
 * Scoring rules (MultipleRewardGame):
 *  - Each valid play always gives {@link #PLAY_SCORE} points (10).
 *  - The list {@code eliminated} contains, for that play,
 *    the number of symbols eliminated in each elimination phase.
 *  - For each element {@code n} of {@code eliminated}:
 *        we apply the general formula
 *        step * (BASE_ELIM_POINTS + (n - SIZE_OF_PIECE) * EXTRA_ELIM_POINTS),
 *        where {@code step} is 1 for the first elimination phase, 2 for the second, etc.
 *  - The total game score is the sum of play points and elimination points.
 */
public class MultipleRewardGame extends AbstractGame {

    /**
     * Creates a {@code MultipleRewardGame} with the given configuration.
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
    public MultipleRewardGame(int r, int c, int diff,
                              Symbol empty, Symbol[] values,
                              Random gen, Eliminator elim, Accomodator acc) {
        super(r, c, diff, empty, values, gen, elim, acc);
        this.playPoints = 0;
        this.elimPoints = 0;
    }

    /**
     * Updates the score after a play.
     *
     * @param eliminated list where each element is the number of symbols
     *                   eliminated in one elimination phase of this play
     */
    @Override
    public void registerPlayScore(List<Integer> eliminated) {
        // 1) Fixed points for every valid play
        this.playPoints += PLAY_SCORE;

        if (eliminated == null || eliminated.isEmpty()) {
            return;
        }

        // 2) Elimination points: apply step multiplier (1st step, 2nd step, ...)
        int step = 1;

        for (Integer e : eliminated) {
            if (e != null) {
                int eliminatedInStep = e.intValue();

                // General formula for this game mode:
                // step * (BASE_ELIM_POINTS + (eliminatedInStep - SIZE_OF_PIECE) * EXTRA_ELIM_POINTS)
                int pointsThisStep = BASE_ELIM_POINTS
                                   + (eliminatedInStep - SIZE_OF_PIECE) * EXTRA_ELIM_POINTS;

                this.elimPoints += step * pointsThisStep;
            }
            step++;
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
}
