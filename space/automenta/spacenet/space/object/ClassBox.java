/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automenta.spacenet.space.object;

import automenta.spacenet.Starts;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.Color;




import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Logger;

/**
 * box holding an instantiated Class<? extends Space> object 
 */
public class ClassBox extends Box implements Starts {

    //private Class<? extends Space> cl;
    private Space obj;
    private DynClassLoader classLoader;
    private final String className;
    private Class cl;
    private final URL classURL;
    //private final boolean autoReload;

    public static String getSimpleName(String className) {
        String[] sect = className.split("\\.");
        return sect[sect.length-1];
    }

    public ClassBox(Class targetClass) throws MalformedURLException {
        this(targetClass, targetClass.getName());
    }

    public ClassBox(Class baseClass, String className) throws MalformedURLException {
        this(baseClass.getResource(".").toExternalForm(), className);
    }

    public ClassBox(String packagePath, String className) throws MalformedURLException {
        this(className, new URL(packagePath + "/" + ClassBox.getSimpleName(className) + ".class"));
    }

    public ClassBox(String className, URL classURL) {
        super();

        this.className = className;
        this.classURL = classURL;
        //this.autoReload = autoReload;
    }

    @Override public void start() {
//        if (autoReload) {
////            ReloadingClassLoader classloader = new ReloadingClassLoader(this.getClass().getClassLoader());
////            ReloadingListener listener = new ReloadingListener();
////
////            listener.addReloadNotificationListener(classloader);
//
//            FilesystemAlterationListener listener = new FilesystemAlterationListener() {
//
//                @Override
//                public void onStart(FilesystemAlterationObserver o) {
//                    System.out.println("file change observer START: " + o);
//                }
//
//                @Override
//                public void onFileCreate(File f) {
//                    System.out.println("file created: " + f);
//                }
//
//                @Override
//                public void onFileChange(File f) {
//                    System.out.println("file changed: " + f);
//                }
//
//                @Override
//                public void onFileDelete(File arg0) {
//                }
//
//                @Override
//                public void onDirectoryCreate(File arg0) {
//                }
//
//                @Override
//                public void onDirectoryChange(File arg0) {
//                }
//
//                @Override
//                public void onDirectoryDelete(File arg0) {
//                }
//
//                @Override
//                public void onStop(FilesystemAlterationObserver arg0) {
//                }
//            };
//
//            File root;
//            try {
//                root = new File(getClass().getResource(".").toURI());
//
//                System.out.println("root to listen at: " + root);
//
//                FilesystemAlterationMonitor fam = new FilesystemAlterationMonitor();
//                fam.addListener(root, listener);
//                fam.start();
//
//            } catch (URISyntaxException ex) {
//                java.util.logging.Logger.getLogger(ClassBox.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }

        addBorder();

        reload();
    }

    protected void addBorder() {
        double borderWidth = 0.02;
        Color borderColor = Color.Gray.alpha(0.5);
        
        add(new Box(borderColor).scale(borderWidth).move(-0.5, -0.5, -0.5));
        add(new Box(borderColor).scale(borderWidth).move(-0.5, 0.5, -0.5));
        add(new Box(borderColor).scale(borderWidth).move(0.5, 0.5, -0.5));
        add(new Box(borderColor).scale(borderWidth).move(0.5, -0.5, -0.5));
        add(new Box(borderColor).scale(borderWidth).move(-0.5, -0.5, 0.5));
        add(new Box(borderColor).scale(borderWidth).move(-0.5, 0.5, 0.5));
        add(new Box(borderColor).scale(borderWidth).move(0.5, 0.5, 0.5));
        add(new Box(borderColor).scale(borderWidth).move(0.5, -0.5, 0.5));
    }

    @Override public void stop() {
    }

    public void clear() {
        if (obj!=null) {
            remove(obj);
            obj = null;
        }
    }

    public synchronized void reload() {
        clear();

        System.gc();
        
        try {
            this.classLoader = new DynClassLoader(getClass().getClassLoader(), className, classURL);
            
            cl = classLoader.loadClass(className);

            obj = (Space)(cl.newInstance());

            add(obj);
        } catch (Exception ex) {
            Logger.getLogger(ClassBox.class.getName()).warn(ex);
        }
    }
    
}
