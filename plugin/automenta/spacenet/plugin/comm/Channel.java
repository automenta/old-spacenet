/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automenta.spacenet.plugin.comm;

/**
 *
 * @author seh
 */
public class Channel {
    private final String name;

    public Channel(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }



}
