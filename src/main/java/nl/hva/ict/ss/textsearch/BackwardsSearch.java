package nl.hva.ict.ss.textsearch;

public class BackwardsSearch {

    /**
     * Returns index of the right most location where <code>needle</code> occurs
     * within <code>haystack</code>. Searching starts at the right end side of
     * the text (<code>haystack</code>) and proceeds to the first character
     * (left side).
     *
     * @param needle the text to search for.
     * @param haystack the text which might contain the <code>needle</code>.
     * @return -1 if the <code>needle</code> is not found and otherwise the left
     * most index of the first character of the <code>needle</code>.
     */
    private int R;     // the radix
    private int[] right;     // the bad-character skip array

    private String pattern;      // or as a string
    private int comparrisonsLeft;
    private int comparrisonsRight;


    int findLocationViaRight(String needle, String haystack) {
        BoyerMoore(needle);
        int M = pattern.length();
        int N = haystack.length();
        comparrisonsRight = 0;
        int skip;
        for (int i = N - M; i >= 0; i -= skip) {
            skip = 0;
            for (int j = 0; j <= M - 1; j++) {
                System.out.println("CHARTAT " + pattern.charAt(j));
                if (pattern.charAt(j) != haystack.charAt(i + j)) {
                    skip = Math.max(1, j - right[haystack.charAt(i + j)]);
                    break;
                }
                comparrisonsRight++;
            }
            if (skip == 0) {
                return i;
            }    // found
        }
        return N;
    }

    int findLocationViaLeft(String needle, String haystack) {
        BoyerMoore(needle);
        int M = pattern.length();
        int N = haystack.length();
        int count = 0;
        int skip = 0;        
        comparrisonsLeft = 0;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M - 1; j >= 0; j--) {
                System.out.println("CHARTAT 2" + pattern.charAt(j));
                if (pattern.charAt(j) != haystack.charAt(i + j)) {
                    skip = Math.max(1, j - right[haystack.charAt(i + j)]);
                    break;
                }
                comparrisonsLeft++;
            }
            if (skip == 0) {
                return i;
            }// found
        }
        return N; //NotFound
    }

    int getComparisonsForLastSearchLeft() {
        return comparrisonsLeft;
    }
    
    int getComparisonsForLastSearchRight() {
        return comparrisonsRight;
    }
    
    public void BoyerMoore(String pattern) {
        this.R = 256;
        this.pattern = pattern;

        // position of rightmost occurrence of c in the pattern
        right = new int[R];
        for (int c = 0; c < R; c++) {
            right[c] = -1;
        }
        for (int j = 0; j < pattern.length(); j++) {
            right[pattern.charAt(j)] = j;
        }
    }

}
