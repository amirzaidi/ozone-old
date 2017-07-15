/*
 * Copyright (C) 2017 SpiritCroc
 * Email: spiritcroc@gmail.com
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * To view a copy of this license,
 * visit https://creativecommons.org/licenses/by-nc-sa/4.0/.
 */

package com.ungeeked.ozone.about;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import substratum.theme.template.R;

public class DeppFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.depp, container, false);

        WebView deppWebView = (WebView) v.findViewById(R.id.depp_web_view);
        View uninstallButton = v.findViewById(R.id.uninstall_button);

        deppWebView.setBackgroundColor(Color.TRANSPARENT);
        deppWebView.loadData(AboutFragment.styleHtml(getActivity(), R.string.depp_html),
                "text/html", "UTF-8");

        uninstallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String packageString = "package:" + AboutActivity.THEME_PACKAGE_NAME;
                startActivity(new Intent(Intent.ACTION_UNINSTALL_PACKAGE,
                        Uri.parse(packageString)));
            }
        });

        return v;
    }
}
