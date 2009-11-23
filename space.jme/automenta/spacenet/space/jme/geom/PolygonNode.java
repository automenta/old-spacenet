package automenta.spacenet.space.jme.geom;

import org.apache.log4j.Logger;





/** 2D regular polygon plane */
public class PolygonNode /*extends RectNode*/ {
	private static Logger logger = Logger.getLogger(PolygonNode.class);
	
//	public class PolygonMesh extends TriMesh {
//
//
//	    private int numPoints;
//
//	    private int numTriangles;
//
//		private float radius;
//
//		private int sides;
//
//		private int numSides;
//
//
//	    /**
//	     * Hexagon Constructor instantiates a new Hexagon. This element is center on
//	     * 0,0,0 with all normals pointing up. The user must move and rotate for
//	     * positioning.
//	     *
//	     * @param name
//	     *            the name of the scene element. This is required for
//	     *            identification and comparision purposes.
//	     * @param sideLength
//	     *            The length of all the sides of the tiangles
//	     */
//	    public PolygonMesh(int sides, float radius) {
//	        super();
//
//	        this.radius = radius;
//	        this.sides = sides;
//
//	        this.numTriangles = sides;
//	        this.numSides = sides;
//	        this.numPoints = sides+1;
//
//	        // allocate vertices
//	        setVertexCount(numPoints);
//	        setVertexBuffer(BufferUtils.createVector3Buffer(getVertexCount()));
//	        setNormalBuffer(BufferUtils.createVector3Buffer(getVertexCount()));
//	        getTextureCoords().set(0,
//	                new TexCoords(BufferUtils.createVector2Buffer(getVertexCount())));
//
//	        setTriangleQuantity(numTriangles);
//	        setIndexBuffer(BufferUtils.createIntBuffer(3 * getTriangleCount()));
//
//	        setVertexData();
//	        setIndexData();
//	        setNormalData();
//
//	    }
//
//	    /**
//	     * vertex 0 = center
//	     * vertex 1..(sides+1) = outer points
//	     */
//	    private void setVertexData() {
//	        getVertexBuffer().put(0).put(0).put(0);
//    		getTextureCoords().get(0).coords.put(0).put(0);
//
//	        //System.out.println("---");
//	    	for (int i = 0; i < numSides; i++) {
//	    		double theta = 2.0 * Math.PI * (i)/(numSides);
//				float x = radius/2f * (float) Math.cos( theta );
//	    		float y = radius/2f * (float) Math.sin( theta );
//	    		//System.out.println(i + " " + theta + " " + x + " " + y);
//	    		getVertexBuffer().put(x).put(y).put(0);
//	    		getTextureCoords().get(0).coords.put(x).put(y);
//	    	}
//
//	    }
//
//	    /**
//	     * each outer side 'i' is a side of a triangle, with indices (0, i, i+1)
//	     */
//	    private void setIndexData() {
//	        getIndexBuffer().rewind();
//
//	        //System.out.println("---");
//	        for (int i = 0; i < numTriangles; i++) {
//	        	//System.out.println(" 0 " + (1+((i)%numSides)) + " " + (1+((i+1)%numSides)));
//	        	getIndexBuffer().put(0);
//	        	getIndexBuffer().put(1+((i)%numSides));
//	        	getIndexBuffer().put(1+((i+1)%numSides));
//	        }
//
//	    }
//
//	    /**
//	     * Sets all the default vertex normals to 'up', +1 in the Z direction.
//	     */
//	    private void setNormalData() {
//	        Vector3f zAxis = new Vector3f(0, 0, 1);
//	        for (int i = 0; i < numPoints; i++)
//	            BufferUtils.setInBuffer(zAxis, getNormalBuffer(), i);
//	    }
//
//	}
//
//	public PolygonNode(Rect2D rect, int sides, Color color) {
//		this(rect, sides, 1.0, color);
//	}
//
//	public PolygonNode(Rect2D rect, int sides, double radius, Color color) {
//		super(rect);
//
//		if (sides < 3) {
//			logger.error("polygon has >=3 sides");
//			return;
//		}
//
//		addState(new ColorState(color));
//		startOld(new PolygonMesh(sides, (float)radius));
//	}
//
//	public PolygonNode(int sides, double radius, Color color) {
//		this(new Rect(), sides, radius, color);
//	}
//
	
}
