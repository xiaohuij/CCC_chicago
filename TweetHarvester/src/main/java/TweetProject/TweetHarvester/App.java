/**
 * 2014 University of Melbourne
 * 
 * Cloud and Cluster Computing
 * Team6
 * Tweets Harvesting Application 
 * Chicago
 **/

package TweetProject.TweetHarvester;

import com.team6.twitterHarvester.Tweet;

public class App {
	public static void main(String[] args) throws InterruptedException {
		if (args.length > 0
				&& (args[0].equals("end") || args[0].equals("shutdown") || args[0]
						.equals("close"))) {
			Tweet.stop();
			System.out.println(Tweet.status());
		} else {
			Tweet.exe();
		}
	}
}
