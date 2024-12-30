package tutorial.dsa.algorithms.patternsearching;

/**
 * Refer: https://www.geeksforgeeks.org/boyer-moore-algorithm-for-pattern-searching/
 *
 * Boyer-Moore: It compares the pattern from right to left, skipping characters based on the last mismatch with complexity O(mn).
 *
 * Pattern searching is an important problem in computer science. When we do search for a string in a notepad/word file, browser,
 * or database, pattern searching algorithms are used to show the search results.
 *
 * A typical problem statement would be-
 * ” Given a text txt[0..n-1] and a pattern pat[0..m-1] where n is the length of the text and m is the length of the pattern,
 * write a function search(char pat[], char txt[]) that prints all occurrences of pat[] in txt[]. You may assume that n > m. “
 *
 * Examples:
 * Input:  txt[] = “THIS IS A TEST TEXT”, pat[] = “TEST”
 * Output: Pattern found at index 10
 *
 * Input:  txt[] =  “AABAACAADAABAABA”, pat[] =  “AABA”
 * Output: Pattern found at index 0
 *         Pattern found at index 9
 *         Pattern found at index 12
 *
 * Boyer Moore is a combination of the following two approaches.
 * - Bad Character Heuristic
 * - Good Suffix Heuristic (Refer: https://www.geeksforgeeks.org/boyer-moore-algorithm-good-suffix-heuristic/)
 * */
public class BoyerMooreAlgorithm {
    /* Java Program for Bad Character Heuristic of Boyer
    Moore String Matching Algorithm */

    /**
     * Time Complexity : O(m*n)
     * Auxiliary Space: O(1)
     *
     * The Bad Character Heuristic may take O(m*n) time in worst case. The worst case occurs when all characters of the text
     * and pattern are same. For example, txt[] = “AAAAAAAAAAAAAAAAAA” and pat[] = “AAAAA”. The Bad Character Heuristic may
     * take O(n/m) in the best case. The best case occurs when all the characters of the text and pattern are different.
     * */

    static int NO_OF_CHARS = 256;

    // A utility function to get maximum of two integers
    static int max(int a, int b) { return (a > b) ? a : b; }

    // The preprocessing function for Boyer Moore's
    // bad character heuristic
    static void badCharHeuristic(char[] str, int size,
                                 int badchar[])
    {

        // Initialize all occurrences as -1
        for (int i = 0; i < NO_OF_CHARS; i++)
            badchar[i] = -1;

        // Fill the actual value of last occurrence
        // of a character (indices of table are ascii and
        // values are index of occurrence)
        for (int i = 0; i < size; i++)
            badchar[(int)str[i]] = i;
    }

    /* A pattern searching function that uses Bad
    Character Heuristic of Boyer Moore Algorithm */
    static void search(char txt[], char pat[])
    {
        int m = pat.length;
        int n = txt.length;

        int badchar[] = new int[NO_OF_CHARS];

		/* Fill the bad character array by calling
		the preprocessing function badCharHeuristic()
		for given pattern */
        badCharHeuristic(pat, m, badchar);

        int s = 0; // s is shift of the pattern with
        // respect to text
        // there are n-m+1 potential alignments
        while (s <= (n - m)) {
            int j = m - 1;

			/* Keep reducing index j of pattern while
			characters of pattern and text are
			matching at this shift s */
            while (j >= 0 && pat[j] == txt[s + j])
                j--;

			/* If the pattern is present at current
			shift, then index j will become -1 after
			the above loop */
            if (j < 0) {
                System.out.println(
                        "Patterns occur at shift = " + s);

				/* Shift the pattern so that the next
				character in text aligns with the last
				occurrence of it in pattern.
				The condition s+m < n is necessary for
				the case when pattern occurs at the end
				of text */
                // txt[s+m] is character after the pattern
                // in text
                s += (s + m < n) ? m - badchar[txt[s + m]]
                        : 1;
            }

            else
				/* Shift the pattern so that the bad
				character in text aligns with the last
				occurrence of it in pattern. The max
				function is used to make sure that we get
				a positive shift. We may get a negative
				shift if the last occurrence of bad
				character in pattern is on the right side
				of the current character. */
                s += max(1, j - badchar[txt[s + j]]);
        }
    }

    /* Driver program to test above function */
    public static void main(String[] args)
    {

        char txt[] = "ABAAABCD".toCharArray();
        char pat[] = "ABC".toCharArray();
        search(txt, pat);
    }
}
