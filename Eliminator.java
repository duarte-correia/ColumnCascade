/**
 * An eliminator knows how to eliminate sequences of symbols
 * in an array, according to some strategy.
 */
public interface Eliminator {

    /**
     * Eliminates, in the given array sequence, one occurrence
     * of a sequence of symbols to be eliminated, according to
     * the strategy of this eliminator.
     * Eliminating a symbol means replacing it with symbol {@code nothing}.
     *
     * @param sequence  array with the symbols where the elimination is to take place
     * @param blockSize minimum size of a sequence to be eliminated;
     *                  some implementations may ignore this parameter
     * @param nothing   symbol to be used to replace eliminated symbols,
     *                  representing an empty position
     * @requires sequence != null and nothing != null
     * @return 0 if no sequence has been eliminated; the number of
     *         eliminated elements otherwise
     */
    int eliminateSequence(Symbol[] sequence, int blockSize, Symbol nothing);
}
