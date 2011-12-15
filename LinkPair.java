package ics311;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class LinkPair {
	private Edge link1;
	private Edge link2;
	private Vertex impost1;
	private Vertex impost2;
	private double similarity;
	private Vertex keystone;
	public LinkPair p;
	public int rank;
	public ArrayList<LinkPair> children;
	
	public Vertex getKeystone() {
		return keystone;
	}
	
	public Vertex getImpost1() {
		return impost1;
	}
	
	public Vertex getImpost2() {
		return impost2;
	}
	
	public Edge getLink1() {
		return link1;
	}
	public void setLink1(Edge link1) {
		this.link1 = link1;
	}
	public Edge getLink2() {
		return link2;
	}
	public void setLink2(Edge link2) {
		this.link2 = link2;
	}
	
	public double s() {
		return this.similarity;
	}
	
	public LinkPair(Edge e1, Edge e2) {
		this.rank = 0;
		children = new ArrayList<LinkPair>();
		this.p = this;
		this.link1 = e1;
		this.link2 = e2;
		// Find the impost nodes
		if (e1.origin().equals(e2.origin())) {
			impost1 = e1.destination();
			impost2 = e2.destination();
			keystone = e1.origin();
		} else if (e1.destination().equals(e2.destination())) {
			impost1 = e1.origin();
			impost2 = e2.origin();
			keystone = e1.destination();
		} else if (e1.destination().equals(e2.origin())) {
			impost1 = e1.origin();
			impost2 = e2.destination();
			keystone = e1.destination();
		} else if (e1.origin().equals(e2.destination())) {
			impost1 = e1.destination();
			impost2 = e2.origin();
			keystone = e1.origin();
		}
		
		// add to sets to do set intersect and union
		if (impost1 != null &&  impost2 != null) {
			HashSet<Vertex> v1adj = new HashSet<Vertex>();
			HashSet<Vertex> v2adj = new HashSet<Vertex>();
			Iterator<Vertex> v1itr = impost1.adjacentVertices();
			Iterator<Vertex> v2itr = impost2.adjacentVertices();
			v1adj.add(impost1);
			v2adj.add(impost2);
			while (v1itr.hasNext()) {
				v1adj.add(v1itr.next());
			}
			while (v2itr.hasNext()) {
				v2adj.add(v2itr.next());
			}
			HashSet<Vertex> adjUnion = new HashSet<Vertex>();
			HashSet<Vertex> adjIntersect = new HashSet<Vertex>();
			adjUnion.addAll(v1adj);
			adjUnion.addAll(v2adj);
			adjIntersect.addAll(v1adj);
			adjIntersect.retainAll(v2adj);
			similarity = (double)adjIntersect.size() / (double)adjUnion.size();
		} else {
			similarity = 0;
		}
	}
	
	public void print() {
		// TODO Auto-generated method stub
		
		System.out.println(link1.printString() + " " + link2.printString() + ": " + this.similarity);
	}
}
