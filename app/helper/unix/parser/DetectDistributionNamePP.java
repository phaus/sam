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
public class DetectDistributionNamePP implements ProcessParser {

    /**
     * thx to http://www.novell.com/coolsolutions/feature/11251.html.
     */
    private final static String[] DIST_FILES = {
        "debian_release", "debian_version",
        "fedora-release",
        "gentoo-release",
        "lsb-release",
        "mandrake-release",
        "redhat-release", "redhat_version",
        "release",
        "slackware-release", "slackware-version",
        "sun-release",
        "SUSE-release",
        "UnitedLinux-release",
        "yellowdog-release"
    };
    private final static String[] DIST_NAMES = {
        "Debian", "Debian",
        "Fedora",
        "Gentoo",
        "Ubuntu",
        "Mandrake",
        "Red Hat", "Red Hat",
        "Solaris/Sparc",
        "Slackware", "Slackware",
        "Sun JDS",
        "Novell SUSE",
        "UnitedLinux",
        "Yellow dog"
    };
    private String name = "N/A";

    public void parse(BufferedReader bufferedreader) {
        try {
            String line;
            while ((line = bufferedreader.readLine()) != null) {
                for (int i = 0; i < DIST_FILES.length; i++) {
                    if (line.trim().startsWith(DIST_FILES[i])) {
                        this.name = DIST_NAMES[i];
                    }
                }
            }
        } catch (IOException ex) {
            Logger.error(ex.getLocalizedMessage());
        }
    }

    public String getName() {
        return this.name;
    }
}
