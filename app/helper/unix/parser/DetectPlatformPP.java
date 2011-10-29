/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper.unix.parser;

import helper.PlatformParameters;
import helper.ProcessParser;
import java.io.BufferedReader;
import java.io.IOException;
import models.Distribution;
import models.Platform;
import play.Logger;

/**
 *
 * @author philipp
 */
public class DetectPlatformPP implements ProcessParser {

    private Platform platform;
    private Distribution distribution;
    public DetectPlatformPP(Distribution distribution) {
        this.distribution = distribution;
        this.platform = new Platform();
    }

    public void parse(BufferedReader bufferedreader) {
        try {
            int i = 0;
            String line;
            while ((line = bufferedreader.readLine()) != null && !line.startsWith("Warning:")) {
                updatePlatform(i, line.trim());
                i++;
            }
        } catch (IOException ex) {
            Logger.error(ex.getLocalizedMessage());
        }
    }

    /** 
     * 0 uname -v && 
     * 1 uname -m && 
     * 2 uname -o && 
     * 3 uname -r && 
    #3 SMP Mon Jan 24 19:43:31 CET 2011
    x86_64
    GNU/Linux
    2.6.37-vs2.3.0.37-rc2-christian
    vs10testldap
    h002.innoq.com
     * @param p
     * @param i
     * @param line
     * @return 
     */
    private void updatePlatform(int i, String line) {
        switch (i) {
            case 0:
                this.platform.kernelVersion = line;
                break;
            case 1:
                this.platform.machine = line;
                break;
            case 2:
                this.platform.operationSystem = line;
                break;
            case 3:
                this.platform.kernelRelease = line;
                break;
        }
    }
    public String getCommand(){
        if("Darwin".equals(this.distribution.name)){
        return "uname -v && "
                + "uname -m && "
                + "uname -s && "
                + "uname -r";
        }
        return "uname -v && "
                + "uname -m && "
                + "uname -o && "
                + "uname -r";
    }
    
    public Platform getPlatform() {
        this.platform = Platform.findOrCreateByParameter(PlatformParameters.createFromPlatform(this.platform));
        return this.platform;
    }
}
