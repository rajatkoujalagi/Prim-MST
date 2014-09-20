import java.util.ArrayList;
import java.util.HashSet;

//This class is for implementing Prim's algorithm
//in Simple Scheme. It iterates through an array
//of sets. Time Complexity = O(n*n)

public class SimpleScheme implements Prim {
	HashSet<Vertex> mstVtx = new HashSet<Vertex>();
	//mstEdge will have edges of MST
	ArrayList<Edge> mstEdge = new ArrayList<Edge>(); 
	ArrayList<Integer> mstVtxList = new ArrayList<Integer>();
	int mincost;
	int MAX_VAL = Integer.MAX_VALUE; //max value in int

	/* The method msTree spans the graph and finds the minimum
	edge for each vertex. It identifies the next vertex to be
	added, adds it and then removes it from the vertex group*/
	public void msTree(int noOfVertices, HashSet<Vertex> vtxGroup) 
											throws Exception {
		
		//this loop is for inserting the first
		//vertex into the mst and removing it from
		//the group of vertices
		for (Vertex v : vtxGroup) {
			if (v.label == 0) {
				mstVtx.add(v);
				mstVtxList.add(v.label);
				vtxGroup.remove(v);
				break;				
			}
		}		
		
		//this loop finds the minimum edge cost and
		//adds it
		for (int i = 0; i < noOfVertices - 1; i++) {
			Edge mEdge = minEdge();
			Vertex newV = newVertex(mEdge);
			mstVtx.add(newV);
			mstVtxList.add(newV.label);
			//removing the min edge;
			Vertex src = mEdge.src;
			Vertex dst = mEdge.dst;
			src.removeEdge(mEdge);
			dst.removeEdge(mEdge);
			vtxGroup.remove(newV);
		}
	}		
	//this method finds the min edge
	public Edge minEdge() throws Exception {
		int i = 0;
		HashSet<Edge>[] adjEdges = new HashSet[mstVtx.size()];
		//this holds all adjacent edges to a vertex
		for (Vertex v : mstVtx)
			adjEdges[i++] = v.getEdges();   
		boolean b;
		int min = MAX_VAL;
		Edge minEdge = null;
		for (HashSet<Edge> edgeSet : adjEdges) {
			for (Edge e : edgeSet) {
				
				b= mstVtxList.contains(e.src.label) && mstVtxList.contains(e.dst.label);
				//to check if both source and edge are
				//already present
				if (e.cost < min && !b) {
					min = e.cost;
					minEdge = e;
				}
			}
		}
		
		if (minEdge == null) {
			//if min is null, then graph is not connected
			throw new Exception("Graph not Connected");
		}
		mincost = mincost + minEdge.cost;// weight of edge is added to mincost.
		mstEdge.add(minEdge);// min Edge is added to final edge set
		return minEdge;
	}
	
	//for the new Vertex
	public Vertex newVertex(Edge e) {
		if (mstVtxList.contains(e.src.label))
			return e.dst;
		else
			return e.src;
	}	
	
	//this method prints with minimum cost first 
	//and then each edge in mst is printed
	public void finalOutput() {
		System.out.println("Simple Scheme");	
		System.out.println(mincost);
		for (Edge e : mstEdge) {
			System.out.println(e.src.label + " " + e.dst.label);
		}
	}
}
