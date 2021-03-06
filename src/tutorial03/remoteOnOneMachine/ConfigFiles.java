/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial03.remoteOnOneMachine;

/**
 * This class is a helper class,
 * which generates/delievers confirguration strings/files.
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class ConfigFiles {
    
    /**
     * Creates a basic configuration string which can be used from clients and
     * servers, but need to be complemented with specific settings.
     * 
     * @return a not complet basic configuration
     */
    private static String getCommonRemote(){
        String result =
                "akka {\n"
                + "  actor {\n"
                + "    provider = \"akka.remote.RemoteActorRefProvider\"\n"
                + "  }\n"
                + "  remote {\n"
                + "    netty {\n"
                + "      hostname = \"127.0.0.1\"\n"
                + "    }\n"
                + "  }\n"
                + "}\n";
        return result;
    }

    public static String getServerConfig() {
        String result = ConfigFiles.getCommonRemote() + "\n"
                + "  akka {\n"
                + "    remote.netty.port = 2552 \n"
                + "  }\n";
        return result;
    }
    
    public static String getClientConfig(){
        String result = ConfigFiles.getCommonRemote() + "\n"
                + "  akka {\n"
                + "    remote.netty.port = 2553 \n"
                + "  }\n";
        return result;
    }
    
}
