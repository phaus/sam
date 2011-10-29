/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
    
    public static Host findOrCreateByIp(String user, String ip){
        String key = "HOST_"+user+"_"+getIp(ip);
        Host host = Cache.get(key, Host.class);
        if(host == null){
            host = Host.find(" user = ? and ip = ?", user, ip).first();
        }
        if(host == null){
            host = new Host();
            host.user = user;
            host.ip = ip;
            host.save();
            Cache.set(key, host, "1d");
        }
        return host;
    }
    
    public String getCacheKey(){
        return "HOST_"+user+"_"+getIp(ip);
    }
    public Distribution getDistribution(){
        return this.platform.distribution;
    }
    
    public void setPlatform(Platform platform){
        Logger.debug("setting Platform to: "+platform);
        this.platform = platform;
        this.save();
    }
    
    public static String getIp(String ip){
        try{
            Integer.parseInt(ip.replace(".", ""));
        }catch(NumberFormatException nfe){
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
    public String toString(){
        String out = user+"@"+hostname+"."+dnsdomainname;
        return out;
    }

    public int compareTo(Host t) {
        String thisName = this.hostname+"."+this.dnsdomainname;
        String otherName = t.hostname+"."+this.dnsdomainname;
        return thisName.compareTo(otherName);
    }
}
