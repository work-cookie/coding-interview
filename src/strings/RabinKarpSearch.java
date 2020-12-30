package strings;

/*
    Hash function
    - - - - - - - -  - - -
    HF(str[0..k-1]) = c0*P^0 + c1*P^1 + ...ck-1*P^(k-1)
    c0, c1, c2 -> constants for chars. example - 1, 2, 3 or ASCII codes we can use
    k -> no of chars in string
    P -> prime number > possible allowed chars of string

    if string consists of only lower case -> P can be 29/31
    if string consists of lower&upper case -> P can be 53

    Rolling Hash -> Calculating hash from previously computed hash
    - - - - - - - - - - - -
    newHash = OldHash - remove first char
    newHash = newHash/prime
    newHash += newChar * P^(k-1)
 */

public class RabinKarpSearch {

    private int prime = 101;

    /*
        Average and best case -> TC -> O(n+m) n-strings for generating hashcodes & m -> comparisions
        A good HF produces less collisions, hence only m comparisions only happens.

        In worstcase TC -> O(mn), For each hash(suppose collision happen), there will be m comparisions.
     */
    public int patternSearch(char[] text, char[] pattern) {
        int n = text.length;
        int m = pattern.length;

        // Always text should be greater than the pattern
        if (n < m)
            return -1;

        long targetHash = generateHash(pattern, m - 1);
        long calHash = 0L;
        for (int i = 0; i <= (n - m); i++) {
            if (i == 0)
                calHash = generateHash(text, m - 1);
            else {
                // Calculating hash based on existing hash (rolling hash)
                calHash = recalculateHash(text, i - 1, i + m - 1, calHash, m);
            }
            // If hash matches -> check for equality
            if (targetHash == calHash && checkEqual(text, i, i + m - 1, pattern, 0, m - 1)) {
                return i;
            }
        }
        return -1;
    }

    private boolean checkEqual(char[] string1, int start1, int end1, char[] string2, int start2, int end2) {
        while (start1 <= end1 && start2 <= end2) {
            if (string1[start1] != string2[start2])
                return false;
            start1++;
            start2++;
        }
        return true;
    }

    private long recalculateHash(char[] text, int start, int end, long oldHash, int patternLength) {
        long newHash = oldHash - (text[start] - 'A' + 1);
        newHash /= prime;
        newHash += ((text[end] - 'A' + 1) * Math.pow(prime, patternLength - 1));
        return newHash;
    }

    private long generateHash(char[] chars, int end) {
        long hash = 0;
        for (int i = 0; i <= end; i++) {
            hash += ((chars[i] - 'A' + 1) * Math.pow(prime, i));
        }
        return hash;
    }

    public static void main(String[] args) {
        RabinKarpSearch rabinKarpSearch = new RabinKarpSearch();
        System.out.println(rabinKarpSearch.patternSearch("ABCDEF".toCharArray(), "CDE".toCharArray())); // 2
        System.out.println(rabinKarpSearch.patternSearch("abcdefgh".toCharArray(), "efg".toCharArray())); // 4
        System.out.println(rabinKarpSearch.patternSearch("abcdefgh".toCharArray(), "gh".toCharArray())); // 6
        System.out.println(rabinKarpSearch.patternSearch("abcdefgh".toCharArray(), "xyz".toCharArray())); // -1
    }
}
