package automenta.spacenet.space.jme.video.swing;

import javax.swing.JFrame;

import automenta.spacenet.space.jme.video.Jme;
import com.acarter.scenemonitor.dialog.MonitorDialog;


public class SceneMonitor {

	private MonitorDialog dialog;

	boolean running;
	
	public SceneMonitor(final Jme jme) {
		
		running = true;
		
		Jme.doLater(new Runnable() {
			@Override public void run() {

				new Thread(new Runnable() {

					private float sceneMonitorUpdatePeriod = 0.5f;

					@Override public void run() {
						dialog = new MonitorDialog(new JFrame());
						dialog.setVisible(true);

						dialog.registerNode(jme.getRootNode(), "Root");				
						//dialog.registerNode(jme.getFaceNode(), "Face");				

						
						dialog.doUpdate(sceneMonitorUpdatePeriod);
//						try {
//							Thread.sleep( (int)(1000 * sceneMonitorUpdatePeriod) );
//						} catch (InterruptedException e) {		}
					}
				}).start();

			}

		});

	}

	public void stop() {
		
		dialog.setVisible(false);
		dialog.dispose();
		
	}
}
