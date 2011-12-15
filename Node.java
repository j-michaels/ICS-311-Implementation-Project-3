package ics311;

import java.util.HashSet;

public class Node {
	public Node p;
	public Node left;
	public Node right;
	public Edge edge;
	public double height;
	public int rank;
	public int id;
	public HashSet<Vertex> vertices;
	
	public Node(int id) {
		this.vertices = null;
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
	
	public HashSet<Vertex> findVertices() {
		HashSet<Vertex> rverts = new HashSet<Vertex>();
		if (right == null) { // must be a leaf node
			rverts.add(edge.destination());
			rverts.add(edge.origin());
		} else {
			rverts.addAll(right.findVertices());
			rverts.addAll(left.findVertices());
		}
		return rverts;
	}

	public void calcVertices() {
		// TODO Auto-generated method stub
		this.vertices = findVertices();
	}
}
