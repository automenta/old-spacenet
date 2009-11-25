/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.run.graph.neural;

import automenta.spacenet.plugin.neural.NeuralDiGraph;
import automenta.spacenet.Starts;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.run.graph.ForceDirectedControlWindow;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.Line3D;
import automenta.spacenet.space.object.graph.GraphBox;
import automenta.spacenet.space.object.graph.SpatializeGraph;
import automenta.spacenet.space.object.graph.arrange.ForceDirectedGraphArranger;
import automenta.spacenet.space.object.graph.arrange.ForceDirectedGraphArranger.ForceDirectedParameters;
import automenta.spacenet.space.object.widget.button.Button;
import automenta.spacenet.space.object.widget.button.ButtonAction;
import automenta.spacenet.space.object.widget.button.TextButton;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;
import org.neuroph.core.Connection;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.Weight;
import org.neuroph.nnet.Kohonen;
import org.neuroph.nnet.MultiLayerPerceptron;

/** visualizes a Neuroph Kohenen neural-network */
public class DemoNeuroph extends Box implements Starts {

    final public static double annUpdatePeriod = 0.1;
    final public static double weightUpdatePeriod = 0.25;
    int numInputs = 8;
    int numOutputs = 4;
    private NeuralDiGraph netGraph;

    public static class NeuralSpatializer implements SpatializeGraph {

        final public static double neuronUpdatePeriod = 0.2;

        public NeuralSpatializer() {
            super();
        }

        @Override public Space newEdgeSpace(Object edge, Box pa, Box pb) {
            final Line3D line = new Line3D(pa.getPosition(), pb.getPosition(), new DoubleVar(0.01), 3);
            if (edge instanceof Connection) {
                final Connection c = (Connection) edge;
                final Weight weight = c.getWeight();
                //final Box box = line.add(new Box(pa.getPosition(), new Vector3(0.5, 0.5, 0.5)).color(getWeightColor(weight)));

                //final Color co = new Color(1,1,1);
                //box.color(co);

                line.color(Color.newRandomHSB(0.5, 0.7));
                
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

        @Override public Box newVertexBox(final Object vertex) {


            if (vertex instanceof Neuron) {

                final Box rect = new Box().scale(0.9).move(0, 0, 0.1);
                final Neuron n = (Neuron) vertex;
                
                

                return rect;
            }

            return null;
        }
    }

    public class NeuralNetControlWindow extends Window {

        public NeuralNetControlWindow() {
            super();

            TextButton kohonenButton = add(new TextButton("Kohonen"));
            kohonenButton.addButtonAction(new ButtonAction() {
                @Override public void onButtonPressed(Button b) {
                    setNetworkKohonen();
                }
            });

            TextButton mlButton = add(new TextButton("MultiLayer Perceptron"));
            mlButton.addButtonAction(new ButtonAction() {
                @Override public void onButtonPressed(Button b) {
                    setNetworkMultiLayerPerceptron();
                }
            });

            kohonenButton.span(-0.4, -0.4, 0.4, -0.3);

            mlButton.span(-0.4, -0.2, 0.4, -0.1);

        }


    }

    protected void setNetwork(NeuralNetwork n) {
        n.randomizeWeights();
        randomInputs(n);
        netGraph.setNetwork(n);
    }
    
    protected void setNetworkMultiLayerPerceptron() {
        setNetwork(new MultiLayerPerceptron(4, 6, 5, 2, 1));
    }
    protected void setNetworkKohonen() {
        setNetwork(new Kohonen(numInputs, numOutputs));
    }

    @Override
    public void start() {

        netGraph = new NeuralDiGraph();

        setNetworkMultiLayerPerceptron();

        ForceDirectedParameters params = new ForceDirectedParameters(new Vector3(50, 50, 50), 0.01, 0.01, 2.0);
        ForceDirectedGraphArranger fd = new ForceDirectedGraphArranger(params, 0.1, 0.01, 0.25) {

            protected void updateSize(Object n, Box nBox, Vector3 nextSize) {
                if (n instanceof Neuron) {
                    double v = getNeuronScale((Neuron) n);
                    nextSize.set(v, v, v);
                    nBox.color(getNeuronColor((Neuron)n));
                }
            }
        };
        fd.setFaceCamera(false);



        final NeuralSpatializer sp = new NeuralSpatializer();
        
        final Box rotBox = add(new Box());

        rotBox.add(new GraphBox(netGraph, sp, fd));

        add(new Repeat() {

            @Override
            public double repeat(double t, double dt) {
                t/=4.0;
                rotBox.getOrientation().set(0, t, 0);
                return 0.0;
            }

        });

        ForceDirectedControlWindow fdControlWin = add(new ForceDirectedControlWindow(fd));
        fdControlWin.move(-2, 1, 0);

        NeuralNetControlWindow nnControlWin = add(new NeuralNetControlWindow());
        nnControlWin.move(-2, -1, 0);


        add(new Repeat() {

            @Override public double repeat(double t, double dt) {
                if (Math.random() < 0.1) {
                    netGraph.getNet().randomizeWeights();
                }
                if (Math.random() < 0.1) {
                    randomInputs(netGraph.getNet());
                }
                netGraph.getNet().calculate();
                return annUpdatePeriod;
            }
        });
    }

    public void randomInputs(NeuralNetwork ann) {
        for (Neuron n : ann.getInputNeurons()) {
            n.setInput((-0.5 + Math.random()) * 2.0);
        }
    }

    public Color getNeuronColor(Neuron n) {

        //float v = (float) (0.5f * (n.getOutput() + 1.0));
        float v = (float) ((n.getOutput()));

        return Color.hsb(v, 0.5, 0.5).alpha(0.1);
    }

    public double getNeuronScale(Neuron n) {
        float v = (float) (0.5f * (n.getOutput() + 1.0));
        return Math.pow(v/1.3, 2.0);
    }

    @Override
    public void stop() {
    }

    public static void main(String[] args) {
        new DefaultJmeWindow(new DemoNeuroph());
    }
}
