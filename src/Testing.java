import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import java.util.Arrays;
import java.util.Random;

public class Testing {
	private static Random random = new Random();
	private static String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";

	@Test 
	public void smallMachines() {
		String[] pats = new String[] {
				"",
				"A",
				"AB",
				"AA",
				"AAAA",
				"BAAA",
				"AAAB",
				"AAAC",
				"ABAB",
				"ABCD",
				"ABBA",
				"AABC",
				"ABAAB",
				"AABAACAABABA",
				"ABRACADABRA",
		};
		int[][] flinks = new int[][] {
			{ -1 },
			{ -1, 0 },
			{ -1, 0, 0 },
			{ -1, 0, 1 },
			{ -1, 0, 1, 2, 3 },
			{ -1, 0, 0, 0, 0 },
			{ -1, 0, 1, 2, 0 },
			{ -1, 0, 1, 2, 0 },
			{ -1, 0, 0, 1, 2 },
			{ -1, 0, 0, 0, 0 },
			{ -1, 0, 0, 0, 1 },
			{ -1, 0, 1, 0, 0 },
			{ -1, 0, 0, 1, 1, 2 },
			{ -1, 0, 1, 0, 1, 2, 0, 1, 2, 3, 4, 0, 1 },
			{ -1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 3, 4 },
		};
		int[] comps = new int[] { 0, 0, 1, 1, 3, 3, 5, 5, 3, 3, 3, 4, 5, 16, 12 };
		int i = 0;
		for (String pat : pats) {
			int[] flink = new int[pat.length() + 1];
			assertEquals(comps[i], StringMatch.buildKMP(pat, flink));
			assertArrayEquals(flinks[i], flink);
			i++;
		}
	}
	
	@Test
	public void testRunKMP() {
		String[] pats = new String[] {
				"",
				"A",
				"AB",
				"AA",
				"AAAA",
				"BAAA",
				"AAAB",
				"AAAC",
				"ABAB",
				"ABCD",
				"ABBA",
				"AABC",
				"ABAAB",
				"AABAACAABABA",
				"ABRACADABRA",
		};
		Result[] results = new Result[] {
			new Result(0,0),
			new Result(0,1),
			new Result(6,15),
			new Result(0,3),
			new Result(0,7),
			new Result(-1,26),
			new Result(4,17),
			new Result(-1,36),
			new Result(-1,34),
			new Result(-1,34),
			new Result(-1,34),
			new Result(5,18),
			new Result(-1,36),
			new Result(-1,46),
			new Result(9,39),
		};
		
		String text = "AAAAAAABCABRACADABRA";
		
		int i = 0;
		for (String pat : pats) {
			Result res = StringMatch.matchKMP(pat, text);
			assertEquals(results[i].pos, res.pos);
			assertEquals(results[i].comps, res.comps);
			i++;
		}
	}

	@Test
	public void lec13bKMP() {
		String[] pats = new String[] {
				"AABC",
				"ABCDE",
				"AABAACAABABA",
				"ABRACADABRA",
		};
		int[][] flinks = new int[][] {
			{ -1, 0, 1, 0, 0 },
			{ -1, 0, 0, 0, 0, 0 },
			{ -1, 0, 1, 0, 1, 2, 0, 1, 2, 3, 4, 0, 1 },
			{ -1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 3, 4 },
		};
		String text = "AAAAAABRACADABAAAAAAAAAAAAAAAAAAAAAAABCAAAAAAAAAAABAABAAAAAAAAAAAAAAA";
		Result results[] = new Result[] {
				new Result(35, 68),
				new Result(-1, 128),
				new Result(-1, 123),
				new Result(-1, 126),
		};
		int i = 0;
		for (String pat : pats) {
			Result res = StringMatch.runKMP(pat, text, flinks[i]);
			assertEquals(results[i].pos, res.pos);
			assertEquals(results[i].comps, res.comps);
			i++;
		}
	}
	
	@Test
	public void lec14aBoyerMoore() {
		String[] pats = new String[] {
				"00000",
				"11111",
				"00011",
				"10100",
				"10000",
		};
		String text = "00000000000000000000";
		Result[] results = new Result[] {
				new Result(0,5),
				new Result(-1,4),
				new Result(-1,8),
				new Result(-1,48),
				new Result(-1,80),
		};
		int i = 0;
		for (String pat : pats) {
			Result res = StringMatch.matchBoyerMoore(pat,text);
			assertEquals(results[i].pos, res.pos);
			assertEquals(results[i].comps, res.comps);
			i++;
		}
	}
	
	@Test
	public void testBuildDelta1Empty() {
		String pattern = "";
		
		int[] empty = new int[Constants.SIGMA_SIZE];
		int[] emptyMatch = new int[Constants.SIGMA_SIZE];
		StringMatch.buildDelta1(pattern, empty);
		assertArrayEquals(empty,emptyMatch);
	}
	
	@Test
	public void testBuildDelta1Singletons() {
		
		char[] pats = new char[Constants.SIGMA_SIZE];
		for (int i = 0; i < Constants.SIGMA_SIZE; i++)
			pats[i] = (char)i;
		
		int[] delta1;
		int[] testDelta1;
		for (int i = 0; i < pats.length; i++) {
			delta1 = new int[Constants.SIGMA_SIZE];
			testDelta1 = new int[Constants.SIGMA_SIZE];
			StringMatch.buildDelta1(Character.toString(pats[i]), delta1);
			for (int j = 0; j < Constants.SIGMA_SIZE; j++)
				testDelta1[j] = 1;
			testDelta1[pats[i]] = 0;
			assertArrayEquals(delta1,testDelta1);
		}
	}
	
	@Test
	public void testBuildDelta1DoubleRepeats() {
		String[] pats = new String[] {
			"AA",
			"BB",
			"CC",
			"DD",
			"EE",
			"FF",
		};
		
		int[] as = new int[Constants.SIGMA_SIZE];
		Arrays.fill(as, 2);
		as[65] = 0;
		
		int[] bs = new int[Constants.SIGMA_SIZE];
		Arrays.fill(bs, 2);
		bs[66] = 0;
		
		int[] cs = new int[Constants.SIGMA_SIZE];
		Arrays.fill(cs, 2);
		cs[67] = 0;
		
		int[] ds = new int[Constants.SIGMA_SIZE];
		Arrays.fill(ds, 2);
		ds[68] = 0;
		
		int[] es = new int[Constants.SIGMA_SIZE];
		Arrays.fill(es, 2);
		es[69] = 0;
		
		int[] fs = new int[Constants.SIGMA_SIZE];
		Arrays.fill(fs, 2);
		fs[70] = 0;
		
		int[][] delta1s = new int[][] {
			as,
			bs,
			cs,
			ds,
			es,
			fs,
		};
		
		for (int i = 0; i < pats.length; i++) {
			int[] delta = new int[Constants.SIGMA_SIZE];
			StringMatch.buildDelta1(pats[i], delta);
			assertArrayEquals(delta,delta1s[i]);
		}
	}

	@Test
	public void testRandomDelta() {
		String pattern = "AEGHIZMN";
		int[] shifts = new int[] {
				7,
				6,
				5,
				4,
				3,
				2,
				1,
				0,
		};
		int[] delta1 = new int[Constants.SIGMA_SIZE];
		StringMatch.buildDelta1(pattern, delta1);
		for (int i = 0; i < pattern.length(); i++) {
			assertEquals(shifts[i],delta1[pattern.charAt(i)]);
		}
	}
	
	
	@Test
	public void testNoMatch() {
		StringBuilder pattern = new StringBuilder();
		Random rand = new Random();
		char[] characters = new char[] {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		for (int i = 0; i < 1000; i++) {
			pattern.append(characters[rand.nextInt(characters.length)]);
		}
		Result naive = StringMatch.matchNaive(pattern.toString(),"100011");
		Result kmp = StringMatch.matchKMP(pattern.toString(), "100011");
		Result bm = StringMatch.matchBoyerMoore(pattern.toString(), "100011");
		assertEquals(naive.pos,-1);
		assertEquals(kmp.pos,-1);
		assertEquals(bm.pos,-1);
		assertFalse(naive.comps > kmp.comps);
		assertFalse(naive.comps > bm.comps);
	}
	
	@Test
	public void testNaive() {
		String[] pats = new String[] {
				"AAAA",
				"BAAA",
				"AAAB",
				"AAAC",
				"ABAB",
		};
		String text = "AAAAAAAAABAAAAAAAAAB";
		assertEquals(20, text.length());
		Result[] results = new Result[] {
				new Result(0, 4),
				new Result(9, 13),
				new Result(6, 28),
				new Result(-1, 62),
				new Result(-1, 35),
		};
		int i = 0;
		for (String pat : pats) {
			Result res = StringMatch.matchNaive(pat, text);
			assertEquals(res.pos, results[i].pos);
			assertEquals(res.comps, results[i].comps);
			i++;
		}
	}

	@Test
	public void testBuildKMP() {
		String [] pats = new String[] {
				"AABC",
				"ABAAB",
				"AAAB", 
				"ABCDE",
				"AABAACAABABA",
				"ABRACADABRA",
		};
		int[] flink;
		int[][] builds = new int[][] {
			{-1,0,1,0,0},
			{-1,0,0,1,1,2},
			{-1,0,1,2,0},
			{-1,0,0,0,0,0},
			{-1,0,1,0,1,2,0,1,2,3,4,0,1},
			{-1,0,0,0,1,0,1,0,1,2,3,4},
		};
		int[] nums = new int[] {
				4,
				5,
				5,
				4,
				16,
				12,
		};
		for (int i = 0; i < pats.length; i++) {
			flink = new int[pats[i].length()+1];
			int comps = StringMatch.buildKMP(pats[i], flink);
			assertArrayEquals(builds[i],flink);
			assertEquals(comps,nums[i]);
		}
	}

	@Test 
	public void testKMP() {
		String[] pats = new String[] {
				"AAAA",
				"BAAA",
				"AAAB",
				"AAAC",
				"ABAB",
		};
		String text = "AAAAAAAAABAAAAAAAAAB";
		assertEquals(20, text.length());
		Result[] results = new Result[] {
				new Result(0, 7),
				new Result(9, 16),
				new Result(6, 21),
				new Result(-1, 43),
				new Result(-1, 40),
		};
		int i = 0;
		for (String pat : pats) {
			Result res = StringMatch.matchKMP(pat, text);
			assertEquals(res.pos, results[i].pos);
			assertEquals(res.comps, results[i].comps);
			i++;
		}
	}
	
	@Test
	public void kmpWins() {
		String pattern = "10";
		String text = "00000000001000000000000";
		Result ans = StringMatch.matchKMP(pattern, text);
		System.out.println("KMP: " + ans.comps);
		ans = StringMatch.matchBoyerMoore(pattern, text);
		System.out.println("BoyerMoore: " + ans.comps);
	}
	
	@Test
	public void bmWins() {
		String[] pats = new String[] {
				makeRandomPattern(),
				makeRandomPattern(),
				makeRandomPattern(),
				makeRandomPattern(),
				makeRandomPattern(),
				makeRandomPattern(),
		};
		String text = makeRandomText(alphabet);
		for (String str : pats) {
			Result ans = StringMatch.matchBoyerMoore(str, text);
			System.out.println("BoyerMoore: " + ans.comps);
			ans = StringMatch.matchKMP(str, text);
			System.out.println("KMP: " + ans.comps);
		}
	}
	
	@Test 
	public void testEmpty() {
		System.out.println("testEmpty");
		match("", "");
		match("", "ab");
		System.out.println();
	}

	@Test 
	public void testOneChar() {
		System.out.println("testOneChar");
		match("a", "a");
		match("a", "b");
		System.out.println();
	}

	@Test 
	public void testRepeat() {
		System.out.println("testRepeat");
		match("aaa", "aaaaa");
		match("aaa", "abaaba");
		match("abab", "abacababc");
		match("abab", "babacaba");
		System.out.println();
	}

	@Test 
	public void testPartialRepeat() {
		System.out.println("testPartialRepeat");
		match("aaacaaaaac", "aaacacaacaaacaaaacaaaaac");
		match("ababcababdabababcababdaba", "ababcababdabababcababdaba");
		System.out.println();
	}


	@Test 
	public void testRandomly() {
		System.out.println("testRandomly");
		for (int i = 0; i < 100; i++) {
			String pattern = makeRandomPattern();
			for (int j = 0; j < 100; j++) {
				String text = makeRandomText(pattern);
				match(pattern, text);
			}
		}
		System.out.println();
	}

	/* Helper functions */

	private static String makeRandomPattern() {
		StringBuilder sb = new StringBuilder();
		int steps = random.nextInt(10) + 1;
		for (int i = 0; i < steps; i++) {
			if (sb.length() == 0 || random.nextBoolean()) {  // Add literal
				int len = random.nextInt(5) + 1;
				for (int j = 0; j < len; j++)
					sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
			} 
			else {  // Repeat prefix
				int len = random.nextInt(sb.length()) + 1;
				int reps = random.nextInt(3) + 1;
				if (sb.length() + len * reps > 1000)
					break;
				for (int j = 0; j < reps; j++)
					sb.append(sb.substring(0, len));
			}
		}
		return sb.toString();
	}
	
	private static String makeRandomText(String pattern) {
		StringBuilder sb = new StringBuilder();
		int steps = random.nextInt(100);
		for (int i = 0; i < steps && sb.length() < 10000; i++) {
			if (random.nextDouble() < 0.7) {  // Add prefix of pattern
				int len = random.nextInt(pattern.length()) + 1;
				sb.append(pattern.substring(0, len));
			} 
			else {  // Add literal
				int len = random.nextInt(30) + 1;
				for (int j = 0; j < len; j++)
					sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
			}
		}
		return sb.toString();
	}

	private static void match(String pattern, String text) {
		// run all three algorithms and test for correctness
		Result ansNaive = StringMatch.matchNaive(pattern, text);
		int expected = text.indexOf(pattern);
		System.out.println(expected);
		System.out.println(ansNaive.pos);
		assertEquals(expected, ansNaive.pos);
		Result ansKMP = StringMatch.matchKMP(pattern, text);
		assertEquals(expected, ansKMP.pos);
		Result ansBoyerMoore = StringMatch.matchBoyerMoore(pattern, text);
		assertEquals(expected, ansBoyerMoore.pos);
		
		System.out.println(String.format("%5d %5d %5d : %s", 
				ansNaive.comps, ansKMP.comps, ansBoyerMoore.comps,
				(ansNaive.comps < ansKMP.comps && ansNaive.comps < ansBoyerMoore.comps) ?
						"Naive" :
							(ansKMP.comps < ansNaive.comps && ansKMP.comps < ansBoyerMoore.comps) ?
									"KMP" : "Boyer-Moore"));

	}
}
