package strings;

public class SubstringSearch {

    //Naive TC -> O(mn) SC -> O(1)
    public static boolean hasSubstring(char[] text, char[] pattern) {
        int n = text.length;
        int m = pattern.length;
        int i = 0, j = 0, k = 0;
        while (i < n && j < m) {
            if (text[i] == pattern[j]) {
                i++;
                j++;
            } else {
                k++;
                i = k;
                j = 0;
            }
        }
        if (j == m) {
            System.out.println("Pattern match found at idx: " + k);
            return true;
        }
        return false;
    }

    /**
     * Compute the length of longest prefix which is also a suffix
     * LPS[i] = LLPS in string S[0..i] such that S[0..k] == S[(i-k)..i]
     * <p>
     * matches -> store the next idx and increment both ptrs
     * not matches -> jump to previous matched location
     *
     * @param pattern
     * @return lps array
     */
    private int[] computeLPS(char[] pattern) {
        int length = pattern.length;
        int[] lps = new int[length];
        lps[0] = 0;
        int idx = 1, j = 0;
        while (idx < length) {
            if (pattern[idx] == pattern[j]) {
                lps[idx] = j + 1;
                idx++;
                j++;
            } else {
                if (j == 0) {
                    lps[idx] = 0;
                    idx++;
                } else {
                    j = lps[j - 1];
                }
            }
        }
        return lps;
    }

    /**
     * TC -> O(m+n) and SC -> O(m) for LPS store
     *
     * @param text
     * @param pattern
     * @return
     */
    public boolean KMPSearch(char[] text, char[] pattern) {
        int n = text.length;
        int m = pattern.length;
        if (n < m)
            return false;
        int[] lps = computeLPS(pattern);
        int i = 0, j = 0;
        while (i < n && j < m) {
            if (text[i] == pattern[j]) {
                i++;
                j++;
            } else {
                if (j == 0) {
                    i++;
                } else {
                    j = lps[j - 1];
                }
            }
        }
        if (j == m) {
            System.out.println("Pattern match found at idx: " + (i - m));
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(hasSubstring("abcdabcdabcabcdefghi".toCharArray(), "abcde".toCharArray())); // 11
        SubstringSearch substringSearch = new SubstringSearch();
        boolean result = substringSearch.KMPSearch("abcdabcdabcabcdefghi".toCharArray(), "abcde".toCharArray());
        System.out.println(result); // 11
        System.out.println(hasSubstring("abcdabcdabcabcdfghabcdei".toCharArray(), "abcde".toCharArray())); // 18
        result = substringSearch.KMPSearch("abcdabcdabcabcdfghabcdei".toCharArray(), "abcde".toCharArray());
        System.out.println(result); // 18
        System.out.println(hasSubstring("aaaaa".toCharArray(), "bba".toCharArray())); // -1
        result = substringSearch.KMPSearch("aaaaa".toCharArray(), "bba".toCharArray());
        System.out.println(result); // -1
    }
}
