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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yahoo.mobile.client.android.yodel.R;
import com.yahoo.mobile.client.android.yodel.utils.ImageLoader;

public class FullImageFragment extends Fragment {

    private final static String EXTRA_PHOTO_URL = "com.yahoo.mobile.sample.extra.photourl";

    public static FullImageFragment newInstance(String photoUrl) {
        FullImageFragment fragment = new FullImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_PHOTO_URL, photoUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_full_image, container, false);

        ImageView imageView = (ImageView)rootView.findViewById(R.id.full_image);

        String photoUrl = getArguments().getString(EXTRA_PHOTO_URL);
        ImageLoader.getInstance().displayImage(photoUrl, imageView);

        return rootView;
    }
}
