/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helper.unix.UnixPlatformHelper;
import java.util.List;
import java.util.Set;
import models.Host;
import play.Logger;
import play.mvc.Controller;

/**
 *
 * @author philipp
 */
public class Packages extends Controller {
    public static void showUpdates(long hostId){
        Host host = Host.findById(hostId);
        Logger.info("loading updates on "+host);
        UnixPlatformHelper helper = UnixPlatformHelper.getInstance();
        helper.setHost(host);
        Set<Host> hosts = Application.getHosts();
        List<String> updates = helper.updatedPackages();
        render(host, hosts, updates);
    }
    
    public static void showDistUpgrade(long hostId){
        Host host = Host.findById(hostId);
        Logger.info("loading upgrade on "+host);
        UnixPlatformHelper helper = UnixPlatformHelper.getInstance();
        helper.setHost(host);
        Set<Host> hosts = Application.getHosts();
        List<String> updates = helper.upgradeDistribution();
        render(host, hosts, updates);
    }
    
    public static void search(long hostId, String query){
        Host host = Host.findById(hostId);
        Logger.info("search for "+query+" on "+host);  
        UnixPlatformHelper helper = UnixPlatformHelper.getInstance();
        helper.setHost(host);
        Set<Host> hosts = Application.getHosts();
        List<String>results = helper.searchPackage(query);
        render(host, hosts, results);
    }
}
