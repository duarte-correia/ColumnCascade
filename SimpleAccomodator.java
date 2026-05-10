/**
 * Simple accomodator that simulates normal gravity:
 * all non-empty symbols "fall" to the end of the array and all
 * {@code nothing} positions are moved to the beginning,
 * leaving no internal holes in the sequence.
 *
 * Example:
 *   [ ., A, ., B, C, . ]  ('.' is nothing)
 * becomes
 *   [ ., ., ., A, B, C ]
 */
public class SimpleAccomodator implements Accomodator {

    /**
     * Moves all non-empty symbols to the end of the array (indexes closer
     * to {@code sequence.length - 1}), preserving their relative order,
     * and fills all remaining positions at the beginning with {@code nothing}.
     *
     * @param sequence array with the symbols to accomodate
     * @param nothing  symbol that represents empty positions ("holes")
     * @requires sequence != null && nothing != null
     */
    @Override
    public void accomodate(Symbol[] sequence, Symbol nothing) {

        int write = sequence.length - 1;

        // first pass: copy non-nothing symbols from bottom to top
        for (int i = sequence.length - 1; i >= 0; i--) {
            if (sequence[i] != nothing) {
                sequence[write] = sequence[i];
                write--;
            }
        }

        // second pass: fill the remaining positions with nothing
        for (int i = write; i >= 0; i--) {
            sequence[i] = nothing;
        }
    }
}
