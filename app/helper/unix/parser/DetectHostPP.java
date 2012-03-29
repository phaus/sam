/**
 * DetectHostPP
 * 29.03.2012
 * @author Philipp Haussleiter
 *
 */
package helper.unix.parser;

import helper.ProcessParser;
import java.io.BufferedReader;
import java.io.IOException;
import models.Host;
import play.Logger;

/**
 *
 * @author philipp
 */
public class DetectHostPP implements ProcessParser {

    private Host host;

    public DetectHostPP(Host host) {
        this.host = host;
    }

    public Host getHost() {
        return this.host;
    }

    public void parse(BufferedReader bufferedreader) {
        try {
            int i = 0;
            String line;
            while ((line = bufferedreader.readLine()) != null && !line.startsWith("Warning:")) {
                updateHost(i, line.trim());
                i++;
            }
            this.host.ip = Host.getIp(this.host.ip);
        } catch (IOException ex) {
            Logger.error(ex.getLocalizedMessage());
        }
    }

    private void updateHost(int i, String line) {
        switch (i) {
            case 0:
                this.host.hostname = line;
                break;
            case 1:
                this.host.dnsdomainname = line;
                break;
        }
    }
}
