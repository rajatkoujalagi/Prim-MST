import java.util.HashSet;

// Each vertex will have a label and set of edges linked to it
// whose destination nodes form the adjacency list of that vertex
public class Vertex {
	int label;
	HashSet<Edge> edgesGroup = new HashSet<Edge>();

	public HashSet<Edge> getEdges() {
		return edgesGroup;
	}
	
	
	public Vertex(int label, Edge ed) { //constructor initializing variables
		this.label = label;
		edgesGroup.add(ed);
	}

	public Vertex(int label) {
		this.label = label;
	}

	public void addEdge(Edge ed) { //adds an edge to edge group
		edgesGroup.add(ed);
	}

	

	
	public void removeEdge(Edge e) { //removes a selected edge from the group
		for(Edge ed : edgesGroup){
			if((e.src.label==ed.src.label || e.src.label==ed.dst.label) && 
					(e.dst.label==ed.src.label || e.dst.label==ed.dst.label)){
				edgesGroup.remove(ed);
				break;
			}
		}
	}
}
