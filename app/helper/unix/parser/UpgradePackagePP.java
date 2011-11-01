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
        super.startToken = "Abhängigkeitsbaum wird aufgebaut...";
        return "apt-get update && apt-get upgrade -s";
    }
}
