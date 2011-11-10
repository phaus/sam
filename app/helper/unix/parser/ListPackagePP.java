/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper.unix.parser;

import helper.ProcessParser;
import helper.PackageParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import models.AppPackage;
import models.Distribution;
import models.Host;
import models.HostPackage;
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
                    p.save();
                }
                if (line.startsWith(startToken)) {
                    on = true;
                }
            }
            Logger.info("updating HostPackages for: " + host);
            updateHostPackages();
        } catch (IOException ex) {
            Logger.error(ex.getLocalizedMessage());
        }
    }

    private PackageParser getPackageParser() {
        if ("Arch Linux".equals(this.distribution.name)) {
            on = true;
            return new ArcLinuxPackageParser(distribution);
        }
        
        if(this.distribution.name.toLowerCase().endsWith("suse")){    
        on = true;
        return new SusePackageParser(distribution);
        }
        
        startToken = "+++-";
        return new DebianPackageParser(distribution);
    }

    private void updateHostPackages() {
        HostPackage.cleanForHost(host);
        HostPackage hp;
        for (AppPackage ap : this.packages) {
            hp = new HostPackage(host, ap);
            hp.save();
        }
    }

    public String getCommand() {
        if ("Arch Linux".equals(this.distribution.name)) {
            return "pacman -Q";
        }
        if(this.distribution.name.toLowerCase().endsWith("suse")){
            return "rpm -qa --queryformat=\"%{NAME} %{version} %{summary} \\n\" | sort -n";
        }
        return "dpkg -l";
    }

    public void setDistribution(Distribution distribution) {
        Logger.info("distribution is: " + distribution);
        this.distribution = distribution;
    }

    public void setHost(Host host) {
        Logger.info("Host is: " + host);
        this.host = host;
    }

    public List<AppPackage> getPackages() {
        return this.packages;
    }
}
