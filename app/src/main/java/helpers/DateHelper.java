package helpers;

import com.nurolopher.currency.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nursultan on 14-Feb 15.
 */
public class DateHelper {

    public static String getDateDiff(String lastUpdateDateStr) {
        Date lastUpdateDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            lastUpdateDate = dateFormat.parse(lastUpdateDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date currentDate = new Date();

        long timeDiff = currentDate.getTime() - lastUpdateDate.getTime();
        long seconds = timeDiff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        String defaultText = "Currencies has been successfully updated";
        StringBuilder toastMessage = new StringBuilder();

        seconds = seconds - minutes * 60;
        minutes = minutes - hours * 60;
        hours = hours - days * 24;
        if (days > 0) {
            toastMessage.append(days + " days");
        }
        if (hours > 0) {
            toastMessage.append(hours + " hours ");
        }
        if (minutes > 0) {
            toastMessage.append(minutes + " minutes ");
        }
        if (seconds > 0) {
            toastMessage.append(seconds + " seconds ago");

        }
        if (timeDiff / 1000 > 0) {
            defaultText = "Last updated ";
        }
        toastMessage.insert(0, defaultText);
        return toastMessage.toString();

    }
}
