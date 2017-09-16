package com.kpchuck.qstiles;

/**
 * Created by karol on 16/09/17.
 */

public class ShellHelpOut {

    public void runShellCommands(String[] commands){
        try {
            ProcessBuilder builder = new ProcessBuilder("su");

            Process p = builder.start();

            //get stdin of shell
            java.io.BufferedWriter p_stdin =
                    new java.io.BufferedWriter(new java.io.OutputStreamWriter(p.getOutputStream()));

            for (String c: commands) {
                p_stdin.write(c);
                p_stdin.newLine();
                p_stdin.flush();
            }

            p_stdin.write("exit");
            p_stdin.newLine();
            p_stdin.flush();
        } catch (Exception e) {
            android.util.Log.e("Error with Quick Settings", e.getMessage());
        }
    }
}
