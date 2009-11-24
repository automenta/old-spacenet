package automenta.spacenet.run.graph.nn;

import automenta.spacenet.var.graph.DiGraph;
import java.util.Iterator;
import org.neuroph.core.Connection;
import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;

public class NeuralDiGraph extends DiGraph<Neuron, Connection> {

    private final NeuralNetwork net;

    public NeuralDiGraph(NeuralNetwork net) {
        super();
        this.net = net;
        refresh();
    }

    protected void refresh() {
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
