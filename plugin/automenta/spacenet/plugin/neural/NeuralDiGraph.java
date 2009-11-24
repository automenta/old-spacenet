package automenta.spacenet.plugin.neural;

import automenta.spacenet.var.graph.DiGraph;
import java.util.Iterator;
import org.neuroph.core.Connection;
import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;

public class NeuralDiGraph extends DiGraph<Neuron, Connection> {

    private NeuralNetwork net;

    public NeuralDiGraph() {
        super();
    }

    public NeuralDiGraph(NeuralNetwork net) {
        this();
        setNetwork(net);
    }

    public NeuralNetwork getNet() {
        return net;
    }

    public void setNetwork(NeuralNetwork net) {
        this.net = net;
        
        clear();
        Iterator<Layer> li = net.getLayersIterator();
        while (li.hasNext()) {
            Layer l = li.next();
            Iterator<Neuron> ni = l.getNeuronsIterator();
            while (ni.hasNext()) {
                Neuron neuron = ni.next();
                addVertex(neuron);
                Iterator<Connection> ci = neuron.getInputConnections().iterator();
                while (ci.hasNext()) {
                    Connection connection = ci.next();
                    Neuron fromNeuron = connection.getConnectedNeuron();
                    addEdge(connection, fromNeuron, neuron);
                }
            }
        }
    }
}
