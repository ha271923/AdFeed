package com.yahoo.mobile.client.android.yodel.platform;

import android.content.Context;
import android.view.View;

import com.flurry.android.ads.FlurryAdNative;

import java.util.Map;

/**
 * Created by ha271 on 2016/11/15.
 */

public abstract class BaseAD {
    public abstract void InitAdServer(Context context);
    public abstract void endTimedEvent(String eventName, Map<String, String> eventParams);
    public abstract void logError(String errorId, String errorDescription, Throwable throwable);
    public abstract void logEvent(String eventName, Map<String, String> eventParams, boolean timed);
    public abstract void loadAdAssetInView(FlurryAdNative adNative, String assetName, View view);
}
