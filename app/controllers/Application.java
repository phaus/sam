package controllers;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import models.Host;
import play.mvc.Before;
import play.mvc.Controller;

public class Application extends Controller {

    public static TreeSet<Host> HOSTS;
    
    @Before
    public static void init(){
        Application.HOSTS = new TreeSet<Host>();
        List<Host>hosts = Host.findAll();
        for(Host host : hosts){
            Application.HOSTS.add(host);
        }
    }
    
    
    public static void index() {
        Set<Host> hosts = Application.HOSTS;
        render(hosts);
    }
}