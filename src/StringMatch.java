/**
 * TODO #1
 */

import java.util.Arrays;
public class StringMatch {

	/**
	 * TODO
	 * 
	 * Returns the result of running the naive algorithm to match pattern in text.
	 */
	public static Result matchNaive(String pattern, String text) {  
		int m = pattern.length(), n = text.length();
		Result ans = new Result(0,0);
		int i = 0, j = 0;

		if (m == 0)
			return ans;

		while (ans.pos <= n - m) {
			ans.comps++;
			if (pattern.charAt(i) == text.charAt(j)) {
				i++;
				j++;
				if (i == m) return ans;
			} 
			else {
				i = 0;
				ans.pos++;
				j = ans.pos;
			}
		}
		return new Result(-1, ans.comps);
	}

	/**
	 * TODO
	 * 
	 * Populates flink with the failure links for the KMP machine associated with the
	 * given pattern, and returns the cost in terms of the number of character comparisons.
	 */
	public static int buildKMP(String pattern, int[] flink) {
		int count = 0;
		int i = 2;
		int m = pattern.length();

		if (m == 0) {
			flink[0] = -1;
			return count;
		}
		
		flink[0] = -1;
		flink[1] = 0;

		while (i <= m) {
			int j = flink[i-1];
			while (j != -1 && ++count != 0 && pattern.charAt(j) != pattern.charAt(i-1)) {
				j = flink[j]; 
			}
			flink[i] = j+1;
			i++;
		}
		return count;
	}

	/**
	 * TODO
	 * 
	 * Returns the result of running the KMP machine specified by flink (built for the
	 * given pattern) on the text.
	 */
	public static Result runKMP(String pattern, String text, int[] flink) {

		int j = -1;
		int state = -1;
		int comps = 0;
		char curr = '\0';
		int m = pattern.length();
		int n = text.length();

		while (true) {
			if (state == -1 || ++comps != 0 && curr == pattern.charAt(state)) {
				state++;
				if (state == m) {
					return new Result(j - m + 1,comps);
				}
				j++;
				if (j == n) {
					return new Result(-1,comps);
				}
				curr = text.charAt(j);
			} else {
				state = flink[state];
			}
		}
	}

	/**
	 * Returns the result of running the KMP algorithm to match pattern in text. The number
	 * of comparisons includes the cost of building the machine from the pattern.
	 */
	public static Result matchKMP(String pattern, String text) {
		int m = pattern.length();
		int[] flink = new int[m + 1];
		int comps = buildKMP(pattern, flink);
		Result ans = runKMP(pattern, text, flink);
		return new Result(ans.pos, comps + ans.comps);
	}

	/**
	 * TODO
	 * 
	 * Populates delta1 with the shift values associated with each character in the
	 * alphabet. Assume delta1 is large enough to hold any ASCII value.
	 */
	public static void buildDelta1(String pattern, int[] delta1) {
		int m = pattern.length();
		int last;
		
		for (int i = 0; i < Constants.SIGMA_SIZE; i++) {
			delta1[i] = m;
		}
		
		for (int i = 0; i < m; i++) {
			delta1[pattern.charAt(i)] = m-i-1;
		}
	}

	/**
	 * TODO
	 * 
	 * Returns the result of running the simplified Boyer-Moore algorithm using the
	 * delta1 table from the pre-processing phase.
	 */
	public static Result runBoyerMoore(String pattern, String text, int[] delta1) {
		int n = text.length();
		int m = pattern.length();
		int count = 0;
		int i = 0;
		int j = 0;

		if (m == 0)
			return new Result(0,0);

		while (j <= n-m) {
			for (i = m-1; i >= 0 && ++count != 0 && pattern.charAt(i) == text.charAt(i+j); --i); 
			if (i < 0) {
				return new Result(j,count);
			} else {
				int index = text.charAt(i+j);
				int d = 0;
				if (index > Constants.SIGMA_SIZE) {
					d = m;
				} else {
					d = delta1[index];
				}
				j += Math.max(1, d-m+1+i);
			}
		}
		return new Result(-1,count);
	}

	/**
	 * Returns the result of running the simplified Boyer-Moore algorithm to match 
	 * pattern in text. 
	 */
	public static Result matchBoyerMoore(String pattern, String text) {
		int[] delta1 = new int[Constants.SIGMA_SIZE];
		buildDelta1(pattern, delta1);
		return runBoyerMoore(pattern, text, delta1);
	}

	public static void main(String[] args) {
		StringMatch sm = new StringMatch();
		String pattern = "1";
		String text = "000000000000000000001";
		Result r = sm.matchBoyerMoore(pattern,text);
		System.out.println(r.pos);
		System.out.println(r.comps);
		r = sm.matchKMP(pattern,text);
		System.out.println(r.pos);
		System.out.println(r.comps);
		r = sm.matchNaive(pattern,text);
		System.out.println(r.pos);
		System.out.println(r.comps);
		//int[] flink = new int[pattern.length()+1];
		//sm.buildKMP(pattern, flink);
		//System.out.println(Arrays.toString(flink));
		/*
		String pattern = "A";
		String text = "ABC";
		int[] flink = new int[2];
		int comps = sm.buildKMP(pattern, flink);
		System.out.println(Arrays.toString(flink));
		System.out.println(comps);
		*/
		
	}
	
}
