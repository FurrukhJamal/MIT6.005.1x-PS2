package twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        //throw new RuntimeException("not implemented");
    	//Set<String> mentionedUsers = Extract.getMentionedUsers(tweets);
    	Map<String, Set<String>> listOfUsersAndFollowers = new HashMap<>();
    	
    	Set<String> userNamesOfTweets = new HashSet<>();
    	for(Tweet tweet : tweets)
    	{
    		userNamesOfTweets.add(tweet.getAuthor());
    	}
    	
    	//get tweets for every user and check if he has mentioned anyone
    	for (String username : userNamesOfTweets)
    	{
    		List<Tweet> usersTweets = Filter.writtenBy(tweets, username);
    		Set<String> following = Extract.getMentionedUsers(usersTweets);
    		listOfUsersAndFollowers.put(username, following);
    	}
    	
    	
    	return listOfUsersAndFollowers;
    	
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        //throw new RuntimeException("not implemented");
    	System.out.println("followsGraph gotten" +followsGraph);
    	Map<String, Integer> followers = new HashMap<>();
    	List<String> influencers = new ArrayList<>();
    	
    	for(Set<String> followings : followsGraph.values())
    	{
    		System.out.println("Loop hitting :" +followsGraph.values());
    		for(String person : followings)
    		{
    			person = person.toLowerCase();
    			followers.putIfAbsent(person, 0);
    			followers.put(person, followers.get(person) + 1);
    			if(!influencers.contains(person))
    			{
    				influencers.add(person);
    			}
    			
    		}
    	}
    	
    	//then add the following keys that had no followees but were there in the followsGrap map
    	for (String keyName : followsGraph.keySet())
    	{
    		System.out.println("keySet loop hitting");
    		followers.putIfAbsent(keyName, 0);
    		if(!influencers.contains(keyName.toLowerCase()))
    		{
    			influencers.add(keyName.toLowerCase());
    		}
    	}
    	
    	System.out.println("followers before sorting : " +followers);
    	System.out.println("influencers before sorting: " +influencers);
    	
    	influencers.sort(new Comparator<String>() {
    		@Override public int compare(String one, String two )
    		{
    			System.out.println("inside override method, Value of string one: " +one);
    			System.out.println(followers.get(one));
    			System.out.println(followers.get(two));
    			return followers.get(two).compareTo(followers.get(one));
    			
    		}
    	}); 
    	System.out.println("influencers : " +influencers);
    	return influencers;
    }
    
    public static void main(String [] args)
    {
//    	Map<String, Set<String>> guessfollows = new HashMap<>();
//    	
//    	Set<String> valueForOneUser = new HashSet<>();
//    	//valueForOneUser.add("Tim");
//    	Set<String> valueForTwoUser = new HashSet<>();
//    	
//    	guessfollows.put("furrukh", valueForOneUser);
//    	//guessfollows.put("ali", valueForTwoUser);
//    	
//    	List<String> influencers = influencers(guessfollows);
//    	
//    	System.out.println(influencers);
    	
    	//test followGraph
    	Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
        Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
       
        Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to @alyssa talk about rivest so much?", d1);
        Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
        Tweet tweet3 = new Tweet(3, "davemat", "junit tests in @Rivest #java8@mikes #software_construction", d2);

        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet2);
        tweets.add(tweet1);
        
        Map<String, Set<String>> test = guessFollowsGraph(tweets);
        
        System.out.println("guess follow graph" +test);
        
        
        List<String> testList = influencers(test);
        System.out.println("influencers at end" +testList);
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
