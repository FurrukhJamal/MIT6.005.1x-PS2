package twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        //throw new RuntimeException("not implemented");
    	List<Tweet> tweetsByUser = new ArrayList<>();
    	//System.out.println("username to check: " + username);
    	//String test = username.toLowerCase();
    	for(Tweet tweet : tweets)
    	{
    		String author = tweet.getAuthor().toLowerCase();
    		//System.out.println("username :" +test  + " author in lower:" + author);
    		
    		if(author.equalsIgnoreCase(username.toLowerCase()))
    		{
    			//System.out.println("inside if condition");
    			tweetsByUser.add(tweet);
    		}
    	}
    	return tweetsByUser;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        //throw new RuntimeException("not implemented");
    	List<Tweet> tweetsByTimespan = new ArrayList<>();
//    	for(Tweet tweet : tweets)
//    	{
//    		if(tweet.getAuthor() == username)
//    		{
//    			tweetsByUser.add(tweet);
//    		}
//    	}
//    	return tweetsByUser;
    	
    	//Timespan calculatedTimespan = Extract.getTimespan(tweets);
    	Predicate<Tweet> withinTimeSpan = tweet->{
    		Instant start = timespan.getStart();
    		Instant end = timespan.getEnd();
    		Instant timestamp = tweet.getTimestamp();
    		
    		return(timestamp.isAfter(start) && timestamp.isBefore(end) || timestamp.equals(start) || timestamp.equals(end));
    	};
    	
    	return tweets.stream().filter(withinTimeSpan).collect(Collectors.toList());
    }

    /**
     * Find tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        //throw new RuntimeException("not implemented");
//    	for (Tweet tweet : tweets)
//    	{
//    		for (String word : words)
//    		{
//    			if (tweet.getText().toLowerCase().contains(word.toLowerCase()))
//    		}
//    	}
    	//List<Tweet> filteredList = new ArrayList<>();
    	Predicate<Tweet> filterByWords = tweet-> {
    		//System.out.println("tweet:" +tweet.getText());
    		for (String word : words)
    		{
    			//System.out.println("word : " +word);
    			if(tweet.getText().toLowerCase().contains(word.toLowerCase())) {
    				return true ;
    			}
    			
    			
       		}
			return false;
    	};
    	
    	return tweets.stream().filter(filterByWords).collect(Collectors.toList());
    	
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
