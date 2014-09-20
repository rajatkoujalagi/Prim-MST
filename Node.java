
public class Node extends Edge{ //Node class inherits Edge 
	int deg;  //for degree
	int data; // this will have the weight of edge
	Node child;
	Node leftSib;
	Node rightSib;
	Node parent;
	boolean childCut;
	
	public Node(Vertex src, Vertex dst, int cost){
		// calling edge constructor
		super(src, dst, cost);
		this.data = cost;
	}
}
