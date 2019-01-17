package nl.hva.ict.ss.textsearch;
/**
 *
 * @author rowanvi
 */

public class BoyerMoore {
    private final int R;     // the radix
    private int[] right;     // the bad-character skip array

    private char[] pattern;  // store the pattern as a character array
    private String pat;      // or as a string

    /**
     * Preprocesses the pattern string.
     *
     * @param pat the pattern string
     */
    public BoyerMoore(String pat) {
        this.R = 256;
        this.pat = pat;

        // position of rightmost occurrence of c in the pattern
        right = new int[R];
        for (int c = 0; c < R; c++){
            right[c] = -1;
        }
        for (int j = 0; j < pat.length(); j++){
            right[pat.charAt(j)] = j;
            System.out.println("PAT CHARTATJ " + right[pat.charAt(j)]);
        }
    }

    /**
     * Returns the index of the first occurrrence of the pattern string
     * in the text string.
     *
     * @param  txt the text string
     * @return the index of the first occurrence of the pattern string
     *         in the text string; n if no such match
     */
    public int search(String txt) {
        int m = pat.length();
        int n = txt.length();
        int skip;
        for (int i = n - m; i >= 0; i -= skip) {
            skip = 0;
            for (int j = 0; j <= m-1; j++) {
                if (pat.charAt(j) != txt.charAt(i+j)) {
                    skip = Math.max(1, j - right[txt.charAt(i+j)]);
                    break;
                }
            }
            if (skip == 0) return i;    // found
        }
        return n;                       // not found
    }

     /**
     * Takes a pattern string and an input string as command-line arguments;
     * searches for the pattern string in the text string; and prints
     * the first occurrence of the pattern string in the text string.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String pattern = "ABC";
        String text = "BBBBBBBBBBBABBBBBBABCCCCC";
        BoyerMoore boyermoore = new BoyerMoore(pattern);
        int indexOfset = boyermoore.search(text);

        System.out.println("Input text:    " + text);

        System.out.print("Found pattern: ");
        for (int i = 0; i < indexOfset; i++) {
            System.out.print(" ");
        }
        System.out.println(pattern);


    }
}