package fr.punk;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 */
public class PusherMain {

    private static final Twitter twitter = TwitterFactory.getSingleton();

    public static void main (String[] args) throws Exception {
            post("","");
    }

    private static void post(String line1, String line2) {
        try {
            twitter.updateStatus(line1+"\n"+line2);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}

