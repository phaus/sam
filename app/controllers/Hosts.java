/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helper.PlatformHelper;
import helper.unix.UnixPlatformHelper;
import java.util.List;
import java.util.Set;
import models.AppPackage;
import models.Distribution;
import models.Host;
import models.Platform;
import play.cache.Cache;
import play.mvc.Controller;

/**
 *
 * @author philipp
 */
public class Hosts extends Controller {
    
    public static void scan(String ip){
        if(ip == null || ip.isEmpty()){
            Application.index();
        }
        PlatformHelper helper = UnixPlatformHelper.getInstance();
        helper.setHost(Host.findOrCreateByIp("root", ip));        
        Set<Host> hosts = Application.getHosts();
        Host host = helper.detectHost();
        Distribution distribution = helper.dectectDistribution();
        Platform platform = helper.detectPlatform();
        List<AppPackage> packages = helper.listPackages();
        render(hosts, host, distribution, platform, packages); 
    }
    
    public static void show(long hostId){
        Host host = Host.findById(hostId);
        Set<Host> hosts = Application.getHosts();
        Platform platform = host.platform;
        Distribution distribution = platform.distribution;
        
        List<AppPackage> packages = distribution.packages;
        render(hosts, host, distribution, platform, packages);         
    }
    
    public static void delete(long hostId){
        Host host = Host.findById(hostId);
        Cache.safeDelete(host.getCacheKey());
        host.delete();
        Application.index();
    }
}
