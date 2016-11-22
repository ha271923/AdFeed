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

package com.yahoo.mobile.client.android.yodel.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yahoo.mobile.client.android.yodel.FeedApplication;
import com.yahoo.mobile.client.android.yodel.R;
import com.tumblr.jumblr.types.Post;
import com.yahoo.mobile.client.android.yodel.platform.LogAD;
import com.yahoo.mobile.client.android.yodel.platform.YahooAD;
import com.yahoo.mobile.client.android.yodel.utils.AnalyticsHelper;
import com.yahoo.mobile.client.android.yodel.utils.PermissionUtils;
import com.yahoo.mobile.client.android.yodel.utils.SMLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity extends ActionBarActivity implements PostListFragment.Callbacks, ActivityCompat.OnRequestPermissionsResultCallback {

    public static final int UNUSED_REQUEST_CODE = 255;
    private static final List<String> REQUIRED_DANGEROUS_PERMISSIONS = new ArrayList<String>();
    static {
        REQUIRED_DANGEROUS_PERMISSIONS.add(ACCESS_FINE_LOCATION);
        REQUIRED_DANGEROUS_PERMISSIONS.add(RECORD_AUDIO);
        REQUIRED_DANGEROUS_PERMISSIONS.add(WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, PostListFragment.newInstance())
                    .commit();
        }

        getPermissions(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Uncomment the line below if targeting any API level less than API 14
        // FlurryAgent.onStartSession(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // HTTP response cache
        HttpResponseCache cache = HttpResponseCache.getInstalled();
        if (cache != null) {
            cache.flush();
        }

        // Uncomment the line below if targeting any API level less than API 14
        // FlurryAgent.onEndSession(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager)getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(
                    new ComponentName(this, PostSearchActivity.class)));
            searchView.setQueryRefinementEnabled(true);
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogAD.logEvent(
                            YahooAD.EVENT_STREAM_SEARCH_CLICK, null, false);
                }
            });
        }

        menu.findItem(R.id.action_load_AD).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SMLog.i("Hawk","onMenuItemClick --> Load AD");
                return false;
            }
        });
        menu.findItem(R.id.action_unload_AD).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SMLog.i("Hawk","onMenuItemClick --> UnLoad AD");
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onPostSelected(Post post, int positionId, View clickedView) {
        // log user event
        HashMap<String, String> eventParams = new HashMap<>(2);
        eventParams.put(YahooAD.PARAM_ARTICLE_ORIGIN, post.getBlogName());
        eventParams.put(YahooAD.PARAM_ARTICLE_TYPE, post.getType());
        LogAD.logEvent(YahooAD.EVENT_STREAM_ARTICLE_CLICK, eventParams, false);
        LogAD.logScreen(post.getBlogName());

        Intent intent = new Intent(this, PostDetailActivity.class);
        intent.putExtra(PostDetailActivity.EXTRA_CURRENT_PAGE_INDEX, positionId);
        startActivity(intent);
    }

    private void getPermissions(Context context){
        List<String> permissionsToBeRequested = new ArrayList<String>();
        for (String permission : REQUIRED_DANGEROUS_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, permission)!=
                    PackageManager.PERMISSION_GRANTED) {
                permissionsToBeRequested.add(permission);
            }
        }

        // Request dangerous permissions
        if (!permissionsToBeRequested.isEmpty()) {
            ActivityCompat.requestPermissions((Activity)context, permissionsToBeRequested.toArray(
                    new String[permissionsToBeRequested.size()]), UNUSED_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (requestCode != UNUSED_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, results,ACCESS_FINE_LOCATION)) {
            // TODO:
        }
        else if (PermissionUtils.isPermissionGranted(permissions, results,RECORD_AUDIO)) {
            // TODO:
        }
        else if (PermissionUtils.isPermissionGranted(permissions, results,WRITE_EXTERNAL_STORAGE)) {
            // TODO:
        }
        else {
            // TODO:
        }
    }
}
