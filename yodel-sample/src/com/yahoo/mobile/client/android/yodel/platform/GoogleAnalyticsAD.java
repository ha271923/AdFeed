package com.yahoo.mobile.client.android.yodel.platform;

import android.content.Context;
import android.net.http.HttpResponseCache;
import android.view.View;

import com.flurry.android.FlurryAgent;
import com.flurry.android.ads.FlurryAdNative;
import com.flurry.android.ads.FlurryAdNativeAsset;
import com.yahoo.mobile.client.android.yodel.utils.AnalyticsHelper;
import com.yahoo.mobile.client.share.search.settings.SearchSDKSettings;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by ha271 on 2016/11/15.
 */

public class GoogleAnalyticsAD extends BaseAD {

    @Override
    public void InitAdServer(Context context){
        // TODO:
    }


    @Override
    public void logError(String errorId, String errorDescription, Throwable throwable) {
        // TODO:
    }
    @Override
    public void endTimedEvent(String eventName, Map<String, String> eventParams) {
        // TODO:
    }
    @Override
    public void loadAdAssetInView(FlurryAdNative adNative, String assetName, View view) {
        // TODO:
    }

    @Override
    public void logEvent(String eventName, Map<String, String> eventParams, boolean timed) {
        // TODO:
    }

}
