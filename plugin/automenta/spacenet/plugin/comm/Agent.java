/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automenta.spacenet.plugin.comm;

import java.net.URL;

/**
 *
 * @author seh
 */
public class Agent {

    public final String id;
    public final URL imageURL;

    public Agent(String id, URL imageURL) {
        super();
        this.id = id;
        this.imageURL = imageURL;
    }

    @Override public boolean equals(Object obj) {
        if (obj instanceof Agent) {
            Agent other = (Agent)obj;
            return other.id.equals( id );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id;
    }





}
