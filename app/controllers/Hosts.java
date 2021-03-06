/**
 * Hosts
 * 29.03.2012
 * @author Philipp Haussleiter
 *
 */
package controllers;

import helper.PlatformHelper;
import helper.unix.UnixPlatformHelper;
import java.util.List;
import java.util.Set;
import models.AppPackage;
import models.Distribution;
import models.Host;
import models.HostPackage;
import models.Platform;
import play.Logger;
import play.cache.Cache;
import play.mvc.Controller;

/**
 *
 * @author philipp
 */
public class Hosts extends Controller {

    public static void scan(String user, String ip) {
        if (ip == null || ip.isEmpty()) {
            Application.index();
        }
        PlatformHelper helper = UnixPlatformHelper.getInstance();
        helper.setHost(Host.findOrCreateByUserAndIp(user, ip));
        Host host = helper.detectHost();
        if (helper.getStatus() < 128) {
            host.save();
        } else {
            Logger.info("there are some errors!");
            Application.index();
        }
        helper.dectectDistribution();
        helper.detectPlatform();
        helper.getDistribution();
        helper.listPackages();
        show(host.id);
    }

    public static void show(long hostId) {
        Host host = Host.findById(hostId);
        Logger.info("Host: " + host);
        Set<Host> hosts = Application.getHosts();
        Platform platform = host.platform;
        Distribution distribution = host.getDistribution();
        List<AppPackage> packages = host.getPackages();
        render(hosts, host, distribution, platform, packages);
    }

    public static void delete(long hostId) {
        Host host = Host.findById(hostId);
        HostPackage.cleanForHost(host);
        Cache.safeDelete(host.getCacheKey());
        host.delete();
        Application.index();
    }
}
