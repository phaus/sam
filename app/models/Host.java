/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import play.Logger;
import play.db.jpa.Model;

/**
 *
 * @author philipp
 */
@Entity
public class Host extends Model implements Comparable<Host> {

    @ManyToOne
    public Platform platform;
    @OneToMany(mappedBy = "host")
    public List<HostPackage> packages;
    public String hostname = "";
    public String dnsdomainname = "";
    public String user;
    public String ip;

    public Host() {
        packages = new LinkedList<HostPackage>();
    }

    public static Host findOrCreateByUserAndIp(String user, String ip) {
        ip = Host.getIp(ip);
        Host host = Host.find(" user = ? and ip = ?", user, ip).first();
        if (host == null) {
            host = new Host();
            host.user = user;
            host.ip = ip;
        } else {
            Logger.info("found Host: " + host);
        }
        Logger.info("Host: " + host);
        return host;
    }

    public String getCacheKey() {
        return "HOST_" + user + "_" + getIp(ip);
    }

    public List<AppPackage> getPackages() {
        return HostPackage.getAppPackagesForHost(this);
    }

    public Distribution getDistribution() {
        if (this.platform != null) {
            return this.platform.distribution;
        }
        return null;
    }

    public static String getIp(String ip) {
        try {
            Integer.parseInt(ip.replace(".", ""));
        } catch (NumberFormatException nfe) {
            try {
                InetAddress ipaddress = InetAddress.getByName(ip);
                return ipaddress.getHostAddress();
            } catch (UnknownHostException ex) {
                java.util.logging.Logger.getLogger(Host.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ip;
    }

    @Override
    public String toString() {
        return hostname + "." + dnsdomainname;
    }

    public int compareTo(Host t) {
        String thisName = this.hostname + "." + this.dnsdomainname;
        String otherName = t.hostname + "." + this.dnsdomainname;
        return thisName.compareTo(otherName);
    }
}
