package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "Custom tweet it is", d2 );
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    @Test
    public void testWrittenBy_OneTweet() {
    	//for one tweet and some other author name
    	List<Tweet> tweets = new ArrayList<>();
    	tweets.add(tweet1);
    	List<Tweet> tweetsOfUser = Filter.writtenBy(tweets, "bbitdiddle");
    	assertEquals(Collections.emptyList(), tweetsOfUser);
    	
    	//for one tweet and correct username
    	tweetsOfUser = Filter.writtenBy(tweets, "alyssa");
        assertEquals(tweetsOfUser.size(), 1);
        assertEquals(tweetsOfUser.get(0).getAuthor(), "alyssa" );
        
    }
    
    @Test
    public void testWrittenBy_OneTweetUppercase() {
    	List<Tweet> tweets = new ArrayList<>();
    	tweets.add(tweet1);
    	    	
    	//for one tweet and correct username
    	List<Tweet> tweetsOfUser = Filter.writtenBy(tweets, "ALySsa");
        assertEquals(tweetsOfUser.size(), 1);
        assertEquals(tweetsOfUser.get(0).getAuthor(), "alyssa" );
    }
    
    @Test
    public void testWrittenBy_ManyTweets() {
    	List<Tweet> tweets = new ArrayList<>();
    	tweets.add(tweet1);
    	tweets.add(tweet2);
    	tweets.add(tweet3);
    	List<Tweet> tweetsOfUser = Filter.writtenBy(tweets, "alyssa");
    	assertEquals(tweetsOfUser.size(), 2);
    	
    	Tweet [] sampleTweets = {tweet1, tweet3};
    	
    	assertEquals(tweetsOfUser.get(0), tweet1);
    	assertEquals(tweetsOfUser.get(1), tweet3);
    }
    
    @Test
    public void testinTimespan() {
    	Instant testStart = Instant.parse("2017-02-17T10:00:00Z");
    	Instant testEnd = Instant.parse("2017-02-17T11:00:00Z"); 
    	
    	List<Tweet> tweetsInTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
    	assertEquals(tweetsInTimespan, Collections.emptyList());
    }
    
    @Test
    public void testinTimespan_oneTweetinTimespan() {
    	Instant testStart = Instant.parse("2016-02-17T10:00:00Z");
    	Instant testEnd = Instant.parse("2016-02-17T10:30:00Z"); 
    	
    	List<Tweet> tweetsInTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
    	assertEquals(1, tweetsInTimespan.size());
    	assertTrue("Expected tweet in returned value", tweetsInTimespan.contains(tweet1));
    }
    
    @Test 
    public void testinTimespan_MorethanOneTweetinTimespan() {
    	Instant testStart = Instant.parse("2016-02-17T10:00:00Z");
    	Instant testEnd = Instant.parse("2016-02-17T11:30:00Z"); 
    	
    	List<Tweet> tweetsInTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
    	assertEquals(2, tweetsInTimespan.size());
    	assertTrue("Expected tweet in returned value", tweetsInTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
    }
    
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    
    
    @Test
    public void testContaining_MoreWords() {
    	List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3), Arrays.asList("custom", "Reasonable"));
    	assertEquals("Expected size of returned List", 2, containing.size());
    	assertTrue("Expected tweets in returned LIst", containing.containsAll(Arrays.asList(tweet1, tweet3)));
    	assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    
    @Test
    public void testContaining_noWords() {
    	List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3), Arrays.asList("wonderfull"));
    	assertEquals(containing , Collections.emptyList());
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
