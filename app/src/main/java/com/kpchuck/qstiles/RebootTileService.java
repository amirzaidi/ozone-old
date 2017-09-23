package com.kpchuck.qstiles;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by Karol Przestrzelski on 05/09/2017.
 */

public class RebootTileService extends android.service.quicksettings.TileService {

    public void onClick(){

        Shell.SU.run("reboot");
    }
}
