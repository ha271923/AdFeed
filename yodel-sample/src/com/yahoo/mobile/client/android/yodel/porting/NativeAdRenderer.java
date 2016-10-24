package com.yahoo.mobile.client.android.yodel.porting;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

// import com.htc.launcher.feeds.ad.htcadadapter.BaseNativeAd;

/**
 * An interface for creating ad views and rendering them using ad data.
 *
 * @param <T> The ad payload type.
 */
public interface NativeAdRenderer<T extends BaseNativeAd> {
	static final int STATIC_NATIVE = 0;
	static final int STATIC_NATIVE_WITH_STAR_RATING = 1;
	static final int FACEBOOK_VIDEO = 2;
	static final int VIDEO = 3;

    /**
     * Creates a new view to be used as an ad.
     *
     * This method is called when you call { com.mopub.nativeads.MoPubStreamAdPlacer#getAdView}
     * and the convertView is null. You must return a valid view.
     *
     * @param activity The activity. Useful for creating a view.
     * @param parent The parent that the view will eventually be attached to. You might use the
     * parent to determine layout parameters, but should return the view without attaching it to the
     * parent.
     * @return A new ad view.
     */
    @NonNull
    View createAdView(@NonNull Activity activity, @Nullable ViewGroup parent);

    /**
     * Renders a view created by {@link #createAdView} by filling it with ad data.
     *
     * @param view The ad {@link View}
     * @param ad The ad data that should be bound to the view.
     */
    void renderAdView(@NonNull View view, @NonNull T ad);

    /**
     * Determines if this renderer supports the type of native ad passed in.
     *
     * @param nativeAd The native ad to render.
     * @return True if the renderer can render the native ad and false if it cannot.
     */
    boolean supports(@NonNull BaseNativeAd nativeAd);

	int getRendererType();
}
