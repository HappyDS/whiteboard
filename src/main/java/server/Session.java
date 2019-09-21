package server;

import config.Config;

import java.security.SecureRandom;
import java.util.Date;

/*
 * @https://www.mkyong.com/java/java-how-to-generate-a-random-string/
 * */
public class Session {
    private static SecureRandom random = new SecureRandom();
    public String session;
    public long timestamp;
    private Config config = new Config();

    public Session() {
        this.session = generateRandomString();
        Date date = new Date();
        this.timestamp = date.getTime();
    }

    private String generateRandomString() {
        if (this.config.sessionLength < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(this.config.sessionLength);
        for (int i = 0; i < this.config.sessionLength; i++) {

            // 0-62 (exclusive), random returns 0-61
            int rndCharAt = random.nextInt(this.config.sessionDict.length());
            char rndChar = this.config.sessionDict.charAt(rndCharAt);
            sb.append(rndChar);
        }
        return sb.toString();

    }
}
