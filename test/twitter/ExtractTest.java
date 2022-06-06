package twitter;

import static org.junit.Assert.*;

import java.util.List;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "davemat", "junit tests in @Rivest #java8@mikes #software_construction", d2);
    private static final Tweet tweet4 = new Tweet(4, "davemat", "junit tests in @Rivest and @furrukh #java8@mikes #software_construction", d2);
    private static final Tweet tweet5 = new Tweet(5, "davemat", "junit tests in @dot.test @Rivest and @furrukh some text @test's #java8@mikes #software_construction", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    @Test
    public void testgetTimespan() {
    	//when there is one tweet
    	List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet1);
        Timespan timespan = Extract.getTimespan(tweets);
        
        assertEquals("start == end", timespan.getStart(), timespan.getEnd());
        
        assertEquals("start ==d1", timespan.getStart(), d1);
        
        //when size of tweet is greater than 1
        tweets.add(tweet2);
        
        Timespan timeSpanForTwoTweets = Extract.getTimespan(tweets);
        
        
        assertEquals(timeSpanForTwoTweets.getStart(),  d1);
        
        assertEquals(timeSpanForTwoTweets.getEnd(),  d2);
        
        
        		
    	
    }
    
    @Test
    public void testGetMentionedUsers() {
    	List<Tweet> tweets = new ArrayList<>();
    	tweets.add(tweet1);
    	
    	// when there is just one tweet with no mention
    	Set<String> set = Extract.getMentionedUsers(tweets);
    	assertEquals(Collections.emptySet(), set);
    	
    	
    	
    }
    
    @Test
    public void testGetMentionedUsers_oneMention() {
    	List<Tweet> tweets = new ArrayList<>();
    	tweets.add(tweet3);
    	Set<String> set = Extract.getMentionedUsers(tweets);
    	assertEquals(set.size(), 1);
    	
    	
    
    }
    
    @Test 
    public void testGetMentionedUsers_TwoMentions() {
    	List<Tweet> tweets = new ArrayList<>();
    	tweets.add(tweet4);
    	
    	Set<String> set = Extract.getMentionedUsers(tweets);
    	assertEquals(set.size(), 2);
    }
    
    
    
    @Test
    public void testGetMentioned_onePlusTweetOnePlusMention() {
    	List<Tweet> tweets = new ArrayList<>();
    	tweets.add(tweet4);
    	tweets.add(tweet3);
    	
    	String [] testUsers = {"rivest", "furrukh"};
    	Set<String> set = Extract.getMentionedUsers(tweets);
//    	int counter = 0;
//    	for (String user : set)
//    	{
//    		assertEquals(user, testUsers[counter]);
//    		counter++;
//    		
//    	}
    	
    	assertTrue("expected mention of usernames in the set", set.containsAll(Arrays.asList("rivest", "furrukh")));
    	
    	

    }
    
    @Test 
    public void testGetMentionedUsers_UserNamesEndedWithPunctuationBug() {
    	List<Tweet> tweets = new ArrayList<>();
    	tweets.add(tweet5);
    	
    	Set<String> set = Extract.getMentionedUsers(tweets);
    	System.out.println("Set gotten in Test: " +set);
    	System.out.println(set);
    	assertEquals( 2, set.size());
    	assertTrue("Testing with a username having a punctuation mark", set.containsAll(Arrays.asList("furrukh", "rivest")) );
    	
    }
    
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
