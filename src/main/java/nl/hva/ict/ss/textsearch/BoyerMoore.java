package nl.hva.ict.ss.textsearch;

/**
 *
 * @author rowanvi
 */

public class BoyerMoore {

    private final int R;     // the radix
    private int[] right;     // the bad-character skip array
    private int[] left;

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
        left = new int[R];
        for (int c = 0; c < R; c++) {
            right[c] = -1;
            left[c] = -1;
        }
        for (int j = 0; j < pat.length(); j++) {
            right[pat.charAt(j)] = j;
        }
    }

    /**
     * Returns the index of the first occurrrence of the pattern string in the
     * text string.
     *
     * @param txt the text string
     * @return the index of the first occurrence of the pattern string in the
     * text string; n if no such match
     */
    public int searchLeft(String txt) {
        int M = pat.length();
        int N = txt.length();
        int count = 0;
        int skip = 0;
        int charCount = 0;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M - 1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i + j)) {
                    skip = Math.max(1, j - right[txt.charAt(i + j)]);
                    break;
                }
                charCount++;
            }
            if (skip == 0) {
                System.out.println("CHAR compares Left: " + charCount);
                return i;
            }
        }
        System.out.println("CHAR compares Left: " + charCount);
        return N;
    }

    public int searchRight(String txt) {
        int m = pat.length();
        int n = txt.length();
        int charCompares = 0;
        int skip;
        for (int i = n - m; i >= 0; i -= skip) {
            skip = 0;
            for (int j = 0; j <= m - 1; j++) {
                if (pat.charAt(j) != txt.charAt(i + j)) {
                    skip = Math.max(1, j - right[txt.charAt(i + j)]);
                    break;
                }
                charCompares++;
            }
            if (skip == 0) {
                System.out.println("CHAR compares Right: " + charCompares);
                return i;
            }    // found
        }
        System.out.println("CHAR compares Right: " + charCompares);
        return n;
    }

    /**
     * Takes a pattern string and an input string as command-line arguments;
     * searches for the pattern string in the text string; and prints the first
     * occurrence of the pattern string in the text string.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String pattern = "CCTTTTCC";
        String text = "CCTTCCCCGCTTCTGCTACCTTTTCCATCGTCTTCGasdfghjksdfghjklsdfghjkfghcccTCTTCCTCCcccccccccgcCCcccttccttccttccttcctCC";
        BoyerMoore boyermoore = new BoyerMoore(pattern);
        int indexRight = boyermoore.searchRight(text);
        int indexLeft = boyermoore.searchLeft(text);
        System.out.println("Input text:          " + text);
        System.out.print("Found pattern Right: ");
        for (int i = 0; i < indexRight; i++) {
            System.out.print(" ");
        }
        System.out.println(pattern);
        System.out.print("Found pattern left:  ");

        for (int i = 0; i < indexLeft; i++) {
            System.out.print(" ");
        }
        System.out.println(pattern);

    }
}
