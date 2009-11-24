/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.run.graph.nn;

import automenta.spacenet.Starts;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.run.graph.AbstractDemoGraph.ForceDirectedControlWindow;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.Line3D;
import automenta.spacenet.space.object.graph.GraphBox;
import automenta.spacenet.space.object.graph.SpatializeGraph;
import automenta.spacenet.space.object.graph.arrange.ForceDirectedGraphArranger;
import automenta.spacenet.space.object.graph.arrange.ForceDirectedGraphArranger.ForceDirectedParameters;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;
import org.neuroph.core.Connection;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.Weight;
import org.neuroph.nnet.MultiLayerPerceptron;

/** visualizes a Neuroph Kohenen neural-network */
public class DemoKohenen extends Box implements Starts {

    final public static double annUpdatePeriod = 0.1;
    final public static double weightUpdatePeriod = 0.2;
    int numInputs = 8;
    int numOutputs = 8;
    private NeuralNetwork ann;

    public static class NeuralSpatializer implements SpatializeGraph {

        private final NeuralNetwork net;
        final public static double neuronUpdatePeriod = 0.2;

        public NeuralSpatializer(NeuralNetwork net) {
            super();
            this.net = net;
        }

        @Override public Space newEdgeSpace(Object edge, Box pa, Box pb) {
            final Line3D line = new Line3D(pa.getPosition(), pb.getPosition(), new DoubleVar(0.01), 2);
            if (edge instanceof Connection) {
                final Connection c = (Connection) edge;
                final Weight weight = c.getWeight();
                //final Box box = line.add(new Box(pa.getPosition(), new Vector3(0.5, 0.5, 0.5)).color(getWeightColor(weight)));

                //final Color co = new Color(1,1,1);
                //box.color(co);
                
                line.add(new Repeat() {

                    @Override public double repeat(double t, double dt) {
                        line.getRadius().set(0.02 * (weight.getValue() + 1.0));
                        //co.set(getWeightColor(weight));
                        return neuronUpdatePeriod;
                    }
                });

            }
            return line;
        }

        public Color getWeightColor(Weight w) {
            float v = (float) (0.5f * (w.getValue() + 1.0));
            return Color.hsb(v, 0.2, v).alpha(0.1);
        }

        public Color getNeuronColor(Neuron n) {

            //float v = (float) (0.5f * (n.getOutput() + 1.0));
            float v = (float) ( (n.getOutput()));
            
            return Color.hsb(v,0.5, 0.5).alpha(0.1);
        }

        public double getNeuronScale(Neuron n) {
            float v = (float) (0.5f * (n.getOutput() + 1.0));
            return (0.5 + v) * 0.25;
        }

        @Override public Box newVertexBox(final Object vertex) {


            if (vertex instanceof Neuron) {

                final Box rect = new Box().scale(0.9).move(0, 0, 0.1);
                final Neuron n = (Neuron) vertex;
                final Color c = getNeuronColor(n);
                rect.color(c);

                rect.add(new Repeat() {

                    @Override public double repeat(double t, double dt) {                        
                        c.set(getNeuronColor(n));
                        rect.scale(getNeuronScale(n));
                        return neuronUpdatePeriod;
                    }
                });

                return rect;
            }

            return null;
        }

        private void refresh() {
        }
    }

    @Override
    public void start() {
        //ann = new Kohonen(numInputs, numOutputs);
        ann = new MultiLayerPerceptron(6, 8, 6, 3);

        final NeuralDiGraph netGraph = new NeuralDiGraph(ann);


        ForceDirectedParameters params = new ForceDirectedParameters(new Vector3(50, 50, 50), 0.01, 0.01, 2.0);
        ForceDirectedGraphArranger fd = new ForceDirectedGraphArranger(params, 0.1, 0.01, 0.25);
        fd.setFaceCamera(true);



        final NeuralSpatializer sp = new NeuralSpatializer(ann);

        add(new GraphBox(netGraph, sp, fd));

        ForceDirectedControlWindow fdControlWin = add(new ForceDirectedControlWindow(fd));
        fdControlWin.move(-2, 0, 0);

        ann.randomizeWeights();
        randomInputs(ann);
        
        add(new Repeat() {

            @Override public double repeat(double t, double dt) {
                if (Math.random() < 0.2) {
                    ann.randomizeWeights();
                }
                if (Math.random() < 0.2) {
                    randomInputs(ann);
                }
                ann.calculate();
                return annUpdatePeriod;
            }

        });
    }

    public void randomInputs(NeuralNetwork ann) {
        for (Neuron n : ann.getInputNeurons()) {
            n.setInput(Math.random());
        }
    }

    @Override
    public void stop() {
    }

    public static void main(String[] args) {
        new DefaultJmeWindow(new DemoKohenen());
    }
}
