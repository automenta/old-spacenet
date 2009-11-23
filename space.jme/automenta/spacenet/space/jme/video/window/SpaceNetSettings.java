/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automenta.spacenet.space.jme.video.window;

import com.jme.system.PropertiesGameSettings;
import java.io.IOException;

/**
 *
 * @author seh
 */
class SpaceNetSettings extends PropertiesGameSettings {

    public SpaceNetSettings() {
        super("properties.cfg");
        load();
    }

    @Override
    public boolean load() {
        return super.load();
    }

    @Override
    public void save() throws IOException {
        super.save();
    }



    public int getSubSamples() {
        int ss = getInt("SUBSAMPLES", 0);
        return ss;
    }

    public void setSubSamples(int ss) {
        setInt("SUBSAMPLES", ss);
    }


}
