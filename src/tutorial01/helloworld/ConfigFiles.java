/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial01.helloworld;

/**
 * This class is a helper class,
 * which generates/delievers confirguration strings/files.
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
public class ConfigFiles {

    public static String getSimpleLocalActorConfig() {
        String result =
                "  akka {\n"
                + "  actor {\n"
                + "    provider = \"akka.actor.LocalActorRefProvider\"\n"
                + "  }\n"
                + "}\n";

        return result;
    }
}
