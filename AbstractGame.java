import java.util.List;
import java.util.Random;

/**
 * Implements the behavior common to all kinds of games, leaving the
 * scoring policy to be defined by concrete subclasses.
 */
public abstract class AbstractGame implements Game {

    public static final int SIZE_OF_PIECE = 3;

    // Scoring constants
    public static final int PLAY_SCORE = 10;
    public static final int BASE_ELIM_POINTS = 200;
    public static final int EXTRA_ELIM_POINTS = 50;

    // Game state
    protected int playPoints;
    protected int elimPoints;

    protected PlayArea area;
    protected Piece current;
    protected Random gen;

    /**
     * Creates a generic game, configuring the play area and all
     * required parameters.
     *
     * @param r      number of rows in the grid
     * @param c      number of columns in the grid
     * @param diff   difficulty level (affects grid generation)
     * @param empty  symbol that represents an empty position
     * @param values symbols that may appear in the grid/pieces
     * @param gen    random generator to be used
     * @param elim   symbol elimination strategy
     * @param acc    symbol accommodation strategy
     * @requires r > 0 && c > 0
     * @requires diff >= 0 && diff <= r
     * @requires empty != null && values != null
     * @requires gen != null && elim != null && acc != null
     */
    public AbstractGame(int r, int c, int diff,
                        Symbol empty, Symbol[] values,
                        Random gen, Eliminator elim, Accomodator acc) {
        this.area = new PlayArea(r, c, diff, empty, values, gen, elim, acc);
        this.gen = gen;
        this.current = null;

        this.playPoints = 0;
        this.elimPoints = 0;
    }

    /** {@inheritDoc} */
    @Override
    public int linesInGrid() {
        int[] dims = area.gridDimensions();
        return dims[0];
    }

    /** {@inheritDoc} */
    @Override
    public int colsInGrid() {
        int[] dims = area.gridDimensions();
        return dims[1];
    }

    /**
     * {@inheritDoc}
     *
     * @param n number of permutations to apply (n >= 0)
     */
    @Override
    public void permutatePiece(int n) {
        if (current != null && n > 0) {
            current.permutation(n);
        }
    }

    /**
     * {@inheritDoc}
     *
     * Generates a new piece only if there is space for a piece of
     * size {@link #SIZE_OF_PIECE}.
     */
    @Override
    public void generatePiece() {
        if (!area.hasEnoughSpace(SIZE_OF_PIECE)) {
            current = null;
            return;
        }
        current = new Piece(gen, SIZE_OF_PIECE, area.emptySymbol(), area.allSymbols());
    }

    /**
     * {@inheritDoc}
     *
     * If the column is invalid for this game, this method returns 0,
     * which causes the caller (PCOMain) to treat the game as finished
     * instead of throwing an exception.
     *
     * @param col 1-based column index; if invalid, 0 is returned
     * @return number of empty positions in the given column
     */
    @Override
    public int spaceInColumn(int col) {
        int totalCols = colsInGrid();
        if (col < 1 || col > totalCols) {
            return 0;
        }
        int idx = col - 1; // PlayArea uses 0-based
        return area.spaceInColumn(idx);
    }

    /**
     * {@inheritDoc}
     *
     * Places the current piece in the given column (if it exists),
     * performs all eliminations and accommodations, and updates the
     * score through {@link #registerPlayScore(List)}.
     *
     * @param col 1-based column index where the piece will be placed
     */
    @Override
    public void placePiece(int col) {
        if (current == null) {
            return;
        }

        int idx = col - 1;

        area.placePiece(current, idx);

        List<Integer> eliminated = area.eliminateAccomodateAll(SIZE_OF_PIECE);

        registerPlayScore(eliminated);

        current = null;
    }

    /**
     * {@inheritDoc}
     *
     * @return string with the vertical representation of the current piece,
     *         or the empty string if there is no piece
     */
    @Override
    public String currentPiece() {
        if (current == null) {
            return "";
        }
        return current.toString();
    }

    /**
     * {@inheritDoc}
     *
     * The game is finished when there is no column with at least
     * {@link #SIZE_OF_PIECE} empty cells.
     *
     * @return {@code true} if the game is finished, {@code false} otherwise
     */
    @Override
    public boolean finished() {
        return !area.hasEnoughSpace(SIZE_OF_PIECE);
    }

    /**
     * {@inheritDoc}
     *
     * Includes the current grid, the current piece and both components
     * of the score (play points and elimination points).
     *
     * @return string describing the current state of the game
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Current grid: \n");
        sb.append(area.currentGrid());
        sb.append("\n");

        sb.append("Current piece: \n");
        sb.append(currentPiece());

        sb.append("Score of plays: ")
          .append(playPoints)
          .append("   Score of eliminations: ")
          .append(elimPoints)
          .append("\n");

        return sb.toString();
    }

    /* =========================================================
     *   GAME-SPECIFIC (SCORING) PART
     * ========================================================= */

    /**
     * Updates the score after a valid play, according to the
     * scoring policy defined by the concrete subclass.
     *
     * @param eliminated list with the number of eliminated symbols in
     *                   each elimination phase of the play
     */
    @Override
    public abstract void registerPlayScore(List<Integer> eliminated);

    /** {@inheritDoc} */
    @Override
    public abstract int score();
}
