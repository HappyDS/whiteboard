package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yangzhe Xie
 * @date 2019-08-25
 */
public class DateUtil {
    private DateUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String getFormattedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        return formatter.format(new Date());
    }
}
