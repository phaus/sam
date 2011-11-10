/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper.unix.parser;

import helper.ProcessParser;
import java.io.BufferedReader;
import java.io.IOException;
import play.Logger;

/**
 *
 * @author philipp
 */
public class DetectDistributionVersionPP implements ProcessParser {

    private String name;
    private String version;

    public DetectDistributionVersionPP(String name) {
        this.name = name;
    }

    public void parse(BufferedReader bufferedreader) {
        try {
            String line;
            while ((line = bufferedreader.readLine()) != null && !line.startsWith("Warning:")) {
                if ("Debian".equals(name)) {
                    this.version = line.trim();
                    break;
                }
                if ("Ubuntu".equals(name) && line.startsWith("DISTRIB_RELEASE=")) {
                    this.version = line.replace("DISTRIB_RELEASE=", "").trim();
                    break;
                }
                if (name.toLowerCase().endsWith("suse") && line.startsWith("VERSION =")) {
                    this.version = line.replace("VERSION = ", "").trim();
                    break;
                }
                this.version = line.trim();
            }
        } catch (IOException ex) {
            Logger.error(ex.getLocalizedMessage());
        }
    }

    public String getCommand() {
        String command = "uname -v";
        if ("Debian".equals(name)) {
            command = "cat /etc/debian_version";
        }
        if ("Ubuntu".equals(name)) {
            command = "cat /etc/lsb-release";
        }
        if ("Darwin".equals(name)) {
            command = "uname -r";
        }
        if (name.toLowerCase().endsWith("suse")) {
            command = "cat /etc/SuSE-release";
        }
        return command;
    }

    public String getVersion() {
        return this.version;
    }
}
