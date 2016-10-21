/*
 * Copyright 2015 Yahoo Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yahoo.mobile.client.android.yodel;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
// import android.os.HandlerThread;
import android.os.Looper;

import com.yahoo.mobile.client.android.yodel.platform.AdInterface;
import com.yahoo.mobile.client.android.yodel.platform.YahooAD;


public class FeedApplication extends Application implements AdInterface {

    private static FeedApplication sApplication;
    private Handler mMainThreadHandler;
    public static YahooAD yahooAD;

    @Override
    public void onCreate() {
        super.onCreate();


        sApplication = this;

        // create handlers
        mMainThreadHandler = new Handler(Looper.getMainLooper());
        //HandlerThread backgroundHandlerThread = new HandlerThread(LOG_TAG); // Hawk: I don't know why do Yahoo create this thread.
        //backgroundHandlerThread.start(); // Hawk: I don't know why do Yahoo create this thread.

        initServer(sApplication);

    }

    @Override
    public void initServer(Context context) {

        yahooAD = new YahooAD();
        yahooAD.InitAdServer(context);

    }




    public static FeedApplication getInstance() {
        return sApplication;
    }

    public Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }
}
