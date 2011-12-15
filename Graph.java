package ics311;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Graph {
	boolean[][] AdjacencyMatrix;
	ArrayList<Edge> edges;
	ArrayList<Vertex> vertices;
	ArrayList<Vertex> dfsArray;
	int time;
	private ArrayList<ArrayList<Vertex>> sccArray;
	private String fileName;
	private Vertex source;
	
	public Graph() {
		edges = new ArrayList<Edge>();
		vertices = new ArrayList<Vertex>();
		time = 0;
		fileName = "";
	}
	
	public void readFromFile(String fileName) {
		this.fileName = fileName;  
		FileReader reader;
		
		try {
			reader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(reader);
			String nextLine;
			String mode = "";
			int i = 0;
			while ((nextLine = br.readLine()) != null) {
				String[] lines = nextLine.split(" ");
				i++;
				if (lines[0].equals("*Vertices")) {
					mode = "vertices";
				} else if (lines[0].equals("*Arcs")) {
					mode = "arcs";
				} else {
					if (mode.equals("vertices")) {
						Vertex v = new Vertex(lines[0]);
						v.setid(i-1);
						vertices.add(v);
					} else if (mode.equals("arcs")) {
						//System.out.print("Line: ");
						//System.out.println(nextLine);
						
						Vertex v1 = vertices.get(Integer.parseInt(lines[0])-1);
						Vertex v2 = vertices.get(Integer.parseInt(lines[1])-1);
						
						//System.out.println("v1: "+ v1.id() + ", v2: " + v2.id());
						//boolean d = false;
						//if (Float.parseFloat(lines[2]) == 1.0) {
						//	d = true;
						//}
						//System.out.println("lines2: "+ lines[2]);
						float w = Float.parseFloat(lines[2]);
						//System.out.println("w: "+w);
						
						Edge e = new Edge(v1, v2, null, false, w); // assume edge is undirected
						edges.add(e);
						//System.out.println("Edge " + e.origin().id() + "->" +e.destination().id() + ": " + e.getDist());
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// assuming e1 and e2 are different edges
	public double similarity(Edge e1, Edge e2) {
		Vertex v1 = null;
		Vertex v2 = null;
		// Find the impost nodes
		if (e1.origin().equals(e2.origin())) {
			v1 = e1.destination();
			v2 = e2.destination();
		} else if (e1.destination().equals(e2.destination())) {
			v1 = e1.origin();
			v2 = e2.origin();
		} else if (e1.destination().equals(e2.origin())) {
			v1 = e1.origin();
			v2 = e2.destination();
		} else if (e1.origin().equals(e2.destination())) {
			v1 = e1.destination();
			v2 = e2.origin();
		}
		
		// add to sets to do set intersect and union
		if (v1 != null &&  v2 != null) {
			HashSet<Vertex> v1adj = new HashSet<Vertex>();
			HashSet<Vertex> v2adj = new HashSet<Vertex>();
			Iterator<Vertex> v1itr = v1.adjacentVertices();
			Iterator<Vertex> v2itr = v2.adjacentVertices();
			v1adj.add(v1);
			v2adj.add(v2);
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
			return (double)adjIntersect.size() / (double)adjUnion.size();
		} else {
			return 0;
		}
	}
	
	public void buildDendrogramold() {
		HashMap<String,Edge[]> initial = new HashMap<String,Edge[]>();
		// first determine the similarity of all link pairs (edge pairs with
		// 1 vertex that is the same)
		Iterator<Edge> edgeItr1 = edges.iterator();
		
		while (edgeItr1.hasNext()) {
			Edge e = edgeItr1.next();
			
		}
	}
	
	public void buildDendrogramold2() {
		Iterator<Vertex> itr = vertices.iterator();
		// Iterate over each vertices in G.V
		// Treat it as the keystone node of a link pair
		while (itr.hasNext()) {
			//
			Vertex keystone = itr.next();
			// Duplicate the keystone's adjacent vertices arraylist,
			// so that we can try all possible combinations of the keystone's
			// adjacent vertices
			ArrayList<Vertex> innerLoopVertices = (ArrayList<Vertex>) keystone.adjacentVerticesArray().clone();
			Iterator<Vertex> outerLoopAdjItr = keystone.adjacentVertices();
			while (outerLoopAdjItr.hasNext()) {
				Vertex impost1 = outerLoopAdjItr.next();
				// Remove self from the innerloopvertices array
				innerLoopVertices.remove(impost1);
				Iterator<Vertex> innerLoopIterator = innerLoopVertices.iterator();
				while (innerLoopIterator.hasNext()) {
					Vertex impost2 = innerLoopIterator.next();
					// find the similarity between these two edges:
					// (keystone, impost1) and (keystone, impost2)
					
					// need to augment Vertex to hold a set of its adjacent vertices
					HashSet<Vertex> v1adj = new HashSet<Vertex>();
					HashSet<Vertex> v2adj = new HashSet<Vertex>();
					/*Iterator<Vertex> v1itr = v1.adjacentVertices();
					Iterator<Vertex> v2itr = v2.adjacentVertices();
					v1adj.add(v1);
					v2adj.add(v2);
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
					adjIntersect.retainAll(v2adj);*/
				}
			}
		}
	}
	
	public void buildDendrogram() {
		ArrayList<LinkPair> pairs = new ArrayList<LinkPair>();
		
		Iterator<Vertex> itr = vertices.iterator();
		// Iterate over each vertices in G.V
		// Treat it as the keystone node of a link pair
		while (itr.hasNext()) {
			//
			Vertex keystone = itr.next();
			// Duplicate the keystone's adjacent vertices arraylist,
			// so that we can try all possible combinations of the keystone's
			// adjacent vertices
			ArrayList<Edge> innerLoopEdges = (ArrayList<Edge>) keystone.incidentEdges.clone();
			Iterator<Edge> outerLoopAdjItr = keystone.incidentEdges();
			while (outerLoopAdjItr.hasNext()) {
				Edge impost1 = outerLoopAdjItr.next();
				// Remove self from the innerloopedges array
				innerLoopEdges.remove(impost1);
				Iterator<Edge> innerLoopIterator = innerLoopEdges.iterator();
				while (innerLoopIterator.hasNext()) {
					Edge impost2 = innerLoopIterator.next();
					// find the similarity between these two edges:
					// (keystone, impost1) and (keystone, impost2)
					LinkPair pair = new LinkPair(impost1, impost2);
					pairs.add(pair);
				}
			}
		}
		
		// Now that all the similarities have been calculated, sort them
		// in descending order
		DescendingLinkPairComparator compr = new DescendingLinkPairComparator();
		Collections.sort(pairs, compr);
		// Basic print of the similarities (for debug)
		printPairs(pairs);
		
		// cc means community count, it's just a way to give each node (community)
		// a unique id number
		int cc = 1;
		System.out.println("Total pairs: "+pairs.size());
		Iterator<LinkPair> linkItr = pairs.iterator();
		while (linkItr.hasNext()) {
			LinkPair pair = linkItr.next();
			
			Node comm = new Node(cc);
			cc++;
			
			comm.left = pair.getLink1().community();
			comm.left.p = comm;
			comm.right = pair.getLink2().community();
			comm.left.p = comm;
			
			comm.height = pair.s();
		}
		HashSet<Node> communities = new HashSet<Node>();
		Iterator<Edge> e_itr = edges.iterator();
		while (e_itr.hasNext()) {
			Edge e = e_itr.next();
			
			communities.add(e.community());
			e.community().calcVertices();
		}
		System.out.println("Total communities: " + communities.size());
		
		// Convert to ArrayList for sorting
		ArrayList<Node> comms = new ArrayList<Node>(communities);
		// then sort by community size (# of vertices)
		DescendingCommComparator ccompr = new DescendingCommComparator();
		Collections.sort(comms, ccompr);
		Iterator<Node> c_itr = comms.iterator();
		int i = 0;
		while (c_itr.hasNext()) {
			i++;
			if (i > 20) break;
			Node c = c_itr.next();
			System.out.println("Community: " + c.id+ ", size: "+c.vertices.size());
			Iterator<Vertex> v_itr = c.vertices.iterator();
			
		}
		
		Iterator<Vertex> v_itr = vertices.iterator();
		while (v_itr.hasNext()) {
			v_itr.next().calcCommunities();
		}
		DescendingVertexComparator vcompr = new DescendingVertexComparator();
		Collections.sort(vertices, vcompr);
		v_itr = vertices.iterator();
		i = 0;
		while (v_itr.hasNext()) {
			i++;
			if (i>20) break;
			Vertex v = v_itr.next();
			ArrayList<Node> v_comms = v.findCommunities();
			System.out.println("Vertex: "+v.getName()+", comms: "+v_comms.size());
		}
	}
	
	public void union(Node x, Node y) {
		link(findSet(x), findSet(y));
	}
	
	public void link(Node x, Node y) {
		if (x.rank > y.rank){
			y.p = x;
		} else {
			x.p = y;
			if (x.rank == y.rank) {
				y.rank++;
			}
		}
	}
	
	// Find a the representative of a set
	// compresses the path
	public Node findSet(Node node) {
		if (node != node.p) {
			node.p = findSet(node.p);
		}
		return node.p;
	}
	
	private void printPairs(ArrayList<LinkPair> pairs) {
		// TODO Auto-generated method stub
		Iterator<LinkPair> itr = pairs.iterator();
		while (itr.hasNext()) {
			itr.next().print();
		}
	}

	public int numVertices() {
		return vertices.size();
	}
	
	public Iterator<Vertex> vertices() {
		return vertices.iterator();
	}

	public int numEdges() {
		return edges.size();
	}
	
	public Iterator<Edge> edges() {
		return edges.iterator();
	}
	
	// Undirected Graph accessors
	
	public int degree(Vertex v) {
		return v.degree();
	}
	
	// Unused ... I think
	public ArrayList<Vertex> adjacentVerticesArray(Vertex v) {
		Iterator<Edge> itr = edges.iterator();
		ArrayList<Vertex> va = new ArrayList<Vertex>();
		
		while (itr.hasNext()) {
			Edge e = itr.next();
			Vertex[] ev = endVertices(e);
			if ((ev[0] == v) && (ev[1] != v)) {
				va.add(ev[1]);
			} else if ((ev[1] == v) && (ev[0] != v)) {
				va.add(ev[0]);
			}
		}
		
		return va;
	}
	
	public Iterator<Vertex> adjacentVertices(Vertex v) {
		return adjacentVerticesArray(v).iterator();
	}
	
	public Iterator<Edge> incidentEdges(Vertex v) {
		return v.incidentEdges();
	}
	
	public Iterator<Edge> inIncidentEdges(Vertex v) {
		return v.inIncidentEdges();
	}
	
	public Iterator<Edge> outAdjacentEdges(Vertex v) {
		return v.outIncidentEdges();
	}
	
	public Vertex[] endVertices(Edge e) {
		return e.endVertices();
	}
	
	public Vertex opposite(Vertex v, Edge e) throws InvalidEdgeException {
		if (e.destination() == v) {
			return e.origin();
		} else if (e.origin() == v) {
			return e.destination();
		} else {
			throw new InvalidEdgeException();
		}
	}
	
	public boolean areAdjacent(Vertex v1, Vertex v2) {
		Iterator<Edge> itr = v1.incidentEdges();
		
		while (itr.hasNext()) {
			Vertex[] va = endVertices(itr.next());
			if (((va[0] == v1) && (va[1] == v2)) || ((va[0] == v2) && (va[1] == v1))) {
				return true;
			}
		}
		
		return false;
	}
	
	
	// Directed Graph Accessors
	public Iterator<Edge> directedEdges() {
		ArrayList<Edge> directedEdges = new ArrayList<Edge>();
		Iterator<Edge> itr = edges.iterator();
		
		while (itr.hasNext()) {
			Edge e = itr.next();
			if (e.isDirected() == true) {
				directedEdges.add(e);
			}
		}
		return directedEdges.iterator();
	}
	
	public Iterator<Edge> undirectedEdges() {
		ArrayList<Edge> directedEdges = new ArrayList<Edge>();
		Iterator<Edge> itr = edges.iterator();
		
		while (itr.hasNext()) {
			Edge e = itr.next();
			if (e.isDirected() == false) {
				directedEdges.add(e);
			}
		}
		return directedEdges.iterator();
	}
	
	public int inDegree(Vertex v) {
		return v.inDegree();
	}
	
	public int outDegree(Vertex v) {
		return v.outDegree();
	}
	
	public Iterator<Vertex> inAdjacentVertices(Vertex v) {
		return v.inAdjacentVertices();
	}
	
	public Iterator<Vertex> outAdjacentVertices(Vertex v) {
		return v.inAdjacentVertices();
	}
	
	// Mutators
	
	public Edge insertEdge(Vertex u, Vertex v, Object info, int weight) {
		Edge e = new Edge(u, v, info, false, weight);
		edges.add(e);
		return e;
	}
	
	public Vertex insertVertex(String name) {
		Vertex v = new Vertex(name);
		vertices.add(v);
		return v;
	}
	
	public Edge insertDirectedEdge(Vertex u, Vertex v, Object info, int weight) {
		Edge e = new Edge(u, v, info, true, weight);
		edges.add(e);
		return e;
	}
	
	public Object removeVertex(Vertex v) {
		vertices.remove(v);
		v.removeSelf();
		// TODO: remove each edge on v and remove associated edges on other v's
		
		return v;
	}
	
	public Object removeEdge(Edge e) {
		edges.remove(e);
		Vertex[] va = e.endVertices();
		va[0].removeSelf();
		va[1].removeSelf();
		// TODO: Same as above.
		
		return e;
	}
	
	public void setDirectionFrom(Edge e, Vertex newOrigin) {
		e.setOrigin(newOrigin);
	}
	
	public void setDirectionTo(Edge e, Vertex newDestination) {
		e.setDestination(newDestination);
	}
	
	public void makeUndirected(Edge e) {
		e.setDirected(false);
	}
	
	public void reverseDirection(Edge e) throws InvalidEdgeException {
		if (e.isDirected() == false) {
			throw new InvalidEdgeException();
		} else {
			Vertex u = e.origin();
			e.setOrigin(e.destination());
			e.setDestination(u);
		}
	}

	public void printEdges() {
		// TODO Auto-generated method stub
		Iterator<Edge> itr=edges.iterator();
		while (itr.hasNext()) {
			Edge e = itr.next();
			Vertex[] va = e.endVertices();
			System.out.println("Edge: " + va[0].id() + ", " + va[1].id());
		}
	}

	// print for debugging
	public void printVertices() {
		// TODO Auto-generated method stub
		System.out.println("Time: "+time);
		Iterator<Vertex> itr = vertices.iterator();
		while (itr.hasNext()) {
			Vertex v = itr.next();
			Vertex pi = (Vertex)v.getAnnotation("pi");
			int pi_id = 0;
			if (pi != null) {
				pi_id = pi.id();
			}
			System.out.println("Vertex " + v.id() + ": color: " + "; time: " + v.getAnnotation("d") + "; finish: " + v.getAnnotation("finish") + "; id: " + v.getAnnotation("color") + "; pi: "+ pi_id);
		}
		Iterator<ArrayList<Vertex>> itr2 = sccArray.iterator();
		while (itr2.hasNext()) {
			ArrayList<Vertex> va = itr2.next(); // this represents a single connected comp.
			// sort it!
			DescendingComparator compr = new DescendingComparator();
			Collections.sort(va, compr);
			Iterator<Vertex> itr3 = va.iterator();
			System.out.print("{");
			while (itr3.hasNext()) {
				Vertex x = itr3.next();
				System.out.print(x.id() + ":" + x.degree() + " ");
			}
			System.out.print("}, ");
		}
		System.out.println(" fin.");
		
		
	}
	
	public void setSource(Vertex s) {
		this.source = s;
	}
	
	private Vertex findRoot(ArrayList<Vertex> av) {
		// TODO Auto-generated method stub
		Iterator<Vertex> itr = av.iterator();
		while (itr.hasNext()) {
			Vertex v = itr.next();
			if (v.getAnnotation("root").equals(true)) return v;
		}
		return null;
	}
	
	public void dijkstra(Vertex s) {
		// check that v is in vertices
		if (vertices.contains(s)) {
			this.setSource(s);
			//System.out.println("Source s has " + s.outDegree() + " outbound edges.");
			Iterator<Vertex> itr = vertices.iterator();
			
			// Initialize all vertices to have infinite distance
			while (itr.hasNext()) {
				Vertex v = itr.next();
				v.setDist(0);
				v.setInfDist(true);
				v.setPrev(null);
			}
			s.setDist(0);
			s.setInfDist(false);
			ArrayList<Vertex> bigS = new ArrayList<Vertex>();
			@SuppressWarnings("unchecked")
			ArrayList<Vertex> q = (ArrayList<Vertex>) vertices.clone();
			
			while (q.size() > 0) { // while Q is not empty
				Vertex u = minDistance(q);
				//System.out.print("Iterating vertex "+u.id() + ": ");
				if (u.getInfDist()) { // check for infinity
					break;
				}// else { System.out.println(u.getDist()); }
				q.remove(u);
				bigS.add(u);
				Iterator<Edge> adj_itr = u.outIncidentEdges();
				while (adj_itr.hasNext()) {
					Edge e = adj_itr.next();
					relax(e);
				}
			}
		}
	}
	
	// build a list of paths (sequences of vertices) for each vertex other than the source
	public ArrayList<Path> constructPaths() {
		ArrayList<Path> results = new ArrayList<Path>();
		// one way to speed this up would be to only iterate through
		// vertices for which paths exist
		// these could be tabulated through relax()
		Iterator<Vertex> itr = vertices.iterator();
		int invalid = 0;
		while (itr.hasNext()) {
			
			Vertex v = itr.next();
			//System.out.println("Looking for a path from v:"+v.getName() +" to source");
			if (!v.getInfDist() && (v != source)) {
				
				ArrayList<Vertex> sequence = new ArrayList<Vertex>();
				sequence.add(v);
				while ((v.getPrev() != null) && (v.getPrev() != source)) {
					v = v.getPrev();
					sequence.add(v);
				}
				sequence.add(source);
				Collections.reverse(sequence);
				Path p = new Path(sequence);
				if (p.valid) {
					results.add(p);
				} else {
					invalid++;
				}
				
			}
		}
		//System.out.println("Invalid: "+ invalid);
		return results;
	}
	
	public Vertex minDistance(ArrayList<Vertex> vertexArray) {
		Iterator<Vertex> itr = vertexArray.iterator();
		Vertex min = vertexArray.get(0);
		
		while (itr.hasNext()) {
			Vertex u = itr.next();
						
			// if u is not infinite AND either (u is less than min OR min is infinite)
			if ((!u.getInfDist()) && ((u.getDist() < min.getDist()) || (min.getInfDist()))) {
				min = u;
			}
		}
		
		return min;
	}
	
	public void relax(Edge e) {
		Vertex verts[] = e.endVertices();
		Vertex u = verts[0];
		Vertex v = verts[1];
		
		// (x, inf) always set
		// (inf, x) ignore
		// (inf, inf) ignore
		
		if ((u.getInfDist() != true) && ((v.getInfDist()) || (v.getDist() > u.getDist() + e.getDist()))) {
			//System.out.println("Relaxing "+u.id()+","+u.getDist() + "->"+v.id() +","+v.getDist()+" to weight " + (u.getDist() + e.getDist()));
			v.setDist(u.getDist() + e.getDist());
			v.setInfDist(false);
			//System.out.println("New value for "+v.id() + ": " + v.getDist());
			v.setPrev(u);
		}
	}

	public boolean bellmanford(Vertex s) {
		// TODO Auto-generated method stub
		source = s;
		Iterator<Vertex> itr = vertices.iterator();
		//System.out.println("Starting Bellman-Ford from vertex "+s.id());
		
		// Initialize all vertices to have infinite distance
		while (itr.hasNext()) {
			Vertex v = itr.next();
			v.setDist(0);
			v.setInfDist(true);
			v.setPrev(null);
		}
		s.setDist(0);
		s.setInfDist(false);
		
		for (int i=1; i<numVertices()-1; i++) {
			Iterator<Edge> itr2 = edges.iterator();
			while (itr2.hasNext()) {
				relax(itr2.next());
			}
		}
		
		Iterator<Edge> edge_itr = edges.iterator();
		while (edge_itr.hasNext()) {
			Edge e = edge_itr.next();
			Vertex u = e.origin();
			Vertex v = e.destination();

			// What if u is infinity?
			// What about v?
			// (x, inf) this will never happen if there is an edge
			// (inf, x) same as above
			// (inf, inf) irrelevant
			if (!u.getInfDist() && !v.getInfDist()) {
				if ((u.getDist() + e.getDist() < v.getDist())) {
					System.out.println("Negative cycle found when testing "+u.getName() + "->" + v.getName()+": distance is "+(u.getDist()+ e.getDist()) + " but vertex "+v.getName()+" is "+ v.getDist());
					System.out.println("Backtracking from vertex "+v.getName()+" to find the cycle...");
					ArrayList<Vertex> sequence = new ArrayList<Vertex>();
					sequence.add(v);
					while ((v.getPrev() != null) && (v.getPrev() != source)) {
						v = v.getPrev();
						if (sequence.contains(v)) {
							break;
						}
						sequence.add(v);
					}
					Collections.reverse(sequence);
					
					System.out.print("The cycle is probably: "+v.getName()+"->");
					Iterator<Vertex> vert_itr = sequence.iterator();
					while (vert_itr.hasNext()) {
						Vertex pv = vert_itr.next();
						
						if (pv == v) {
							System.out.println(v.getName());
							break;
						} else {
							System.out.print(pv.getName()+"->");
						}
					}
					System.out.println("Please check the graph input data.");
					return false;
				}
			}
		}
		return true;
	}
}