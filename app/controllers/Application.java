package controllers;

import helper.sam.SamHelper;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import models.Host;
import play.mvc.Controller;

public class Application extends Controller {

    public static TreeSet<Host> getHosts(){
        TreeSet<Host> hostSet = new TreeSet<Host>();
        List<Host>hosts = Host.findAll();
        for(Host host : hosts){
            hostSet.add(host);
        }
        return hostSet;
    }
    
    public static void index() {
        Set<Host> hosts = Application.getHosts();
        render(hosts);
    }
    
    public static void setup(){
        String sshKey = SamHelper.getInstance().getPublicSSHKey();
        Set<Host> hosts = Application.getHosts();
        render(hosts,sshKey);
    }
}