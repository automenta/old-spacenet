package automenta.spacenet.space.jme.geom;

public class FlatLine3DNode /* extends BoxNode*/ {

//    private final FlatLine3D line;
//
//    protected class Mesh extends TriMesh {
//
//        private int numTriangles;
//        private int numPoints;
//
//        public Mesh() {
//            super();
//
//            this.numTriangles = 2;
//            this.numPoints = 4;
//
//            setVertexCount(numPoints);
//            setVertexBuffer(BufferUtils.createVector3Buffer(getVertexCount()));
//            setNormalBuffer(BufferUtils.createVector3Buffer(getVertexCount()));
//            //	        getTextureCoords().set(0,
//            //	                new TexCoords(BufferUtils.createVector2Buffer(getVertexCount())));
//
//            setTriangleQuantity(numTriangles);
//            setIndexBuffer(BufferUtils.createIntBuffer(3 * getTriangleCount()));
//
//            setModelBound(new OrientedBoundingBox());
//
//            updateMesh();
//        }
//
//        protected void updateMesh() {
//            setVertexData();
//            setIndexData();
//            setNormalData();
//
//            updateRenderState();
//
//            updateGeometricState(0.0f, true);
//
//            updateModelBound();
//
//        }
//
//        /**
//         * vertex 0 = center
//         * vertex 1..(sides+1) = outer points
//         */
//        private void setVertexData() {
//            getVertexBuffer().rewind();
//
//            float bw = bevel.getBevelBorderX().f();
//            float bh = bevel.getBevelBorderY().f();
//            float wOut = (float) 1.0;
//            float hOut = (float) 1.0;
////			float wOut = (float) getSize().x();
////			float hOut = (float) getSize().y();
//            float wIn = (float) (wOut * (1.0 - bw));
//            float hIn = (float) (hOut * (1.0 - bh));
//            float zIn = bevel.getBevelZ().f();
//
//
//            float[] ul = new float[3];
//            float[] ur = new float[3];
//            float[] bl = new float[3];
//            float[] br = new float[3];
//
//            getVertexBuffer().put(ul);
//            getVertexBuffer().put(ur);
//            getVertexBuffer().put(bl);
//            getVertexBuffer().put(br);
//
//        }
//
//        /**
//         * each outer side 'i' is a side of a triangle, with indices (0, i, i+1)
//         */
//        private void setIndexData() {
//            getIndexBuffer().rewind();
//
//            getIndexBuffer().put(0).put(1).put(2);
//            getIndexBuffer().put(2).put(3).put(0);
//        }
//
//        /**
//         * Sets all the default vertex normals to 'up', +1 in the Z direction.
//         */
//        private void setNormalData() {
//
////            //TODO compute normal from cross product
////            final Vector3f normal = new Vector3f(0, 0, 1);
////            normal.
////
////            BufferUtils.setInBuffer(normal, getNormalBuffer(), 0);
////            BufferUtils.setInBuffer(normal, getNormalBuffer(), 1);
//
//        }
//    }
//
//    public FlatLine3DNode(FlatLine3D flatLine3D) {
//        super(flatLine3D);
//
//        this.line = flatLine3D;
//    }
//    private Mesh mesh;
//    private boolean requiresUpdate;
//
//    @Override protected void attachBoxGeometry() {
//        attachSpatials(mesh);
//    }
//
//    @Override protected void detachBoxGeometry() {
//        detachSpatials(mesh);
//    }
//
//    @Override protected void beforeStartJme(JmeNode parent) {
//        super.beforeStartJme(parent);
//
//        mesh.add(new IfDoubleChanges(line.getA(), line.getB(), line.getWidth()) {
//
//            @Override
//            public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
//                updateMesh();
//            }
//        });
//    }
//
//    protected synchronized void updateMesh() {
//        if (!requiresUpdate) {
//            requiresUpdate = true;
//
//            Jme.doLater(new Runnable() {
//
//                @Override public void run() {
//                    mesh.update();
//                    updateGeometricState(0.0f, true);
//                    updateRenderState();
//
//                    requiresUpdate = false;
//                }
//            });
//        }
//    }
}
