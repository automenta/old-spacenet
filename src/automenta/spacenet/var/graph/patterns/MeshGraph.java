package automenta.spacenet.var.graph.patterns;

import automenta.spacenet.var.graph.DiGraph;

public class MeshGraph extends DiGraph<String,String> {

	String[][] n;
	private int width;
	private int height;
	
	public MeshGraph(final int x, final int y, boolean isTorus) {
		super();

		this.width = x;
		this.height = y;

		n = new String[x][y];
		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++) {
				n[i][j] = newNode();
                addVertex(n[i][j]);
			}

		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++) {
				if (!isTorus) {
					if (i < x-1)
						addEdge("r", n[i][j], n[i+1][j]);
				}
				else {
					addEdge("r", n[i][j], n[(i+1)%x][j]);
				}
				
				if (j < y-1)
					addEdge("u", n[i][j], n[i][j+1]);
			}


	}
	
	public Object get(int x, int y) { return n[x][y]; }

	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

    public String newNode() { return null; }
    public String newEdge() { return null; }
}

