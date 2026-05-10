/**
 * Simple eliminator that removes ALL contiguous sequences of equal symbols
 * (different from {@code nothing}) whose length is at least {@code blockSize},
 * in a single pass over the array.
 *
 * Eliminating a symbol means replacing it with {@code nothing}.
 */
public class SimpleEliminator implements Eliminator {

    /**
     * Eliminates, in the given array {@code sequence}, all contiguous
     * sequences of equal symbols (different from {@code nothing}) whose
     * length is at least {@code blockSize}.
     *
     * @param sequence  array where the elimination takes place
     * @param blockSize minimum length of a sequence to be eliminated
     * @param nothing   symbol used to represent empty positions
     * @requires sequence != null && nothing != null && blockSize > 0
     * @return 0 if no sequence was eliminated; otherwise the number of
     *         eliminated elements
     */
    @Override
    public int eliminateSequence(Symbol[] sequence, int blockSize, Symbol nothing) {
        if (sequence == null || nothing == null || blockSize <= 0) {
            return 0;
        }

        boolean[] toEliminate = new boolean[sequence.length];
        int total = 0;

        int i = 0;
        while (i < sequence.length) {

            Symbol curr = sequence[i];

            // ignore empty positions
            if (curr == null || curr == nothing) {
                i++;
                continue;
            }

            // count block size from i
            int j = i + 1;
            while (j < sequence.length && sequence[j] == curr) {
                j++;
            }
            int len = j - i;

            // if block is big enough, mark it for elimination
            if (len >= blockSize) {
                for (int k = i; k < j; k++) {
                    if (sequence[k] != nothing) {
                        toEliminate[k] = true;
                        total++;
                    }
                }
            }

            i = j;
        }

        // apply eliminations
        if (total > 0) {
            for (int k = 0; k < sequence.length; k++) {
                if (toEliminate[k]) {
                    sequence[k] = nothing;
                }
            }
        }

        return total;
    }
}
