/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import models.Distribution;
import models.Host;
import models.Platform;
import play.Logger;

/**
 *
 * @author philipp
 */
public class SystemHelper {

    protected Host host = null;
    protected Platform platform = null;
    protected Distribution distribution = null;
    protected Runtime r;
    protected String sshPrefix = "";

    public SystemHelper() {
        r = Runtime.getRuntime();
    }
    // TODO needs to add a timeout.

    protected void runCommand(String command, ProcessParser pp) {
        if (this.host == null) {
            Logger.error("Host needs to be set!");
        }
        try {
            Logger.info("running: " + this.sshPrefix + " " + command);
            Process p = r.exec(this.sshPrefix + " " + command);
            InputStream in = p.getInputStream();
            BufferedInputStream buf = new BufferedInputStream(in);
            InputStreamReader inread = new InputStreamReader(buf);
            BufferedReader bufferedreader = new BufferedReader(inread);
            pp.parse(bufferedreader);
            try {
                if (p.waitFor() != 0) {
                    Logger.info("exit value = " + p.exitValue());
                }
            } catch (InterruptedException e) {
                System.err.println(e);
            } finally {
                // Close the InputStream
                bufferedreader.close();
                inread.close();
                buf.close();
                in.close();
            }
        } catch (IOException ex) {
            Logger.error(ex.getLocalizedMessage());
        }
    }
}
