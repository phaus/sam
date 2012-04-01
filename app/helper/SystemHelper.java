/**
 * SystemHelper
 * 29.03.2012
 * @author Philipp Haussleiter
 *
 */
package helper;

import helper.parser.BooleanPP;
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
    protected String sshCmdPrefix = "";
    private int status = 255;

    public SystemHelper() {
        r = Runtime.getRuntime();
    }

    protected void runCommand(String command, ProcessParser pp) {
        if (this.host == null) {
            Logger.error("Host needs to be set!");
        }
        try {
            Logger.info("running: " + this.sshCmdPrefix + " " + command);
            Process p = r.exec(this.sshCmdPrefix + " " + command);
            if (!needPassword()) {
                runProcess(p, pp);
            } else {
                status = 255;
                Logger.error(host + " needs a password. Please add the public ssh-key to " + host);
            }
        } catch (IOException ex) {
            Logger.error(ex.getLocalizedMessage());
        }
    }

    /**
     * Checks if a ssh-connections needs a password.
     * thx to http://freeunixtips.com/2009/03/ssh-pw-prompt/
     */
    protected boolean needPassword() throws IOException {
        BooleanPP bp = new BooleanPP();
        Logger.info("check: " + this.sshCmdPrefix + " -qo PasswordAuthentication=no echo 0 || echo 1");
        Process p = r.exec(this.sshCmdPrefix + " -qo PasswordAuthentication=no echo 0 || echo 1");
        runProcess(p, bp);
        return bp.getResult();
    }

    protected void runProcess(Process p, ProcessParser pp) throws IOException {
        InputStream in = p.getInputStream();
        BufferedInputStream buf = new BufferedInputStream(in);
        InputStreamReader inread = new InputStreamReader(buf);
        BufferedReader bufferedreader = new BufferedReader(inread);
        pp.parse(bufferedreader);
        try {
            status = p.waitFor();
            Logger.info("exit value = " + status);
        } catch (InterruptedException e) {
            System.err.println(e);
        } finally {
            // Close the InputStream
            bufferedreader.close();
            inread.close();
            buf.close();
            in.close();
        }
    }

    public int getStatus() {
        return status;
    }
}
