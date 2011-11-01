/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper.unix.parser;

import helper.ProcessParser;
import helper.parser.tools.ArcLinuxPackageParser;
import helper.parser.tools.DebianPackageParser;
import helper.parser.tools.PackageParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import models.AppPackage;
import models.Distribution;
import models.Host;
import play.Logger;

/**
 *
 * @author philipp
 */
public class ListPackagePP implements ProcessParser {

    private List<AppPackage> packages = new LinkedList<AppPackage>();
    private Distribution distribution;
    private Host host;
    private boolean on = false;
    public String startToken = "+++-";

    public void parse(BufferedReader bufferedreader) {
        try {
            PackageParser parser = getPackageParser();
            String line;
            AppPackage p;
            while ((line = bufferedreader.readLine()) != null) {
                if (on) {
                    p = parser.parsePartsToPackage(line);
                    packages.add(p);
                    p.update();
                }
                if (line.startsWith(startToken)) {
                    on = true;
                }
            }
            host.updatePackages(packages);
            this.distribution = parser.getDistribution();
        } catch (IOException ex) {
            Logger.error(ex.getLocalizedMessage());
        }
    }

    private PackageParser getPackageParser() {
        if ("Arch Linux".equals(this.distribution.name)) {
            on = true;
            return new ArcLinuxPackageParser(distribution);
        }
        startToken = "+++-";
        return new DebianPackageParser(distribution);
    }

    public String getCommand() {
        if ("Arch Linux".equals(this.distribution.name)) {
            return "pacman -Q";
        }
        return "dpkg -l";
    }

    public void setDistribution(Distribution distribution) {
        Logger.info("distribution is: "+distribution);
        this.distribution = distribution;
    }
    
    public void setHost(Host host){
        Logger.info("Host is: "+host);
        this.host = host;
    }

    public List<AppPackage> getPackages() {
        return this.packages;
    }
}
