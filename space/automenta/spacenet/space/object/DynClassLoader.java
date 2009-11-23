
package automenta.spacenet.space.object;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author seh
 */
public class DynClassLoader extends ClassLoader{
    private final URL classURL;
    private final String className;


    public DynClassLoader(ClassLoader parent, String className, URL classURL) {
        super(parent);
        this.className = className;
        this.classURL = classURL;
    }

    public Class loadClass(String name) throws ClassNotFoundException {
        if(!className.equals(name))
                return super.loadClass(name);

        try {
            URLConnection connection = classURL.openConnection();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();

            while(data != -1){
                buffer.write(data);
                data = input.read();
            }

            input.close();

            byte[] classData = buffer.toByteArray();

            
            return defineClass(className, classData, 0, classData.length);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}