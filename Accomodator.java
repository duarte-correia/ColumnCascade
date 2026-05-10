/**
 * An accomodator knows how to rearrange the symbols in a sequence
 * that contains "holes" (positions with the symbol {@code nothing}),
 * according to some strategy.
 */
public interface Accomodator {

    /**
     * Accomodates all the occurrences of {@code nothing} in the given
     * array {@code sequence}, possibly changing the order of the other
     * symbols, according to the strategy adopted by this accomodator.
     *
     * @param sequence array with the symbols to accomodate
     * @param nothing  the symbol representing empty positions ("holes")
     * @requires sequence != null and nothing != null
     */
    void accomodate(Symbol[] sequence, Symbol nothing);
}
