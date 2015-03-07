package com.nurolopher.currency;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nursultan on 14-Feb 15.
 */
public class DateHelper {

    public static long[] getDateDiff(String lastUpdateDateStr) {
        long[] dateDiff = new long[5];
        Date lastUpdateDate;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);

        try {
            lastUpdateDate = dateFormat.parse(lastUpdateDateStr);
        } catch (ParseException e) {
            lastUpdateDate = new Date();
        }
        Date currentDate = new Date();

        long timeDiff = currentDate.getTime() - lastUpdateDate.getTime();
        long seconds = timeDiff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;


        seconds = seconds - minutes * 60;
        minutes = minutes - hours * 60;
        hours = hours - days * 24;

        dateDiff[0] = days;
        dateDiff[1] = hours;
        dateDiff[2] = minutes;
        dateDiff[3] = seconds;
        dateDiff[4] = timeDiff;

        return dateDiff;

    }
}
