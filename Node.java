package ics311;

public class Node {
	public Node p;
	public Node left;
	public Node right;
	public Edge edge;
	public double height;
	public int rank;
	public int id;
	
	public Node(int id) {
		this.id = id;
		p = this;
		left = null;
		right = null;
		edge = null;
		height = 0.0;
	}
	
	public Node findSet() {
		if (p != this) {
			p = p.findSet();
		}
		return p;
	}
}
