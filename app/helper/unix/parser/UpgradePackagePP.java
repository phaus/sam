/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper.unix.parser;

import helper.parser.SimpeOutputPP;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import models.Distribution;
import play.Logger;

/**
 *
 * @author philipp
 */
public class UpgradePackagePP extends SimpeOutputPP {

    private Distribution distribution;

    public UpgradePackagePP(Distribution distribution) {
        super();
        this.distribution = distribution;
    }

    public String getCommand() {
        if ("Arch Linux".equals(this.distribution.name)) {
            return "pacman --sync --refresh && pacman -Qu";
        }
        if (this.distribution.name.toLowerCase().endsWith("suse")) {
            super.startToken = "Die folgenden Pakete werden aktualisiert:";
            super.stopToken = "Die folgenden Pakete werden die Architektur ändern:";
            return "zypper refresh && zypper --non-interactive update -D --auto-agree-with-licenses";
        }
        super.startToken = "Abhängigkeitsbaum wird aufgebaut...";
        return "apt-get update && apt-get upgrade -s";
    }
    
    // TODO this must be relocated in a separate class.
    @Override
    public List<String>getOutput(){
        if (this.distribution.name.toLowerCase().endsWith("suse")) {
            return cleanSuseOutput(super.getOutput());
        }
        return super.getOutput();
    }

    private List<String> cleanSuseOutput(List<String> input) {
        List<String> out = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        for(String part : input){
            sb.append(part);
        }
        out.addAll(Arrays.asList(sb.toString().split(" ")));
        return out;
    }
}
