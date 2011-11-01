/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper.sam;

import helper.SystemHelper;
import helper.parser.SimpeOutputPP;
import java.util.List;
import models.Host;

/**
 *
 * @author philipp
 */
public class SamHelper extends SystemHelper {   
    
    private static SamHelper instance = new SamHelper();
    
    private SamHelper(){
        super();
        this.host = Host.findOrCreateByUserAndIp("root", "127.0.0.1");
        this.sshPrefix = " ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no " + this.host.user + "@" + this.host.ip;
    }
    
    public static SamHelper getInstance(){
        return instance;
    }
    
    public String getPublicSSHKey(){
        SimpeOutputPP so = new SimpeOutputPP();
        String command = "cat ~/.ssh/id_rsa.pub";
        runCommand(command, so);
        if(!so.getOutput().isEmpty()){
            return so.getOutput().get(0);
        }
        return "";
    }

}
