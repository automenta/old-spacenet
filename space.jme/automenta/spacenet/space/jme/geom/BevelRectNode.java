package automenta.spacenet.space.jme.geom;

import automenta.spacenet.space.geom3.BevelRect;
import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.jme.video.JmeNode;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;

import com.jme.bounding.OrientedBoundingBox;
import com.jme.math.Vector3f;
import com.jme.scene.TriMesh;
import com.jme.util.geom.BufferUtils;

public class BevelRectNode extends RectNode {

	protected class BevelMesh extends TriMesh {

		private int numTriangles;
		private int numPoints;

		public BevelMesh() {
			super();

			this.numTriangles = 2 /* face */ + (4 * 2) /* 4 sides */;
			this.numPoints = 8;

			setVertexCount(numPoints);
			setVertexBuffer(BufferUtils.createVector3Buffer(getVertexCount()));
			setNormalBuffer(BufferUtils.createVector3Buffer(getVertexCount()));
			//	        getTextureCoords().set(0,
			//	                new TexCoords(BufferUtils.createVector2Buffer(getVertexCount())));

			setTriangleQuantity(numTriangles);
			setIndexBuffer(BufferUtils.createIntBuffer(3 * getTriangleCount()));

			setModelBound(new OrientedBoundingBox());

			updateBevelMesh();
		}

		protected void updateBevelMesh() {
			Jme.doLater(new Runnable() {

				@Override public void run() {
					setVertexData();
					setIndexData();
					setNormalData();

					updateRenderState();
					
					updateGeometricState(0.0f, true);
					
					updateModelBound();
					
				}
				
			});
		}

		/**
		 * vertex 0 = center
		 * vertex 1..(sides+1) = outer points
		 */
		private void setVertexData() {
			getVertexBuffer().rewind();

			float bw = bevel.getBevelBorderX().f();
			float bh = bevel.getBevelBorderY().f();
			float wOut = (float) 1.0;
			float hOut = (float) 1.0;
//			float wOut = (float) getSize().x();
//			float hOut = (float) getSize().y();
			float wIn = (float) (wOut * (1.0 - bw));
			float hIn = (float) (hOut * (1.0 - bh));
			float zIn = bevel.getBevelZ().f();

			//face
			getVertexBuffer().put(wIn/2.0f).put(hIn/2.0f).put(zIn);
			getVertexBuffer().put(wIn/2.0f).put(-hIn/2.0f).put(zIn);
			getVertexBuffer().put(-wIn/2.0f).put(-hIn/2.0f).put(zIn);
			getVertexBuffer().put(-wIn/2.0f).put(hIn/2.0f).put(zIn);

			//sides
			getVertexBuffer().put(wOut/2.0f).put(hOut/2.0f).put(0);
			getVertexBuffer().put(wOut/2.0f).put(-hOut/2.0f).put(0);
			getVertexBuffer().put(-wOut/2.0f).put(-hOut/2.0f).put(0);
			getVertexBuffer().put(-wOut/2.0f).put(hOut/2.0f).put(0);

			//getTextureCoords().get(0).coords.put(0).put(0);


		}

		/**
		 * each outer side 'i' is a side of a triangle, with indices (0, i, i+1)
		 */
		private void setIndexData() {
			getIndexBuffer().rewind();

			//Front
			getIndexBuffer().put(0).put(3).put(2);
			getIndexBuffer().put(1).put(0).put(2);

			//South
			getIndexBuffer().put(6).put(1).put(2);
			getIndexBuffer().put(5).put(1).put(6);

			//East
			getIndexBuffer().put(4).put(0).put(5);
			getIndexBuffer().put(5).put(0).put(1);     

			//West
			getIndexBuffer().put(2).put(3).put(7);
			getIndexBuffer().put(6).put(2).put(7);     

			//North
			getIndexBuffer().put(3).put(0).put(7);
			getIndexBuffer().put(0).put(4).put(7);     

		}

		/**
		 * Sets all the default vertex normals to 'up', +1 in the Z direction.
		 */
		private void setNormalData() {
			//            BufferUtils.setInBuffer(new Vector3f(1,1,1).normalizeLocal(), getNormalBuffer(), 0);
			//            BufferUtils.setInBuffer(new Vector3f(1,-1,1).normalizeLocal(), getNormalBuffer(), 1);
			//            BufferUtils.setInBuffer(new Vector3f(-1,-1,1).normalizeLocal(), getNormalBuffer(), 2);
			//            BufferUtils.setInBuffer(new Vector3f(-1,1,1).normalizeLocal(), getNormalBuffer(), 3);
			//            
			//            BufferUtils.setInBuffer(new Vector3f(1,1,0).normalizeLocal(), getNormalBuffer(), 4);
			//            BufferUtils.setInBuffer(new Vector3f(1,-1,0).normalizeLocal(), getNormalBuffer(), 5);
			//            BufferUtils.setInBuffer(new Vector3f(-1,-1,0).normalizeLocal(), getNormalBuffer(), 6);
			//            BufferUtils.setInBuffer(new Vector3f(-1,1,0).normalizeLocal(), getNormalBuffer(), 7);

			final Vector3f up = new Vector3f(0,0,1);

			BufferUtils.setInBuffer(up, getNormalBuffer(), 0);
			BufferUtils.setInBuffer(up, getNormalBuffer(), 1);
			BufferUtils.setInBuffer(up, getNormalBuffer(), 2);
			BufferUtils.setInBuffer(up, getNormalBuffer(), 3);

			final Vector3f a = new Vector3f(1,1,1).normalizeLocal();
			final Vector3f b = new Vector3f(1,-1,1).normalizeLocal();
			final Vector3f c = new Vector3f(-1,-1,1).normalizeLocal();
			final Vector3f d = new Vector3f(-1,1,1).normalizeLocal();

			BufferUtils.setInBuffer(a, getNormalBuffer(), 4);
			BufferUtils.setInBuffer(b, getNormalBuffer(), 5);
			BufferUtils.setInBuffer(c, getNormalBuffer(), 6);
			BufferUtils.setInBuffer(d, getNormalBuffer(), 7);

		}


	}

	private BevelMesh bevelMesh;
	private BevelRect bevel;


	public BevelRectNode(BevelRect br) {
		super(br);
		this.bevel = br;
		this.bevelMesh = new BevelMesh();
	}

	@Override protected void attachRectGeometry() {
		attachSpatials(bevelMesh);							
	}
	@Override protected void detachRectGeometry() {
		detachSpatials(bevelMesh);							
	}


	@Override protected void beforeStartJme(JmeNode parent) {
		super.beforeStartJme(parent);

		
		updateBevel();

		bevel.add(new IfDoubleChanges(bevel.getBevelZ(), bevel.getBevelBorderX(), bevel.getBevelBorderY()) {
			@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
				updateBevel();				
			}			
		});

	}

	protected void updateBevel() {
		bevelMesh.updateBevelMesh();
		updateGeometricState(0.0f, true);
		updateRenderState();
	}



}
