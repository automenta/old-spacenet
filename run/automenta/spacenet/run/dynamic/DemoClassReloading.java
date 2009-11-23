package automenta.spacenet.run.dynamic;


import automenta.spacenet.space.object.ClassBox;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.Starts;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.object.widget.button.Button;
import automenta.spacenet.space.object.widget.button.ButtonAction;
import automenta.spacenet.space.object.widget.button.TextButton;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * dynamically reload and reinstantiate a class, into a space, from a URL (when a button is clicked)
 */
public class DemoClassReloading extends Space implements Starts {
    private TextButton reloadButton;
    private ClassBox classBox;
    private Class<? extends Space> reloadedClass;

    public DemoClassReloading(Class<? extends Space> c) {
        super();
        this.reloadedClass = c;
    }
    
    @Override public void start() {
        try {

            classBox = add(new ClassBox(reloadedClass));

            classBox.move(1, 0, 0);
            reloadButton = add(new TextButton("Reload"));
            reloadButton.addButtonAction(new ButtonAction() {

                @Override
                public void onButtonPressed(Button b) {
                    classBox.reload();
                }
            });
            reloadButton.move(-1, 0, 0);
        } catch (MalformedURLException ex) {
            Logger.getLogger(DemoClassReloading.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override public void stop() {   }

    public static void main(String[] args) {
        new DefaultJmeWindow(new DemoClassReloading(EditMe.class));
    }
}
