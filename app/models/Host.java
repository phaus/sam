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
import play.cache.Cache;
import play.db.jpa.Model;

/**
 *
 * @author philipp
 */
@Entity
public class Host extends Model implements Comparable<Host> {

    public String hostname = "";
    @ManyToOne
    public Platform platform;
    public String dnsdomainname = "";
    public String user;
    public String ip;
    @OneToMany(mappedBy="host")
    public List<HostPackage> packages;

    public Host() {
        packages = new LinkedList<HostPackage>();
    }

    public static Host findOrCreateByUserAndIp(String user, String ip) {
        Host host = Host.find(" user = ? and ip = ?", user, ip).first();
        if (host == null) {
            host = new Host();
            host.user = user;
            host.ip = ip;
            host.save();
        }
        return host;
    }

    public String getCacheKey() {
        return "HOST_" + user + "_" + getIp(ip);
    }

    public void setPlatform(Platform platform) {
        Logger.debug("setting Platform to: " + platform);
        this.platform = platform;
        this.save();
    }

    public void updatePackages(List<AppPackage>packages){
        if(this.id == null){
            this.save();
        }
        HostPackage.cleanForHost(this);
        for(AppPackage ap : packages){
            this.packages.add(HostPackage.findOrCreate(this, ap));
        }
        this.merge();
    }
 
    
    public List<AppPackage>getPackages(){
        return HostPackage.getAppPackagesForHost(this);
    }
    
    public Distribution getDistribution() {
        if (this.platform != null) {
            return this.platform.distribution;
        }
        return null;
    }

    public Host update() {
        String key = getCacheKey();
        Cache.set(key, this, "1d");
        if (this.id == null) {
            return this.save();
        }
        return this.merge();
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
        String out = hostname + "." + dnsdomainname;
        return out;
    }

    public int compareTo(Host t) {
        String thisName = this.hostname + "." + this.dnsdomainname;
        String otherName = t.hostname + "." + this.dnsdomainname;
        return thisName.compareTo(otherName);
    }
}
