package com.yahoo.mobile.client.android.yodel.platform;

import android.content.Context;
import android.net.http.HttpResponseCache;
import android.view.View;

import com.flurry.android.FlurryAgent;
import com.tumblr.jumblr.types.Post;
import com.yahoo.mobile.client.android.yodel.FeedApplication;
import com.yahoo.mobile.client.android.yodel.utils.AnalyticsHelper;
import com.yahoo.mobile.client.share.search.settings.SearchSDKSettings;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ha271 on 2016/10/21.
 */

public class YahooAD {
    private static String LOG_TAG = FeedApplication.class.getSimpleName();
    private static final String FLURRY_APIKEY = "JQVT87W7TGN5W7SWY2FH";
    public  static final String FLURRY_ADSPACE = "StaticVideoNativeTest";
    public  static final String YAHOO_SEARCH_APIKEY = "your_api_key";
    public  static final String EXTRA_TAG_QUERY = "com.yahoo.mobile.sample.extra.tagquery";

    //
    public static final String EVENT_STREAM_ARTICLE_CLICK = "stream_article_click";
    public static final String EVENT_STREAM_AD_CLICK = "stream_ad_click";
    public static final String EVENT_STREAM_SEARCH_CLICK = "stream_search_click";
    public static final String EVENT_STREAM_PULL_REFRESH = "stream_pullto_refresh";
    public static final String EVENT_CAR_MOREIMG_CLICK = "carousel_moreimages_click";
    public static final String EVENT_CAR_LEARNMORE_CLICK = "carousel_learnmore_click";
    public static final String EVENT_CAR_CONTENT_SWIPE = "carousel_content_swipe";
    public static final String EVENT_AD_CLOSEBUTTON_CLICK = "ad_closebutton_click";
    public static final String EVENT_SEARCH_STARTED = "search_term_started";
    public static final String EVENT_SEARCH_MOREONWEB_CLICK = "search_moreonweb_click";
    public static final String EVENT_SEARCH_RESULT_CLICK = "search_result_click";

    public static final String PARAM_ARTICLE_ORIGIN = "article_origin";
    public static final String PARAM_ARTICLE_TYPE = "article_type";
    public static final String PARAM_CONTENT_LOADED = "content_loaded";
    public static final String PARAM_SEARCH_TERM = "search_term";

    public void InitAdServer(Context context){
        // Init Search SDK
        SearchSDKSettings.initializeSearchSDKSettings(
                new SearchSDKSettings.Builder(YAHOO_SEARCH_APIKEY)
                        .setVoiceSearchEnabled(true));


            // http response cache
            File httpCacheDir = new File(context.getCacheDir(), "http");
            long httpCacheSize = 100 * 1024 * 1024; // 100 MiB

            try {
                HttpResponseCache.install(httpCacheDir, httpCacheSize);
            } catch (IOException e) {
                AnalyticsHelper.logError(LOG_TAG, "HTTP response cache installation failed", e);
            }

            // Init Flurry
            FlurryAgent.setLogEnabled(true);
            FlurryAgent.init(context, FLURRY_APIKEY);
    }

    public void onPostSelected_LogEvent(Post post, int positionId, View clickedView){
        // Log the event
        HashMap<String, String> eventParams = new HashMap<>(2);
        eventParams.put(FeedApplication.yahooAD.PARAM_ARTICLE_ORIGIN, post.getBlogName());
        eventParams.put(FeedApplication.yahooAD.PARAM_ARTICLE_TYPE, post.getType());
        AnalyticsHelper.logEvent(AnalyticsHelper.EVENT_STREAM_ARTICLE_CLICK, eventParams, false);

    }

    public static void logEvent(String eventName, Map<String, String> eventParams, boolean timed){
        AnalyticsHelper.logEvent(eventName, eventParams, timed);
    }



}
