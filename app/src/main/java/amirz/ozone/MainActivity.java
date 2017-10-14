package amirz.ozone;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import eu.chainfire.libsuperuser.Shell;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getSharedPreferences("main", 0);
        if (pref.getBoolean("first", true)) {
            Shell.SU.run("settings put secure sysui_qqs_count 6");

            SharedPreferences.Editor edit = pref.edit();
            edit.putBoolean("first", false);
            edit.apply();

            startActivity(getPackageManager().getLaunchIntentForPackage("projekt.substratum"));
        } else {
            Shell.SU.run("echo '" + 160 + "' > /sys/module/cpu_boost/parameters/input_boost_ms");
            Shell.SU.run("echo 1 > /sys/devices/virtual/graphics/fb0/DCI_P3");
            Log.w("Ozone", "Executed commands");
        }

        super.onCreate(savedInstanceState);
        finish();
        overridePendingTransition(0, 0);
    }
}
