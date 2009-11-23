package automenta.spacenet.space.geom3.extern;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.log4j.Logger;

import automenta.spacenet.Starts;
import automenta.spacenet.UURI;
import automenta.spacenet.space.geom3.Box;

import automenta.spacenet.space.jme.video.Jme;
import com.jme.bounding.OrientedBoundingBox;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.util.export.binary.BinaryImporter;
import com.jme.util.resource.ResourceLocatorTool;
import com.jme.util.resource.SimpleResourceLocator;
import com.jmex.model.converters.FormatConverter;
import com.jmex.model.converters.MaxToJme;
import com.jmex.model.converters.X3dToJme;

public class ThreeDSMaxSpace extends Box implements Starts {
	private static final boolean DefaultTangibility = false;

	private static final Logger logger = Logger.getLogger(ThreeDSMaxSpace.class);

	private UURI uri;

	private Spatial node;

	public static class ModelLoader {

		static final FormatConverter CONVERTER_3DS = new MaxToJme();

		/**
		 * Imports a .3ds model from file system. Like the 2-argument method
		 * with a null textureDir. The texture file(s) are assumed to be in the
		 * same directory as the model file.
		 * @param modelPath the path to the model file. 
		 * Can be relative to the project directory.
		 * @return a Spatial containing the model geometry 
		 * (with provided texture, if any) that can be attached to
		 *  the scenegraph, or null instead if unable to load geometry.
		 */
		//		public static Spatial load3ds(String modelPath) {
		//			return load3ds(modelPath, null);
		//		}

		//		/**
		//		 * Imports a .3ds model from file system.
		//		 * @param modelPath the path to the model file. 
		//		 * Can be relative to the project directory.
		//		 * @param textureDir the path to the directory with the model's
		//		 *  textures. If null, this will attempt to infer textureDir from
		//		 *  modelPath, which assumes that the texture file(s) are in the same
		//		 *  directory as the model file.
		//		 * @return a Spatial containing the model geometry 
		//		 * (with provided texture, if any) that can be attached to
		//		 *  the scenegraph, or null instead if unable to load geometry.
		//		 */
		//		public static Spatial load3ds(String modelPath, String textureDir) {
		//			Spatial output = null; // the geometry will go here.
		//			final ByteArrayOutputStream outStream =
		//				new ByteArrayOutputStream(); // byte array streams don't have to be closed
		//			try {
		//				final File textures;
		//				if(textureDir != null) { // set textureDir location
		//					textures = new File( textureDir );
		//				} else {// try to infer textureDir from modelPath.
		//					textures = new File( 
		//							modelPath.substring(0, modelPath.lastIndexOf('/')) );
		//				}	// Add texture URL to auto-locator
		//				final SimpleResourceLocator location = 
		//					new SimpleResourceLocator(textures.toURI().toURL());
		//				ResourceLocatorTool.addResourceLocator(
		//						ResourceLocatorTool.TYPE_TEXTURE, location );
		//
		//				// read .3ds file into memory & convert it to a jME usable format.
		//				final FileInputStream rawIn = new FileInputStream(modelPath);
		//				CONVERTER_3DS.convert(rawIn, outStream);
		//				rawIn.close(); // FileInputStream s must be explicitly closed.
		//
		//				// prepare outStream for loading.
		//				final ByteArrayInputStream convertedIn = 
		//					new ByteArrayInputStream(outStream.toByteArray());
		//
		//				// import the converted stream to jME as a Spatial
		//				output = (Spatial) BinaryImporter.getInstance().load(convertedIn);
		//			} catch (FileNotFoundException e) {
		//				e.printStackTrace();
		//				System.err.println("File not found at: " + modelPath);
		//			} catch (IOException e) {
		//				e.printStackTrace();
		//				System.err.println("Unable read model at: " + modelPath);
		//			} catch (URISyntaxException e) {
		//				e.printStackTrace();
		//				System.err.println("Invalid texture location at:" + textureDir);
		//			}	/* 
		//			 * The bounding box is an important optimization.
		//			 * There is no point in rendering geometry outside of the camera's
		//			 * field of view. However, testing whether each individual triangle
		//			 * is visible is nearly as expensive as actually rendering it. So you
		//			 * don't test every triangle. Instead, you just test the bounding box.
		//			 * If the box isn't in view, don't bother looking for triangles inside.
		//			 */
		//			output.setModelBound(new BoundingBox());
		//			output.updateModelBound();
		//			return output;
		//		}

		public static Spatial loadX3d(URI modelPath) throws Exception {
			X3dToJme converter = new X3dToJme();
            URL objFile=modelPath.toURL();
            ByteArrayOutputStream BO=new ByteArrayOutputStream();
            converter.convert(objFile.openStream(),BO);
            
            //jbr.setProperty("texurl",new File(".").toURL());
            long time=System.currentTimeMillis();
            Node r=(Node)BinaryImporter.getInstance().load(new ByteArrayInputStream(BO.toByteArray()));
            logger.info("Finished loading time is "+(System.currentTimeMillis()-time));

            
            r.setModelBound(new OrientedBoundingBox());
            r.updateModelBound();
            
            return r;

		}
		
		public static Spatial load3ds(URI modelPath, String textureDir) {
			Spatial output = null; // the geometry will go here.
			final ByteArrayOutputStream outStream =
				new ByteArrayOutputStream(); // byte array streams don't have to be closed
			try {
				final File textures;
				if(textureDir != null) { // set textureDir location
					textures = new File( textureDir );
				} else {// try to infer textureDir from modelPath.
					textures = new File( 
							modelPath.toString().substring(0, modelPath.toString().lastIndexOf('/')) );
				}	// Add texture URL to auto-locator
				final SimpleResourceLocator location = 
					new SimpleResourceLocator(textures.toURI().toURL());
				ResourceLocatorTool.addResourceLocator(
						ResourceLocatorTool.TYPE_TEXTURE, location );

				// read .3ds file into memory & convert it to a jME usable format.
				final FileInputStream rawIn = new FileInputStream(new File(modelPath));
				CONVERTER_3DS.convert(rawIn, outStream);
				rawIn.close(); // FileInputStream s must be explicitly closed.

				// prepare outStream for loading.
				final ByteArrayInputStream convertedIn = 
					new ByteArrayInputStream(outStream.toByteArray());

				// import the converted stream to jME as a Spatial
				output = (Spatial) BinaryImporter.getInstance().load(convertedIn);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.err.println("File not found at: " + modelPath);
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Unable read model at: " + modelPath);
			} catch (URISyntaxException e) {
				e.printStackTrace();
				System.err.println("Invalid texture location at:" + textureDir);
			}	

			/* 
			 * The bounding box is an important optimization.
			 * There is no point in rendering geometry outside of the camera's
			 * field of view. However, testing whether each individual triangle
			 * is visible is nearly as expensive as actually rendering it. So you
			 * don't test every triangle. Instead, you just test the bounding box.
			 * If the box isn't in view, don't bother looking for triangles inside.
			 */
			OrientedBoundingBox bound = new OrientedBoundingBox();
			output.setModelBound(bound);
			output.updateModelBound();
			
			
			return output;
		}

	}

	public ThreeDSMaxSpace(URL u)  {
		this(new UURI(u));
	}

	public ThreeDSMaxSpace(UURI u)  {
		super();
		this.uri = u;
		tangible(DefaultTangibility);
	}

	@Override public void start() {
		updateMesh();
	}

	private void updateMesh()  {
		if (node!=null) {
			remove(node);
			node = null;
		}

		Jme.doLater(new Runnable() {

			@Override public void run() {
				try {
					if (uri.toString().endsWith(".3ds"))
						node = ModelLoader.load3ds(uri.toURI(), null);
					else if (uri.toString().endsWith(".x3d"))
						node = ModelLoader.loadX3d(uri.toURI());
					
					if (node!=null)
						add(node);


				} catch (Exception e) {
					logger.error(e);
					return;
				}
			}


		});

	}

	@Override public void stop() {

	}


}
