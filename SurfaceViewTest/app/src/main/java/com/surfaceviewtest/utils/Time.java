package com.surfaceviewtest.utils;


import android.util.Log;

import java.util.Calendar;

public class Time {
    private static final String TAG = "Time";

    public static TimeSet getTime() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        int millis = now.get(Calendar.MILLISECOND);
        Log.d(TAG, "year:" + year + ", month:" + month + ", day:" + day + ", hour:" + hour + ", minute:" + minute + ", second:" + second + ",millis:" + millis);
        return new TimeSet(year, month, day, hour, minute, second, millis);

    }
}
