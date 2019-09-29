/*
This code does not pass one test case. See
https://leetcode.com/problems/design-twitter/discuss/393227/User-can-follow-self!
for details.
*/

class Tweet implements Comparable<Tweet> {
    public int tweetId;
    public int clockTime;
    
    Tweet(int tweetId, int clockTime) {
        this.tweetId = tweetId;
        this.clockTime = clockTime;
    }
    
    @Override
    public int compareTo(Tweet o) {
        return o.clockTime - this.clockTime;
    }
}

class Twitter {
    private int clock = 0;
    private Map<Integer, List<Integer>> followedBy;
    private Map<Integer, List<Tweet>> tweetsByUser;

    /** Initialize your data structure here. */
    public Twitter() {
        tweetsByUser = new HashMap<>();
        followedBy = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if (tweetsByUser.containsKey(userId)) {
            tweetsByUser.get(userId).add(new Tweet(tweetId, ++clock));
        } else {
            List<Tweet> tweets = new LinkedList<>();
            tweets.add(new Tweet(tweetId, ++clock));
            tweetsByUser.put(userId, tweets);
        }
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Tweet> resultTweets = new LinkedList<>();
        if (tweetsByUser.containsKey(userId)) {           
            List<Tweet> tweets = tweetsByUser.get(userId);
            resultTweets.addAll(tweets);
        }
        if (followedBy.containsKey(userId)) {
            for (Integer user : followedBy.get(userId)) {
                if (tweetsByUser.containsKey(user)) {
                    resultTweets.addAll(tweetsByUser.get(user));
                }
            }
        }
        // Sort all returned tweets with timestamp.
        Collections.sort(resultTweets);
        // Get tweet ids of 10 latest tweets.
        /* 
        This can certainly be optimized for larger dataset. We can store tweets per user in
        a dataset such that they are intrinsically sorted by timestamp. We will need to 
        then get 10 tweets from all followed users and self and find out the final 10.
        */
        List<Integer> tweetIds = new LinkedList<>();
        int counter = 0;
        for (Tweet t : resultTweets) {
            tweetIds.add(t.tweetId);
            counter++;
            if (counter >= 10) {
                break;
            }
        }            
        return tweetIds;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if (followedBy.containsKey(followerId)) {
            followedBy.get(followerId).add(followeeId);
        } else {
            List<Integer> followees = new LinkedList<>();
            followees.add(followeeId);
            followedBy.put(followerId, followees);
        }
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if (followedBy.containsKey(followerId)) {
            followedBy.get(followerId).remove(Integer.valueOf(followeeId));;
            if (followedBy.get(followerId).size() == 0) {
                followedBy.remove(followerId);
            }
        } 
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
