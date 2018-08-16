import java.util.ArrayList;
import java.util.List;

/**
 * TODO #2
 */

public class MatchBot extends TwitterBot {
	/**
	 * Constructs a MatchBot to operate on the last numTweets of the given user.
	 */
	public MatchBot(String user, int numTweets) {
		super(user, numTweets);
	}

	/**
	 * TODO
	 * 
	 * Employs the KMP string matching algorithm to add all tweets containing 
	 * the given pattern to the provided list. Returns the total number of 
	 * character comparisons performed.
	 */
	public int searchTweetsKMP(String pattern, List<String> ans) {
		int count = 0;
		for (String tweet : this.tweets) {
			Result r = StringMatch.matchKMP(pattern, tweet);
			if (r.pos != -1) {
				ans.add(tweet);
				count += r.comps;
			}
		}
		return count;
	}

	/**
	 * TODO
	 * 
	 * Employs the naive string matching algorithm to find all tweets containing 
	 * the given pattern to the provided list. Returns the total number of 
	 * character comparisons performed.
	 */
	public int searchTweetsNaive(String pattern, List<String> ans) {
		int count = 0;
		for (String tweet : this.tweets) {
			Result r = StringMatch.matchNaive(pattern, tweet);
			if (r.pos != -1) {
				ans.add(tweet);
				count += r.comps;
			}
		}
		return count;
	}

	public int searchTweetsBoyerMoore(String pattern, List<String> ans) {
		int count = 0;
		for (String tweet : this.tweets) {
			Result r = StringMatch.matchBoyerMoore(pattern, tweet);
			if (r.pos != -1) {
				ans.add(tweet);
				count += r.comps;
			}
		}
		return count;
	}

	public static void main(String... args) {
		
		String handle = "realDonaldTrump", pattern = "Mexico";
		MatchBot bot = new MatchBot(handle, 2000);

		// Search all tweets for the pattern.
		List<String> ansNaive = new ArrayList<>();
		int compsNaive = bot.searchTweetsNaive(pattern, ansNaive); 
		List<String> ansKMP = new ArrayList<>();
		int compsKMP = bot.searchTweetsKMP(pattern, ansKMP);  
		List<String> ansBM = new ArrayList<>();
		int compsBM = bot.searchTweetsBoyerMoore(pattern, ansBM); 

		System.out.println("naive comps = " + compsNaive + ", KMP comps = " + compsKMP + " boyer-moore comps = " + compsBM);

		for (int i = 0; i < ansKMP.size(); i++) {
			String tweet = ansKMP.get(i);
			assert tweet.equals(ansNaive.get(i));
			System.out.println(i++ + ". " + tweet);
			System.out.println(pattern + " appears at index " + 
					tweet.toLowerCase().indexOf(pattern.toLowerCase()));
		}

		// Do something similar for the Boyer-Moore matching algorithm.
		for (int i = 0; i < ansBM.size(); i++) {
			String tweet = ansBM.get(i);
			assert tweet.equals(ansKMP.get(i));
			System.out.println(i++ + ". " + tweet);
			System.out.println(pattern + " appears at index " +
					tweet.toLowerCase().indexOf(pattern.toLowerCase()));
		}
		
		handle = "CNN";
		pattern = "Trump";
		bot = new MatchBot(handle, 3000);
		ansNaive = new ArrayList<>();
		compsNaive = bot.searchTweetsNaive(pattern, ansNaive); 
		ansKMP = new ArrayList<>();
		compsKMP = bot.searchTweetsKMP(pattern, ansKMP);  
		ansBM = new ArrayList<>();
		compsBM = bot.searchTweetsBoyerMoore(pattern, ansBM);
		
		System.out.println("naive comps = " + compsNaive + ", KMP comps = " + compsKMP + " boyer-moore comps = " + compsBM);

		for (int i = 0; i < ansKMP.size(); i++) {
			String tweet = ansKMP.get(i);
			assert tweet.equals(ansNaive.get(i));
			System.out.println(i++ + ". " + tweet);
			System.out.println(pattern + " appears at index " + 
					tweet.toLowerCase().indexOf(pattern.toLowerCase()));
		}

		// Do something similar for the Boyer-Moore matching algorithm.
		for (int i = 0; i < ansBM.size(); i++) {
			String tweet = ansBM.get(i);
			assert tweet.equals(ansKMP.get(i));
			System.out.println(i++ + ". " + tweet);
			System.out.println(pattern + " appears at index " +
					tweet.toLowerCase().indexOf(pattern.toLowerCase()));
		}
		
		handle = "nytimes";
		pattern = "police";
		bot = new MatchBot(handle, 4000);
		ansNaive = new ArrayList<>();
		compsNaive = bot.searchTweetsNaive(pattern, ansNaive); 
		ansKMP = new ArrayList<>();
		compsKMP = bot.searchTweetsKMP(pattern, ansKMP);  
		ansBM = new ArrayList<>();
		compsBM = bot.searchTweetsBoyerMoore(pattern, ansBM);
		
		System.out.println("naive comps = " + compsNaive + ", KMP comps = " + compsKMP + " boyer-moore comps = " + compsBM);

		for (int i = 0; i < ansKMP.size(); i++) {
			String tweet = ansKMP.get(i);
			assert tweet.equals(ansNaive.get(i));
			System.out.println(i++ + ". " + tweet);
			System.out.println(pattern + " appears at index " + 
					tweet.toLowerCase().indexOf(pattern.toLowerCase()));
		}

		// Do something similar for the Boyer-Moore matching algorithm.
		for (int i = 0; i < ansBM.size(); i++) {
			String tweet = ansBM.get(i);
			assert tweet.equals(ansKMP.get(i));
			System.out.println(i++ + ". " + tweet);
			System.out.println(pattern + " appears at index " +
					tweet.toLowerCase().indexOf(pattern.toLowerCase()));
		}
		
	}
}
