package com.yahoo.mobile.client.android.yodel.porting;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.yahoo.mobile.client.android.yodel.utils.SMLog;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This is the base class for implementations of all native ad formats. When implementing a new
 * native ad format, be sure to also implement and register an appropriate AdRender
 * that supports the format.
 */
public abstract class BaseNativeAd {
	private static final String LOG_TAG = BaseNativeAd.class.getSimpleName();

    public interface NativeEventListener {
        void onAdImpressed();
        void onAdClicked();
    }

    @NonNull final protected Set<String> mImpressionTrackers;
    @NonNull final protected Set<String> mClickTrackers;
    @Nullable protected NativeEventListener mNativeEventListener;

	public static final double MIN_STAR_RATING = 0;
	public static final double MAX_STAR_RATING = 5;

	// Basic fields
	@Nullable protected String mIconImageUrl;
	@Nullable protected String mClickDestinationUrl;
	@Nullable protected String mCallToAction;
	@Nullable protected String mTitle;
	@Nullable protected String mText;
	@Nullable protected String mPrivacyInformationIconClickThroughUrl;
	@Nullable protected String mPrivacyInformationIconImageUrl;
	@Nullable protected Double mStarRating;


	// Extras
	@NonNull protected final Map<String, Object> mExtras;

    protected BaseNativeAd() {
        SMLog.i();
        mImpressionTrackers = new HashSet<String>();
        mClickTrackers = new HashSet<String>();

	    mExtras = new HashMap<String, Object>();
    }

    // Lifecycle Handlers
    /**
     * Your {@link BaseNativeAd} subclass should implement this method if the network requires the developer
     * to prepare state for recording an impression or click before a view is rendered to screen.
     *
     * This method is optional.
     */
    public abstract void prepare(@NonNull final View view);

    /**
     * Your {@link BaseNativeAd} subclass should implement this method if the network requires the developer
     * to reset or clear state of the native ad after it goes off screen and before it is rendered
     * again.
     *
     * This method is optional.
     */
    public abstract void clear(@NonNull final View view);

    /**
     * Your {@link BaseNativeAd} subclass should implement this method if the network requires the developer
     * to destroy or cleanup their native ad when they are permanently finished with it.
     *
     * This method is optional.
     */
    public abstract void destroy();

    public void setNativeEventListener(
            @Nullable final NativeEventListener nativeEventListener) {SMLog.i();
        mNativeEventListener = nativeEventListener;
    }

    // Event Notifiers
    /**
     * Notifies the SDK that the ad has been shown. This will cause the SDK to record an impression
     * for the ad. This method must be called when the native ad is impressed in order for the
     * MoPub impression trackers to fire correctly.
     */
    protected final void notifyAdImpressed() {SMLog.i();
        if (mNativeEventListener != null) {
            mNativeEventListener.onAdImpressed();
        }
    }

    /**
     * Notifies the SDK that the user has clicked the ad. This will cause the SDK to record an
     * click for the ad. This method must be called when the native ad is clicked in order for the
     * MoPub click trackers to fire correctly.
     */
    protected final void notifyAdClicked() {SMLog.i();
        if (mNativeEventListener != null) {
            mNativeEventListener.onAdClicked();
        }
    }

    final protected void addImpressionTrackers(final Object impressionTrackers) throws ClassCastException {SMLog.i();
        if (!(impressionTrackers instanceof JSONArray)) {
            throw new ClassCastException("Expected impression trackers of type JSONArray.");
        }

        final JSONArray trackers = (JSONArray) impressionTrackers;
        for (int i = 0; i < trackers.length(); i++) {
            try {
                addImpressionTracker(trackers.getString(i));
            } catch (JSONException e) {
                // This will only occur if we access a non-existent index in JSONArray.
                SMLog.d(LOG_TAG, "Unable to parse impression trackers.");
            }
        }
    }

    final protected void addClickTrackers(final Object clickTrackers) throws ClassCastException {SMLog.i();
        if (!(clickTrackers instanceof JSONArray)) {
            throw new ClassCastException("Expected click trackers of type JSONArray.");
        }

        final JSONArray trackers = (JSONArray) clickTrackers;
        for (int i = 0; i < trackers.length(); i++) {
            try {
                addClickTracker(trackers.getString(i));
            } catch (JSONException e) {
                // This will only occur if we access a non-existent index in JSONArray.
                SMLog.d(LOG_TAG, "Unable to parse click trackers.");
            }
        }
    }

    final public void addImpressionTracker(@NonNull final String url) {SMLog.i();
        if (TextUtils.isEmpty(url)) {
            SMLog.d(LOG_TAG, "impressionTracker url is not allowed to be null");
            return;
        }
        mImpressionTrackers.add(url);
    }

    final public void addClickTracker(@NonNull final String url) {SMLog.i();
	    if (TextUtils.isEmpty(url))  {
            SMLog.d(LOG_TAG, "clickTracker url is not allowed to be null");
            return;
        }
        mClickTrackers.add(url);
    }

    /**
     * Returns a Set<String> of all impression trackers associated with this native ad. Note that
     * network requests will automatically be made to each of these impression trackers when the
     * native ad is display on screen. See StaticNativeAd#getImpressionMinPercentageViewed
     * and StaticNativeAd#getImpressionMinTimeViewed() for relevant
     * impression-tracking parameters.
     */
    @NonNull
    Set<String> getImpressionTrackers() {SMLog.i();
        return new HashSet<String>(mImpressionTrackers);
    }

    /**
     * Returns a Set<String> of all click trackers associated with this native ad. Note that
     * network requests will automatically be made to each of these click trackers when the
     * native ad is clicked.
     */
    @NonNull
    Set<String> getClickTrackers() {SMLog.i();
        return new HashSet<String>(mClickTrackers);
    }

	/**
	 * Returns the String url corresponding to the ad's icon image.
	 */
	@Nullable
	final public String getIconImageUrl() {SMLog.i();
		return mIconImageUrl;
	}

	/**
	 * Returns the String url that the device will attempt to resolve when the ad is clicked.
	 */
	@Nullable
	final public String getClickDestinationUrl() {SMLog.i();
		return mClickDestinationUrl;
	}

	/**
	 * Returns the Call To Action String (i.e. "Download" or "Learn More") associated with this ad.
	 */
	@Nullable
	final public String getCallToAction() {SMLog.i();
		return mCallToAction;
	}

	/**
	 * Returns the String corresponding to the ad's title.
	 */
	@Nullable
	final public String getTitle() {SMLog.i();
		return mTitle;
	}

	/**
	 * Returns the String corresponding to the ad's body text.
	 */
	@Nullable
	final public String getText() {SMLog.i();
		return mText;
	}

	/**
	 * Returns the Privacy Information click through url. No Privacy Information Icon will be shown
	 * unless this is set to something non-null using setPrivacyInformationIconClickThroughUrl()
	 *
	 * @return String representing the Privacy Information Icon click through url, or {@code null}
	 * if not set.
	 */
	@Nullable
	final public String getPrivacyInformationIconClickThroughUrl() {SMLog.i();
		return mPrivacyInformationIconClickThroughUrl;
	}

	/**
	 * Returns the Privacy Information image url.
	 *
	 * @return String representing the Privacy Information Icon image url, or {@code null} if not
	 * set.
	 */
	@Nullable
	public String getPrivacyInformationIconImageUrl() {SMLog.i();
		return mPrivacyInformationIconImageUrl;
	}

	final public void setPrivacyInformationIconClickThroughUrl(
		@Nullable final String privacyInformationIconClickThroughUrl) {SMLog.i();
		mPrivacyInformationIconClickThroughUrl = privacyInformationIconClickThroughUrl;
	}

	final public void setPrivacyInformationIconImageUrl(
		@Nullable String privacyInformationIconImageUrl) {SMLog.i();
		mPrivacyInformationIconImageUrl = privacyInformationIconImageUrl;
	}

	// Extras Getters
	/**
	 * Given a particular String key, return the associated Object value from the ad's extras map.
	 */
	@Nullable
	final public Object getExtra(@NonNull final String key) {SMLog.i();
		if (TextUtils.isEmpty(key)){
            SMLog.w(LOG_TAG, "getExtra key is not allowed to be null");
			return null;
		}
		return mExtras.get(key);
	}

	/**
	 * Returns a copy of the extras map, reflecting additional ad content not reflected in any
	 * of the above hardcoded setters. This is particularly useful for passing down custom fields
	 * with MoPub's direct-sold native ads or from mediated networks that pass back additional
	 * fields.
	 */
	@NonNull
	final public Map<String, Object> getExtras() {SMLog.i();
		return new HashMap<String, Object>(mExtras);
	}

	final public void addExtra(final String key, final Object value) {SMLog.i();
		if (TextUtils.isEmpty(key)) {
            SMLog.w(LOG_TAG, "addExtra key is not allowed to be null");
			return;
		}
		mExtras.put(key, value);
	}

	public final void setStarRating(@Nullable final Double starRating) {SMLog.i();
		if (starRating == null) {
			mStarRating = null;
		} else if (starRating >= MIN_STAR_RATING && starRating <= MAX_STAR_RATING) {
			mStarRating = starRating;
		} else {
            // SMLog.d(LOG_TAG, "Ignoring attempt to set invalid star rating (%s). Must be between %s and %s .", starRating, MIN_STAR_RATING, MAX_STAR_RATING);
            SMLog.d(LOG_TAG, "Ignoring attempt to set invalid star rating "+starRating+". Must be between "+MIN_STAR_RATING+" and %s ."+ MAX_STAR_RATING);
		}
	}

	@Nullable
	final public Double getStarRating() {SMLog.i();
		return mStarRating;
	}

	public final void setIconImageUrl(@Nullable final String iconImageUrl) {SMLog.i();
		mIconImageUrl = iconImageUrl;
	}

	public final void setClickDestinationUrl(@Nullable final String clickDestinationUrl) {SMLog.i();
		mClickDestinationUrl = clickDestinationUrl;
	}

	public final void setCallToAction(@Nullable final String callToAction) {SMLog.i();
		mCallToAction = callToAction;
	}

	final public void setTitle(@Nullable final String title) {SMLog.i();
		mTitle = title;
	}

	public final void setText(@Nullable final String text) {SMLog.i();
		mText = text;
	}

	// TODO: change return type AdRenderer
	public abstract NativeAdRenderer getAdRenderer();

    // Hawk TODO: integrate this line will need more code, so comment it.
/*	protected Rect mNativeAdViewRect = new Rect(0, 0,
		FeedGridLayoutHelper.getFeedGridViewFullWidth(),
		FeedGridLayoutHelper.getMoPubAdsViewHeight()); */
    protected Rect mNativeAdViewRect = new Rect(0, 0, 0, 0 );

	public Rect getViewRect() {SMLog.i();
		return mNativeAdViewRect;
	}

	public int getViewHeight() {SMLog.i();
		return mNativeAdViewRect.height();
	}

	public int getViewWidth() {SMLog.i();
		return mNativeAdViewRect.width();
	}
}
