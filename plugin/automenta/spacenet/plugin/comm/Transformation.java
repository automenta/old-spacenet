/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automenta.spacenet.plugin.comm;

/**
 *
 * @author seh
 */
public class Transformation {
    private final String name;

    public Transformation(String t) {
        this.name = t;
    }

    @Override public String toString() {
        return name;
    }


}
