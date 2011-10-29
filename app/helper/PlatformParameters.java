/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import models.Platform;

/**
 *
 * @author philipp
 */
public class PlatformParameters {
    public String kernelName;
    public String kernelVersion;
    public String kernelRelease;
    public String machine;
    public String processor;
    public String hardwarePlatform;
    public String operationSystem;

    public PlatformParameters(String kernelVersion, String kernelRelease, String machine, String operationSystem) {
        this.kernelVersion = kernelVersion;
        this.kernelRelease = kernelRelease;
        this.machine = machine;
        this.operationSystem = operationSystem;
    }
    
    public static PlatformParameters createFromPlatform(Platform platform){
        return new PlatformParameters(platform.kernelVersion, platform.kernelRelease, platform.machine, platform.operationSystem);
    }
    @Override
    public String toString(){
        return operationSystem+"_"+machine+" "+kernelVersion+" "+kernelRelease;
    }
}
