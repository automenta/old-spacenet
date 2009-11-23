package automenta.spacenet.run.net;

import automenta.spacenet.var.net.Node;
import automenta.spacenet.var.net.memory.MemoryNet;

public class MeshNet extends MemoryNet {

	Node[][] n;
	private int width;
	private int height;
	
	public MeshNet(final int x, final int y, boolean isTorus) {
		super();

		this.width = x;
		this.height = y;

		n = new Node[x][y];
		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++) {
				n[i][j] = addNode("x");
			}

		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++) {
				if (!isTorus) {
					if (i < x-1)
						addLink("r", n[i][j], n[i+1][j]);
				}
				else {
					addLink("r", n[i][j], n[(i+1)%x][j]);						
				}
				
				if (j < y-1)
					addLink("u", n[i][j], n[i][j+1]);
			}


	}
	
	public Node get(int x, int y) { return n[x][y]; }

	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}

