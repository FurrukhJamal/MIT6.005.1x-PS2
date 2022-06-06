package twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        //throw new RuntimeException("not implemented");
    	if(tweets.size() > 0)
    	{
    		tweets = sortByTimeStamp(tweets);
        	
       		Instant start = tweets.get(0).getTimestamp();
           	Instant end = tweets.get(tweets.size()- 1).getTimestamp();
           	return new Timespan(start, end);
    	}
    	else 
    	{
    		return new Timespan(Instant.parse("2016-02-17T10:00:00Z") , Instant.parse("2016-02-17T10:00:00Z"));
    	}
    	
    	
    	
    	
    	
    	//return new Timespan(start, end);
    }
    
    
    public static List<Tweet> sortByTimeStamp(List<Tweet> tweets){
    	
    	Comparator<Tweet> byTimeStamp = new Comparator<Tweet>() {
    		
    		@Override public int compare(Tweet tweet1, Tweet tweet2) {
    			return tweet1.getTimestamp().compareTo(tweet2.getTimestamp());
    		}
    	};
    	
    	return tweets.stream().sorted(byTimeStamp).collect(Collectors.toList());
    }
    
    
    

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        //throw new RuntimeException("not implemented");
    	Set<String> mentionedUsers = new HashSet<>();
    	String pattern = "\\B@[A-Za-z0-9-_]+\\b";
    	//String pattern = "\\B@[A-Za-z0-9-_(.|,|'|?|:|;|)|(|}|{|]|[|!)]+\\b";
    	
    	Pattern p = Pattern.compile(pattern);
    	//System.out.println(p);
    	for(Tweet tweet:tweets)
    	{
    		String text = tweet.getText();
    		Matcher m = p.matcher(text);
    		//System.out.println(m);
    		
    		while (m.find()) {
                String userMentioned = m.group().substring(1).toLowerCase();
                //System.out.println(userMentioned);
//                if(!userMentioned.contains("!") && !userMentioned.contains("}") && !userMentioned.contains("{") && !userMentioned.contains("(") && !userMentioned.contains(")") && !userMentioned.contains("]") && !userMentioned.contains("[") && !userMentioned.contains("!") && !userMentioned.contains(".") && !userMentioned.contains("'") && !userMentioned.contains(",") && !userMentioned.contains("?") && !userMentioned.contains("!") && !userMentioned.contains(":") && !userMentioned.contains(";"))
//                {
//                	mentionedUsers.add(userMentioned);
//                }
                mentionedUsers.add(userMentioned);
                
            }
    	}
    	//System.out.println("Set of mentioned userNames");
    	//System.out.println(mentionedUsers);
    	return mentionedUsers;
    	
    }
    
    
    public static void main(String [] args) {
    	Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
        Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
       
       Tweet tweet1 = new Tweet(1, "alyssa", "is it @fj reasonable to @alyssa talk about rivest so much?", d1);
       Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
       Tweet tweet3 = new Tweet(3, "davemat", "junit tests in @Rivest #java8@mikes #software_construction", d2);

       List<Tweet> tweets = new ArrayList<>();
       tweets.add(tweet3);
       tweets.add(tweet1);
       
       
       getTimespan(tweets);
       
       Set<String> test = getMentionedUsers(tweets);
       System.out.println(test);
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
