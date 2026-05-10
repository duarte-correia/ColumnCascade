import java.util.List;

/**
 * Interface that defines the public operations of a game.
 *
 * A game is played on a rectangular grid, where pieces are generated,
 * optionally permutated, and then placed in a chosen column.
 * The game can end when there is no space left for new pieces.
 */
public interface Game {

    /**
     * Returns the number of lines (rows) in the game grid.
     *
     * @return number of lines of the grid
     */
    int linesInGrid();

    /**
     * Returns the number of columns in the game grid.
     *
     * @return number of columns of the grid
     */
    int colsInGrid();

    /**
     * Permutates (rotates) the current piece {@code n} times, according
     * to the permutation rule defined by the implementation.
     * If there is no current piece, nothing happens.
     *
     * @param n number of permutations to apply (non-negative)
     * @requires n >= 0
     */
    void permutatePiece(int n);

    /**
     * Places the current piece in the given column of the grid.
     * The column index is 1-based (1 is the left-most column).
     * Implementations may assume the move is valid (enough space).
     *
     * @param col target column (1-based)
     * @requires 1 <= col && col <= colsInGrid()
     */
    void placePiece(int col);

    /**
     * Generates a new random piece to be played, if there is enough
     * space in the grid to place it. If the grid is too full, no
     * piece is generated and the game may become finished.
     */
    void generatePiece();

    /**
     * Returns the number of empty positions in a given column.
     * The column index is 1-based (1 is the left-most column).
     *
     * @param col column to inspect (1-based)
     * @requires 1 <= col && col <= colsInGrid()
     * @return number of empty cells in that column
     */
    int spaceInColumn(int col);

    /**
     * Returns a textual representation of the current piece that
     * is ready to be played, usually one symbol per line.
     *
     * @return string describing the current piece; empty string
     *         if there is no current piece
     */
    String currentPiece();

    /**
     * Indicates whether the game is finished. A game is typically
     * finished when there is no space left to place a new piece.
     *
     * @return {@code true} if the game is finished; {@code false} otherwise
     */
    boolean finished();

    /**
     * Returns a textual representation of the whole game state,
     * including the grid, the current piece and the scores, in a
     * format suitable for printing in the console.
     *
     * @return string describing the current game state
     */
    String toString();

    /**
     * Updates the internal score after a play, according to the
     * scoring policy of the concrete game.
     *
     * @param eliminated list where each element is the number of
     *                   symbols eliminated in one elimination phase
     *                   of the last play
     */
    void registerPlayScore(List<Integer> eliminated);

    /**
     * Returns the total score of the game, according to the
     * scoring policy of the implementation.
     *
     * @return current total score
     */
    int score();
}
