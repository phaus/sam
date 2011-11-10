/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper.unix.parser;

import helper.parser.SimpeOutputPP;
import models.Distribution;

/**
 *
 * @author philipp
 */
public class SearchPackagePP extends SimpeOutputPP {
    private Distribution distribution;
    
    public SearchPackagePP(Distribution distribution){
        super();
        this.distribution = distribution;
    }
    
    public String getCommand(String query){
        if("Arch Linux".equals(this.distribution.name)){
            return "pacman -Ss "+query;
        }
        if(this.distribution.name.toLowerCase().endsWith("suse")){
            super.startToken = "--+--";
            return " zypper search "+query;
        }
        super.startToken = "Paketlisten werden gelesen...";
        return "apt-get update && apt-cache search "+query;
    }
}
