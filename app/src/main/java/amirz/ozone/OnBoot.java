package amirz.ozone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OnBoot extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.w("Ozone", "Boot intent received");
        context.startActivity(context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }
}
