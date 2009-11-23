/**
 * 
 */
package automenta.spacenet.space.object.graph.arrange;

import automenta.spacenet.space.object.graph.*;
import java.util.Map;

import javolution.util.FastMap;

import org.apache.log4j.Logger;

import automenta.spacenet.Maths;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.HasPosition3;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.HasOrientation;
import automenta.spacenet.space.video3d.Video3D;
import automenta.spacenet.var.graph.DiGraph;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;
import edu.uci.ics.jung.graph.util.Pair;

public class ForceDirectedGraphArranger<V, E> implements ArrangeGraph<V, E> {

    private static final Logger logger = Logger.getLogger(ForceDirectedGraphArranger.class);
    private final ForceDirectedParameters params;
    private boolean faceCamera = false;

    public void setFaceCamera(boolean b) {
        this.faceCamera = b;
    }


    public static class ForceDirectedParameters {
//        private final static double DEFAULT_UPDATE_PERIOD = 0.01;
//        private final static double DEFAULT_INTERPOLATION_PERIOD = 0.005;
//        private final static double DEFAULT_INTERPOLATION_SPEED = 0.2;

        private DoubleVar stiffness;
        private DoubleVar repulsion;
        private DoubleVar lengthFactor;
        private final Vector3 boundsMax;

        public ForceDirectedParameters(Vector3 boundsMax, double initialStiffness, double initialRepulsion, double initialLengthFactor) {
            super();
            this.boundsMax = boundsMax;
            this.stiffness = new DoubleVar(initialStiffness);
            this.repulsion = new DoubleVar(initialRepulsion);
            this.lengthFactor = new DoubleVar(initialLengthFactor);
        }

        public Vector3 getBoundsMax() {
            return boundsMax;
        }

        public DoubleVar getLengthFactor() {
            return lengthFactor;
        }

        public DoubleVar getRepulsion() {
            return repulsion;
        }

        public DoubleVar getStiffness() {
            return stiffness;
        }
    }
    private double updatePeriod;
    protected Map<V, Box> nodeVis = new FastMap();
    protected Map<E, Space> linkVis = new FastMap();
    private final double interpolationPeriod;
    private final double interpolationSpeed;
    private DoubleVar timeScale = new DoubleVar(1.0);
    private DoubleVar maxSpeed = new DoubleVar(0.1);
    private Map<Box, Vector3> nextPosition = new FastMap();
    private Map<Box, Vector3> nextSize = new FastMap();
    private Repeat calcRepeat;
    private Repeat interpRepeat;
    private GraphBox<V, E> graphBox;

    public ForceDirectedGraphArranger(ForceDirectedParameters params, double updatePeriod, double interpolationPeriod, double interpolationSpeed) {
        super();
        this.params = params;
        this.updatePeriod = updatePeriod;
        this.interpolationPeriod = interpolationPeriod;
        this.interpolationSpeed = interpolationSpeed;
    }

    public ForceDirectedParameters getParams() {
        return params;
    }

    public Box getBox(V v) {
        return nodeVis.get(v);
    }

    public Vector3 getBoundsMax() {
        return getParams().getBoundsMax();
    }

//    public Node getRandomNode() {
//        List<Node> nodes = new LinkedList(nodeVis.keySet());
//        if (nodes.size() > 0) {
//            return nodes.get((int) Maths.random(0, nodes.size()));
//        }
//        return null;
//    }

    @Override
    public void addedVertex(V v, Box nb) {
        double wx = getBoundsMax().x() / 2.0;
        double wy = getBoundsMax().y() / 2.0;
        double wz = getBoundsMax().z() / 2.0;

        double sx = 0.01;
        double sy = 0.01;
        double sz = 0.01;

        nb.move(Maths.random(-wx, wx), Maths.random(-wy, wy), Maths.random(-wz, wz));
        nodeVis.put(v, nb);
    }

    @Override
    public void removedVertex(V v) {
        Box box = getBox(v);

        Space vis = nodeVis.get(v);
        nodeVis.remove(v);

        if (box != null) {
            nextPosition.remove(box);
            nextSize.remove(box);
        }

    }

    @Override
    public void addedEdge(E e, Space s, Box from, Box to) {
        linkVis.put(e, s);
    }

    @Override
    public void removedEdge(E e) {
        linkVis.remove(e);
    }

    public DiGraph<V, E> getGraph() {
        return graphBox.getGraph();
    }

    @Override public void start(GraphBox<V, E> graphBox) {
        this.graphBox = graphBox;


        calcRepeat = graphBox.add(new Repeat() {

            @Override public double repeat(double t, double dt) {
                forward(dt);
                return updatePeriod;
            }
        });
        interpRepeat = graphBox.add(new Repeat() {

            @Override public double repeat(double t, double dt) {
                interpolate(dt);
                return interpolationPeriod;
            }
        });
    }

    @Override
    public void stop() {
        graphBox.remove(calcRepeat);
        graphBox.remove(interpRepeat);
    }

    public DoubleVar getStiffness() {
        return getParams().getStiffness();
    }

    public DoubleVar getRepulsion() {
        return getParams().getRepulsion();
    }
    Vector3 force = new Vector3();

    protected Vector3 getNextPosition(Box b) {
        Vector3 v = nextPosition.get(b);
        if (v == null) {
            v = new Vector3();
            nextPosition.put(b, v);
        }
        return v;
    }

    public Vector3 getNextSize(Box b) {
        Vector3 v = nextSize.get(b);
        if (v == null) {
            v = new Vector3(1, 1, 1);
            nextSize.put(b, v);
        }
        return v;
    }

    public void forward(double dt) {
        double wx = getBoundsMax().x() / 2.0;
        double wy = getBoundsMax().y() / 2.0;
        double wz = getBoundsMax().z() / 2.0;


        for (V n : nodeVis.keySet()) {
            Box nBox = getBox(n);
            updateSize(n, nBox, getNextSize(nBox));
            updateOrientation(n, nBox, nBox.getOrientation());
        }

        for (E l : linkVis.keySet()) {
            double stiffness = getAttraction(l); //getStiffness().d();

            //Line3D line = linkVis.get(l);

            Pair endPoints = getGraph().getEndpoints(l);
            if (endPoints == null)
                continue;

            V a = (V)endPoints.getFirst();
            V b = (V)endPoints.getSecond();

            if (a == null) continue;
            if (b == null) continue;


            Box aBox = getBox(a);
            double aRad = (aBox.getSize().getMaxRadius() + aBox.getSize().getMinRadius()) / 2.0; //TODO use getAvgRadius

            Box bBox = getBox(b);
            double bRad = (bBox.getSize().getMaxRadius() + bBox.getSize().getMinRadius()) / 2.0;//TODO use getAvgRadius

            //line.getRadius().set(getLineRadius(aRad, bRad));

            double naturalLength = getLengthFactor().d() * (aRad + bRad) / 2.0;

            double currentLength = aBox.getPosition().getDistance(bBox.getPosition());

            double f = stiffness * (currentLength - naturalLength);

            double sx = f * (bBox.getPosition().x() - aBox.getPosition().x());
            double sy = f * (bBox.getPosition().y() - aBox.getPosition().y());
            double sz = f * (bBox.getPosition().z() - aBox.getPosition().z());

            getNextPosition(aBox).add(sx / 2.0, sy / 2.0, sz / 2.0);
            getNextPosition(bBox).add(-sx / 2.0, -sy / 2.0, -sz / 2.0);
        }



        for (V n : nodeVis.keySet()) {

            Box nBox = getBox(n);
            double nMass = getMass(nBox);//nBox.getSize().getMaxRadius();

            force.set(0, 0, 0);

            for (V m : nodeVis.keySet()) {
                if (n == m) {
                    continue;
                }

                double repulsion = getRepulsion(n, m); //getRepulsion().d();

                Box mBox = nodeVis.get(m);
                double mMass = getMass(mBox); //mBox.getSize().getMaxRadius();
                double dist = nBox.getPosition().getDistance(mBox.getPosition());

                double f = -repulsion * (nMass * mMass) / (dist * dist);

                double sx = f * (mBox.getPosition().x() - nBox.getPosition().x());
                double sy = f * (mBox.getPosition().y() - nBox.getPosition().y());
                double sz = f * (mBox.getPosition().z() - nBox.getPosition().z());
                force.add(sx, sy, sz);
            }

            force.multiply(dt * getTimeScale().d());

            if (force.getMagnitude() > getMaxSpeed().d()) {
                force.normalize().multiply(getMaxSpeed().d());
            }

            Vector3 p = getNextPosition(nBox); //getPosition();
            Vector3 s = nBox.getSize();


            double nx = p.x() + force.x();
            double ny = p.y() + force.y();
            double nz = p.z() + force.z();

            nx = Math.min(nx, wx - s.x() / 2.0);
            nx = Math.max(nx, -wx + s.x() / 2.0);

            ny = Math.min(ny, wy - s.y() / 2.0);
            ny = Math.max(ny, -wy + s.y() / 2.0);

            nz = Math.min(nz, wz - s.z() / 2.0);
            nz = Math.max(nz, -wz + s.z() / 2.0);


            p.set(nx, ny, nz);
        }
    }

    protected void interpolate(double dt) {
        for (V n : nodeVis.keySet()) {
            Box b = getBox(n);

            Vector3 currentPosition = b.getPosition();
            Vector3 currentSize = b.getSize();

            double p = interpolationSpeed;
            double np = 1.0 - p;

            Vector3 nextPosition = getNextPosition(b);
            Vector3 nextSize = getNextSize(b);
            double px = np * currentPosition.x() + p * nextPosition.x();
            double py = np * currentPosition.y() + p * nextPosition.y();
            double pz = np * currentPosition.z() + p * nextPosition.z();

            double sx = np * currentSize.x() + p * nextSize.x();
            double sy = np * currentSize.y() + p * nextSize.y();
            double sz = np * currentSize.z() + p * nextSize.z();

            currentPosition.set(px, py, pz);

            currentSize.set(sx, sy, sz);
        }

    }

    public DoubleVar getTimeScale() {
        return timeScale;
    }

    private Double getLineRadius(double rad, double rad2) {
        return ((rad + rad2) / 2.0) / 20.0;
    }

    private DoubleVar getMaxSpeed() {
        return maxSpeed;
    }

    public DoubleVar getLengthFactor() {
        return getParams().getLengthFactor();
    }

    protected void updateSize(V n, Box nBox, Vector3 nextSize) {
        return;
    }

    protected void updateOrientation(V n, Box nBox, Vector3 orientation) {
        if (faceCamera) {
            Vector3 camPoint = graphBox.getThe(Video3D.class).getPosition();
            Maths.rotateAngleTowardPoint(((HasPosition3) nBox).getPosition(), ((HasOrientation) nBox).getOrientation(), camPoint);

        }
        return;
    }

    protected double getAttraction(E l) {
        return getStiffness().d();
    }

    protected double getRepulsion(V n, V m) {
        return getRepulsion().d();
    }
    protected double getMass(Box nBox) {
        return 1.0;
    }

}
