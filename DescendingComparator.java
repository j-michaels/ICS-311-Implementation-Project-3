package ics311;

import java.util.ArrayList;
import java.util.Comparator;

public class DescendingComparator implements Comparator<Vertex> {

	@Override
	//Compare based on the vertices degrees
	public int compare(Vertex v1, Vertex v2) {
		if (v1.degree() > v2.degree()) return -1;
	      else if (v1.degree() < v2.degree()) return 1;
	      return 0; /*equal*/
	}
	
}

class DoubleDescendingComparator implements Comparator<ArrayList<Vertex>> {

	@Override
	//Compare based on the vertices degrees
	public int compare(ArrayList<Vertex> va1, ArrayList<Vertex> va2) {
		if (va1.get(0).degree() > va2.get(0).degree()) return -1;
	      else if (va1.get(0).degree() < va2.get(0).degree()) return 1;
	      return 0; /*equal*/
	}
	
}

class DescendingLinkPairComparator implements Comparator<LinkPair> {

	@Override
	//Compare based on the LinkPair's similarity
	public int compare(LinkPair p1, LinkPair p2) {
		if (p1.s() > p2.s()) return -1;
	      else if (p1.s() < p2.s()) return 1;
	      return 0; /*equal*/
	}
	
}

class DescendingCommComparator implements Comparator<Node> {

	@Override
	//Compare based on the LinkPair's similarity
	public int compare(Node n1, Node n2) {
		if (n1.vertices.size() > n2.vertices.size()) return -1;
	      else if (n1.vertices.size() < n2.vertices.size()) return 1;
	      return 0; /*equal*/
	}
	
}

class DescendingVertexComparator implements Comparator<Vertex> {

	@Override
	//Compare based on the LinkPair's similarity
	public int compare(Vertex v1, Vertex v2) {
		if (v1.communities.size() > v2.communities.size()) return -1;
	      else if (v1.communities.size() < v2.communities.size()) return 1;
	      return 0; /*equal*/
	}
	
}