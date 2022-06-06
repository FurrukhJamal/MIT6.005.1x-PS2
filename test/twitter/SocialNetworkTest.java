package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     */
	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "Custom tweet it is", d2 );
    private static final Tweet tweet4 = new Tweet(4, "furrukh", "Great to meet @alyssa yesterday", d2);
    private static final Tweet tweet5 = new Tweet(5, "alyssa", "Great to meet @bbitdiddle yesterday", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    @Test
    public void testGuessFollowsGraphOneTweetOneFollowing() {
    	List<Tweet> tweets = new ArrayList<>();
    	tweets.add(tweet4);
    	
    	Map<String, Set<String>> guessFollowers = SocialNetwork.guessFollowsGraph(tweets);
    	assertTrue("expected key is there", guessFollowers.containsKey("furrukh"));
    	
    	Set<String> valueOfKey = new HashSet<>();
    	valueOfKey.add("alyssa");
    	
    	Map<String, Set<String>> TestMap = new HashMap<>();
    	TestMap.put("furrukh", valueOfKey);
    	
    	assertEquals(guessFollowers, TestMap);
    	
    	//assertTrue("expected vallue for a key is there", guessFollowers.containsValue)
    }
    
    @Test
    public void GuessFollowsGraphTwoTweetOneFollowing() {
    	List<Tweet> tweets = new ArrayList<>();
    	tweets.add(tweet4);
    	tweets.add(tweet3);
    	
    	Map<String, Set<String>> guessFollowers = SocialNetwork.guessFollowsGraph(tweets);
    	assertTrue("expected key furrukh is there", guessFollowers.containsKey("furrukh"));
    	assertTrue("expected key alyssa is there", guessFollowers.containsKey("alyssa"));
    	
    	Set<String> valueOfKey = new HashSet<>();
    	valueOfKey.add("alyssa");
    	
    	Map<String, Set<String>> TestMap = new HashMap<>();
    	TestMap.put("furrukh", valueOfKey);
    	TestMap.put("alyssa", new HashSet<String>());
    	
    	assertEquals(guessFollowers, TestMap);
    	
    }
    
    @Test
    public void GuessFollowsGraphMoreTweetsMoreFollowing() {
    	List<Tweet> tweets = new ArrayList<>();
    	tweets.add(tweet4);
    	tweets.add(tweet3);
    	tweets.add(tweet2);
    	tweets.add(tweet1);
    	tweets.add(tweet5);
    	
    	
    	Map<String, Set<String>> guessFollowers = SocialNetwork.guessFollowsGraph(tweets);
    	assertTrue("expected key furrukh is there", guessFollowers.containsKey("furrukh"));
    	assertTrue("expected key alyssa is there", guessFollowers.containsKey("alyssa"));
    	
    	Set<String> valueOfKeyforFurrukh = new HashSet<>();
    	valueOfKeyforFurrukh.add("alyssa");
    	
    	Set<String> valueOfKeyforAlyssa = new HashSet<>();
    	valueOfKeyforAlyssa.add("bbitdiddle");
    	
    	Map<String, Set<String>> TestMap = new HashMap<>();
    	TestMap.put("furrukh", valueOfKeyforFurrukh);
    	TestMap.put("alyssa", valueOfKeyforAlyssa);
    	TestMap.put("bbitdiddle", new HashSet<>());
    	
    	
    	assertEquals(guessFollowers, TestMap);
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }
    
    @Test
    public void testInfluencer_OneTweetOneFollower() {
    	List<Tweet> tweets = new ArrayList<>();
    	tweets.add(tweet4);
    	
    	Map<String, Set<String>> guessfollows = new HashMap<>();
    	Set<String> valueForKeyFurrukh = new HashSet<>();
    	valueForKeyFurrukh.add("alyssa");
    	guessfollows.put("furrukh", valueForKeyFurrukh);
    	
    	List<String> influencers = SocialNetwork.influencers(guessfollows);
    	assertEquals(influencers.get(0), "alyssa");
    }
    
    @Test 
    public void testInfluencer_ManyTweetsManyFollower() {
    	Map<String, Set<String>> guessfollows = new HashMap<>();
    	Set<String> valueForKeyFurrukh = new HashSet<>() ;
    	valueForKeyFurrukh.addAll(Arrays.asList("alyssa", "jhon", "smith"));
    	Set<String> valueForKeyAlyssa = new HashSet<>();
    	valueForKeyAlyssa.addAll(Arrays.asList("jhon", "smith"));
    	Set<String> valueForKeyTim = new HashSet<>();
    	valueForKeyTim.add("alyssa");
    	
    	guessfollows.put("furrukh", valueForKeyFurrukh);
    	guessfollows.put("alyssa", valueForKeyAlyssa);
    	guessfollows.put("tim", valueForKeyTim);
    	
    	List<String> influencers = SocialNetwork.influencers(guessfollows);
    	assertEquals("smith", influencers.get(0));
    	assertEquals ("jhon", influencers.get(1));
    	assertEquals("alyssa", influencers.get(2));
    }
    
    @Test 
    public void testInfluencersGraphOneUserNotFollowing() {
    	Map<String, Set<String>> guessfollows = new HashMap<>();
    	
    	Set<String> valueForOneUser = new HashSet<>();
    	
    	guessfollows.put("furrukh", valueForOneUser);
    	
    	List<String> influencers = SocialNetwork.influencers(guessfollows);
    	assertFalse("expected an non empty list with a key furrukh", influencers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
