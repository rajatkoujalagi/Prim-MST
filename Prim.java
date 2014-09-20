// This is an interface for getting the minimum edge, newly added vertex and finally printing the ouput
public interface Prim {
	public Edge minEdge() throws Exception;
	public Vertex newVertex(Edge e);
	public void finalOutput();
}
