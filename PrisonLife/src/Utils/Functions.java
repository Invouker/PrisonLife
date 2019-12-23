// 
// Decompiled by Procyon v0.5.36
// 

package Utils;

import java.util.concurrent.TimeUnit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Functions
{
    public static boolean isNum(final String number) {
        try {
            Integer.parseInt(number);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    public static int getYaw(final float yaw) {
        if (yaw >= 315.0f && yaw <= 45.0f) {
            return 360;
        }
        if (yaw >= 46.0f && yaw <= 135.0f) {
            return 90;
        }
        if (yaw >= 136.0f && yaw <= 225.0f) {
            return 180;
        }
        if (yaw >= 226.0f && yaw <= 314.0f) {
            return 270;
        }
        return 90;
    }
    
    public static String stringBuilder(final int p1, final String[] args) {
        String builder = "";
        for (int i = p1; i < args.length; ++i) {
            builder = String.valueOf(builder) + args[i] + " ";
        }
        builder = builder.trim();
        return builder;
    }
    
    public static String substring(final int a, final int b, final String temp) {
        String c = "";
        for (int i = a; i < b; ++i) {
            final char ch1 = temp.charAt(i);
            c = String.valueOf(c) + ch1;
        }
        return c;
    }
    
    public static boolean rangeOf(final int one, final int two, final int number) {
        return one <= number && two >= number;
    }
    
    public static int random(final int min, final int max) {
        final Random rand = new Random();
        return rand.nextInt(max - min + min);
    }
    
    public static String timestampToDate(final long timestamp) {
        final Date date = new Date(timestamp);
        final DateFormat formatter = new SimpleDateFormat("dd.MM.YYYY");
        final String dateFormatted = formatter.format(date);
        return dateFormatted;
    }
    
    public static String upperCaseWords(final String sentence) {
        final String[] words = sentence.replaceAll("\\s+", " ").trim().split(" ");
        String newSentence = "";
        String[] array;
        for (int length = (array = words).length, j = 0; j < length; ++j) {
            final String word = array[j];
            for (int i = 0; i < word.length(); ++i) {
                newSentence = String.valueOf(newSentence) + ((i == 0) ? word.substring(i, i + 1).toUpperCase() : ((i != word.length() - 1) ? word.substring(i, i + 1).toLowerCase() : (String.valueOf(word.substring(i, i + 1).toLowerCase().toLowerCase()) + " ")));
            }
        }
        return newSentence;
    }
    
    public static String getTime(final int secondsx) {
        final int days = (int)TimeUnit.SECONDS.toDays(secondsx);
        final int hours = (int)(TimeUnit.SECONDS.toHours(secondsx) - TimeUnit.DAYS.toHours(days));
        final int minutes = (int)(TimeUnit.SECONDS.toMinutes(secondsx) - TimeUnit.HOURS.toMinutes(hours) - TimeUnit.DAYS.toMinutes(days));
        final int seconds = (int)(TimeUnit.SECONDS.toSeconds(secondsx) - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.DAYS.toSeconds(days));
        if (days != 0) {
            return String.valueOf(days) + "d, " + hours + "h, " + minutes + "m, " + seconds + "s";
        }
        if (hours != 0) {
            return String.valueOf(hours) + "h, " + minutes + "m, " + seconds + "s";
        }
        if (minutes != 0) {
            return String.valueOf(minutes) + "m, " + seconds + "s";
        }
        return String.valueOf(seconds) + "s";
    }
}
