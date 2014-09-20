//class Edge for the edges. Each edge will have a source and destination vertex
public class Edge {
	Vertex src;
	Vertex dst;
	int cost; //for the weight of each edge
	//constructor initializing the variables	
	public Edge(Vertex src, Vertex dst, int weight) {
		this.src = src;	
		this.dst = dst;
		this.cost = weight;
	}
	
}
