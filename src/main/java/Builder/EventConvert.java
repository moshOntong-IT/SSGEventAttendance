package Builder;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EventConvert { // superclass

    public static void parseAATime(String date, EventDateTimeBuilder builder) throws ParseException {
        SimpleDateFormat dateFormat
                = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        DateFormat dF = new SimpleDateFormat("hh:mm aa");
        Date pre_format = dateFormat.parse(date);
        builder.formatterParseDateTime(dF.format(pre_format) + "");
    }

    public static void greaterThan(String time1, String time2, EventDateTimeBuilder builder) throws ParseException {
        DateFormat dF = new SimpleDateFormat("hh:mm aa");
        Date d = dF.parse(time1);
        Date d2 = dF.parse(time2);

        long t = d.getTime();
        long t2 = d2.getTime();
//        System.out.println(t +"\n"+t2);
        String result = "";
        if (t < t2) {
            result = "Not Ready";
        } else {
            result = "Ready";
        }

        builder.formatterParseDateTime(result);
    }

    public static boolean lessThan(String time1, String time2) throws ParseException {
        DateFormat dF = new SimpleDateFormat("hh:mm aa");
        Date d = dF.parse(time1);
        Date d2 = dF.parse(time2);

        long t = d.getTime();
        long t2 = d2.getTime();
//        System.out.println(t +"\n"+t2);
        String result = "";
        if (t < t2) {
            return true;
        } else {
           return false;
        }

    }

    public static void parseDate(EventDateTimeBuilder builder) {
        SimpleDateFormat dateFormat
                = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        builder.formatterParseDateTime(dateFormat.format(now));
    }

    public static void dateWordDateFormat(String eventDate, EventDateTimeBuilder builder) {
        SimpleDateFormat sdf
                = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat normalFormat
                = new SimpleDateFormat(
                "MMMM dd yyyy");

        try {
            Date d2 = sdf.parse(eventDate);
            builder.formatterParseDateTime(normalFormat.format(d2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void getDuration(String today, String beginDate, EventDateTimeBuilder builder) throws ParseException {

        SimpleDateFormat sdf
                = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");


        Date d1 = sdf.parse(today);
        Date d2 = sdf.parse(beginDate);

        long difference_In_Time
                = d2.getTime() - d1.getTime();

        long difference_In_Days
                = TimeUnit
                .MILLISECONDS
                .toDays(difference_In_Time)
                % 365;
//        System.out.println(difference_In_Days);


        builder.formatterParseDateTime(difference_In_Days + "");


    }

}
