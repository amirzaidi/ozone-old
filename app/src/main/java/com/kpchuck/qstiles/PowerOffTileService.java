package com.kpchuck.qstiles;

/**
 * Created by Karol Przestrzelski on 05/09/2017.
 */

public class PowerOffTileService extends android.service.quicksettings.TileService {

    public void onClick() {
        reboot();
    }


    private void reboot() {

        try {
            ProcessBuilder builder = new ProcessBuilder("su");

            Process p = builder.start();

            //get stdin of shell
            java.io.BufferedWriter p_stdin =
                    new java.io.BufferedWriter(new java.io.OutputStreamWriter(p.getOutputStream()));

            p_stdin.write("reboot -p");
            p_stdin.newLine();
            p_stdin.flush();

            p_stdin.write("exit");
            p_stdin.newLine();
            p_stdin.flush();
        } catch (Exception e) {
            android.util.Log.e("klock", "Error Switching Off", e);
        }

    }
}
