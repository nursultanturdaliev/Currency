package com.nurolopher.currency;

import java.util.HashMap;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class SomConverterApp extends Application {

    private static final String PROPERTY_ID = "UA-60747238-1";

    public static int GENERAL_TRACKER = 0;

    public enum TrackerName {
        APP_TRACKER, GLOBAL_TRACKER, ECOMMERCE_TRACKER,
    }

    public HashMap mTrackers = new HashMap();

    public SomConverterApp() {
        super();
    }

    public synchronized Tracker getTracker(TrackerName appTracker) {
        if (!mTrackers.containsKey(appTracker)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (appTracker == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID) : (appTracker == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker) : analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(appTracker, t);
        }
        return (Tracker) mTrackers.get(appTracker);
    }
}