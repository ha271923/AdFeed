package com.yahoo.mobile.client.android.yodel.platform;

import android.content.Context;
import android.view.View;

import com.flurry.android.ads.FlurryAdNative;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.yahoo.mobile.client.android.yodel.utils.SMLog;

import java.util.Map;

import static com.yahoo.mobile.client.android.yodel.platform.YahooAD.EVENT_STREAM_ARTICLE_CLICK;

/**
 * Created by ha271 on 2016/11/15.
 */

public class LogAD {

    private static YahooAD yahooAD;
    private static GoogleAnalyticsAD googleAD;

    static public void InitAdServer(Context context){
        // TODO:
        if(yahooAD==null){
            yahooAD = new YahooAD();
            yahooAD.InitAdServer(context);
        }
        if(googleAD==null){
            googleAD = new GoogleAnalyticsAD();
            googleAD.InitAdServer(context);
        }

    }

    static public void logError(String errorId, String errorDescription, Throwable throwable) {
        // TODO:
        if(whichVendor(errorId)==Vendors.Yahoo)
            yahooAD.logError(errorId,errorDescription,throwable);
    }

    static public void endTimedEvent(String eventName, Map<String, String> eventParams) {
        // TODO:
        if(whichVendor(eventName)==Vendors.Yahoo)
            yahooAD.endTimedEvent(eventName,eventParams);
    }

    static public void loadAdAssetInView(FlurryAdNative adNative, String assetName, View view) {
        // TODO:
        yahooAD.loadAdAssetInView(adNative,assetName,view);
    }

    static public void logEvent(String eventName, Map<String, String> eventParams, boolean timed) {
        // TODO:
        if(whichVendor(eventName)==Vendors.Yahoo)
            yahooAD.logEvent(eventName, eventParams, timed);
    }
    static public void logScreen(String screenName){
            googleAD.logScreen(screenName);
    }
    private enum Vendors {
        Unknown,
        Yahoo,
        Google,
        MoPub,
        Cheetha,
    }

    private static Vendors whichVendor(String eventName){
        if(eventName.equals(YahooAD.EVENT_STREAM_ARTICLE_CLICK))
            return Vendors.Yahoo;
        if(eventName.equals(YahooAD.EVENT_STREAM_AD_CLICK))
            return Vendors.Yahoo;
        if(eventName.equals(YahooAD.EVENT_STREAM_SEARCH_CLICK))
            return Vendors.Yahoo;
        if(eventName.equals(YahooAD.EVENT_STREAM_PULL_REFRESH))
            return Vendors.Yahoo;
        if(eventName.equals(YahooAD.EVENT_CAR_MOREIMG_CLICK))
            return Vendors.Yahoo;
        if(eventName.equals(YahooAD.EVENT_CAR_LEARNMORE_CLICK))
            return Vendors.Yahoo;
        if(eventName.equals(YahooAD.EVENT_CAR_CONTENT_SWIPE))
            return Vendors.Yahoo;
        if(eventName.equals(YahooAD.EVENT_AD_CLOSEBUTTON_CLICK))
            return Vendors.Yahoo;
        if(eventName.equals(YahooAD.EVENT_SEARCH_STARTED))
            return Vendors.Yahoo;
        if(eventName.equals(YahooAD.EVENT_SEARCH_MOREONWEB_CLICK))
            return Vendors.Yahoo;
        if(eventName.equals(YahooAD.EVENT_SEARCH_RESULT_CLICK))
            return Vendors.Yahoo;
        if(eventName.equals(YahooAD.EVENT_STREAM_ARTICLE_CLICK))
            return Vendors.Yahoo;

        SMLog.i("eventName="+eventName+" is an UNLNOWN type.");
        return Vendors.Unknown;
    }
}
