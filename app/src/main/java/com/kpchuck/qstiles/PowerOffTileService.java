package com.kpchuck.qstiles;

import android.content.Intent;
import android.widget.Toast;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by Karol Przestrzelski on 05/09/2017.
 */

public class PowerOffTileService extends android.service.quicksettings.TileService {

    public void onClick() {

        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        getApplicationContext().sendBroadcast(it);
        if (Shell.SU.available()){
            longToast("Powering Off, Please Wait...");
            Shell.SU.run("reboot -p");

        }else{
            longToast("Ozone doesn't have root permission. Open SuperSU/magisk and try again");
        }

    }

    public void longToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
