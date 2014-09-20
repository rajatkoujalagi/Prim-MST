import java.util.ArrayList;
import java.util.HashSet;

//this class is to implement Prim's algorithm
//with a fibonacci heap
public class FheapScheme implements Prim {
	FibonacciHeap fHeap = new FibonacciHeap();
	HashSet<Vertex> mstVtx = new HashSet<Vertex>();
	ArrayList<Edge> mstEdge = new ArrayList<Edge>(); //same as Simple Scheme, it has edges of mst
	int mincost;

	// inserts all edges from the given vertex whose dest is not 
		// already part of mstVtx to the Fibonacci heap.
	public void newEdge(Vertex v) {
		HashSet<Edge> temp = v.getEdges(); 
		for (Edge e : temp) {
			if (!mstVtx.contains(e.dst))
				fHeap.insert(new Node(e.src, e.dst, e.cost));
		}
	}	
	
	
	
	public void msTree(int verticesNo, HashSet<Vertex> vtxGroup)
										throws Exception {
		
		
		for (Vertex v : vtxGroup) { //this loop is for adding the first vertex(0)
			if (v.label == 0) {
				mstVtx.add(v);
				newEdge(v);
				vtxGroup.remove(v);
				break;				
			}
		}
		//same as Simple Scheme 
		for (int i = 0; i < verticesNo - 1; i++) {
			Edge mEdge = minEdge();
			Vertex newV = newVertex(mEdge);
			newEdge(newV);
			mstVtx.add(newV);
			vtxGroup.remove(newV);
		}
	}

	//for finding the min edge and checking it's validity
	public Node minEdge() throws Exception { 
		Node min;
		boolean b;
		do {
			
			min = fHeap.removeMin();
			//to check if both vertices are already included
			b = mstVtx.contains(min.src) && mstVtx.contains(min.dst);
			// same as Simple Scheme, if min is null then disconnected 
			if (min == null) {
				throw new Exception("Graph Disconnected");
			}
		} while (b);
		mincost = mincost + min.cost; // cost of edge is added to mincost. 
		mstEdge.add(min); // min Edge is added to mstEdges.
		return min;
	}	

	// implementing the Prim interface's function
	public Vertex newVertex(Edge e) {
		if (mstVtx.contains(e.src))
			return e.dst;
		else
			return e.src;
	}
		
	
	public void finalOutput() {  //the final output 
		System.out.println("Fibonacci Scheme");
		System.out.println(mincost);
		
		for (Edge e : mstEdge)
			System.out.println(e.src.label + " " + e.dst.label);
		
	}
}
