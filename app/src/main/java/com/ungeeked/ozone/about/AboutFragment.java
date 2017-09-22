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

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.kpchuck.qstiles.ShellHelpOut;

import substratum.theme.template.R;

public class AboutFragment extends Fragment {

    private static final String TAG = AboutFragment.class.getSimpleName();
    Button qsCountButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.about, container, false);

        WebView aboutWebView = (WebView) v.findViewById(R.id.about_web_view);
        View openSubstratumButton = v.findViewById(R.id.open_substratum_button);
        qsCountButton = v.findViewById(R.id.quickSettingsCount);


        aboutWebView.setBackgroundColor(Color.TRANSPARENT);
        aboutWebView.loadData(styleHtml(getActivity(), R.string.about_html), "text/html", "UTF-8");

        openSubstratumButton.setVisibility(
                Util.isPackageInstalled(getActivity(), Util.SUBSTRATUM_PACKAGE_NAME)
                        ? View.VISIBLE : View.GONE);
        openSubstratumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   Intent intent = new Intent();
                intent = intent.setClassName(Util.SUBSTRATUM_PACKAGE_NAME,
                        "projekt.substratum.activities.launch.ThemeLaunchActivity");
                intent.putExtra("package_name", Util.THEME_PACKAGE_NAME);
                intent.setAction("projekt.substratum.THEME");
                intent.setPackage(Util.THEME_PACKAGE_NAME);
                intent.putExtra("calling_package_name", Util.THEME_PACKAGE_NAME);
                intent.putExtra("oms_check", false);
                intent.putExtra("theme_mode", (String) null);
                intent.putExtra("notification", false);
                intent.putExtra("hash_passthrough", true);
                intent.putExtra("certified", false);
                startActivity(intent);
            }
        });


        qsCountButton.setVisibility(View.VISIBLE);
        qsCountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), qsCountButton);
                popupMenu.getMenuInflater().inflate(R.menu.qs_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        String number = menuItem.getTitle().toString();
                        String base = "settings put secure sysui_qqs_count ";
                        new ShellHelpOut().runShellCommands(new String[]{base + number});

                        return true;
                    }
                });
                popupMenu.show();
            }});




        // Remember which version of this screen the user has seen;
        Util.markAboutSeen(getActivity());

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.about_hide_launcher, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_hide_launcher:
                promptHideLauncher();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static String styleHtml(Context context, @StringRes int resourceId) {
        TypedArray ta = context.obtainStyledAttributes(new int[] {
                android.R.attr.textColorPrimary,
                android.R.attr.colorAccent,
                R.attr.colorWarning}
        );
        String textColorPrimary = String.format("#%06X", (0xFFFFFF & ta.getColor(0, Color.GRAY)));
        String accentColor = String.format("#%06X", (0xFFFFFF & ta.getColor(1, Color.GRAY)));
        String warning_color = String.format("#%06X", (0xFFFFFF & ta.getColor(2, Color.GRAY)));
        String substratumVersion = getVersionName(context, Util.SUBSTRATUM_PACKAGE_NAME);
        String themeVersion = getVersionName(context, Util.THEME_PACKAGE_NAME);
        String androidVersion = getVersionName(context, null);
        ta.recycle();
        String html = context.getString(resourceId);
        html = html.replaceAll("\\?android:attr/textColorPrimary", textColorPrimary);
        html = html.replaceAll("\\?android:attr/colorAccent", accentColor);
        html = html.replaceAll("\\?attr/colorWarning", warning_color);
        html = html.replaceAll("\\?attr/substratumVersion", substratumVersion);
        html = html.replaceAll("\\?attr/themeVersion", themeVersion);
        html = html.replaceAll("\\?attr/androidVersion", androidVersion);
        return html;
    }

    private static String getVersionName(Context context, String packageName) {
        if (packageName == null) {
            return Build.VERSION.RELEASE;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            return context.getString(R.string.versoin_name_and_code, packageInfo.versionName,
                    String.valueOf(packageInfo.versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return context.getString(R.string.unknown_version);
        }
    }

    private void promptHideLauncher() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.hide_launcher_title)
                .setMessage(R.string.hide_launcher_message)
                .setPositiveButton(R.string.hide_launcher_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Util.disableLauncher(getActivity());
                    }
                })
                .setNegativeButton(R.string.hide_launcher_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Only dismiss
                    }
                })
                .show();
    }

}
