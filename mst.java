import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Random;
public class mst {
	static int verticesNo;
	static int edgesNo;
	static HashSet<Vertex> vertexGroup = new HashSet<Vertex>();
		
	public static void randomModeGraph(int n, int density) {
		Random r = new Random();
		// nEdges = n*(n-1)/2 that is max no of edges with n vertices (undirected graph)
		int NumberOfEdges = ((((n * (n - 1)) / 2) * density) / 100);
		int edgesNum = 0;
		while (edgesNum < NumberOfEdges) {
			int src, dst, cost;
			src = r.nextInt(n);
			dst = r.nextInt(n);
			if (src == dst)  //to check if both source and destination are same
				continue;
			
			cost = r.nextInt(1000) + 1;

			
			Vertex vtxSrc = getVtx(src);
			Vertex vtxDst = getVtx(dst);
			
			// checking if edge already exists between the vertices.
			HashSet<Edge> tempEdges = vtxSrc.getEdges();
			for (Edge e : tempEdges) {
				if (e.dst.label == vtxDst.label)
					continue;
			}
			// creating edges
			Node e1 = new Node(vtxSrc, vtxDst, cost);
			Node e2 = new Node(vtxDst, vtxSrc, cost);
			vtxSrc.addEdge(e1);// adding edge to node(vertex)
			vtxDst.addEdge(e2);
			edgesNum++;
		}
		//System.out.println("random done - size is " + edgesNum);
	}
	
	//this checks if vertex is alreadt present in 
	//the vertex group. if not then it adds it
	public static Vertex getVtx(int vtx) { 
		for (Vertex v : vertexGroup) {
			if (v.label == vtx) {
				return v;
			}
		}
		Vertex newVertex = new Vertex(vtx);
		vertexGroup.add(newVertex);
		return newVertex;
	}
	
	
	
	public static void inputModeGraph(Prim pScheme, String fName) throws Exception {
		boolean firstLine = true; //for checking if cursor is on the first line
		try {
			BufferedReader br = new BufferedReader(new FileReader(fName));
			int count = 0;
			while (br != null && br.ready()) {
				String reader = br.readLine();
				String[] inputs = reader.split(" "); //removing spaces
				// the first line contains only 2 values				
				if (firstLine) {
					firstLine = false;			//for ensuring that we are not in first line
					if (inputs.length != 2)
						throw new Exception("Invalid input");
					verticesNo = Integer.parseInt(inputs[0]);
					edgesNo = Integer.parseInt(inputs[1]);
				} else { 
					if (inputs.length != 3) //for the edges
						throw new Exception("Invalid input");
					int src = Integer.parseInt(inputs[0]);
					int dst = Integer.parseInt(inputs[1]);
					int cost = Integer.parseInt(inputs[2]);
					Vertex vtxSrc = getVtx(src); // vertex created
					Vertex vtxDst = getVtx(dst);
					Node e1 = new Node(vtxSrc, vtxDst, cost); //adding edge with (source,destination)
					Node e2 = new Node(vtxDst, vtxSrc, cost); //adding edge with (destination,source)
					vtxSrc.addEdge(e1);
					vtxDst.addEdge(e2);
				}
				count++;
			}
			br.close();
			if (count - 1 != edgesNo)
				throw new Exception("Edges number not equal to input"); //if number of edges with cost entered is less
			if (verticesNo != vertexGroup.size())
				throw new Exception("Vertices number not equal input"); //similar for vertices			
		} catch (NumberFormatException numFormat) { //to check if edges and vertices are numbers
			throw new Exception("Invalid Input " + numFormat.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}	
	
	public static void main(String[] args) {
		int argLen = args.length;		
		if (argLen != 2 && argLen != 3) { //number of parameters should be 2 or 3
			System.out.println("Invalid number of arguments");
			return;
		}
		String arg1 = args[0];
		if (arg1.equalsIgnoreCase("-f")) {// f-heap scheme
			FheapScheme fScheme;
			try {
				String fileName = args[1];
				fScheme = new FheapScheme();
				
				inputModeGraph(fScheme, fileName); //graph generation				
				fScheme.msTree(verticesNo, vertexGroup); //min spanning tree with fscheme				
				fScheme.finalOutput();				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else if (arg1.equalsIgnoreCase("-s")) {// simple scheme
			SimpleScheme sScheme;
			try {
				String fileName = args[1];
				sScheme = new SimpleScheme();				
				inputModeGraph(sScheme, fileName); //graph generation								
				sScheme.msTree(verticesNo, vertexGroup); //min spanning tree with Simple Scheme				
				sScheme.finalOutput();				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else if (arg1.equalsIgnoreCase("-r")) {// random generator case
			try {
				int limit = Integer.parseInt(args[1]);
				int density = Integer.parseInt(args[2]);				
				randomModeGraph(limit, density); //graph generation
				SimpleScheme ss = new SimpleScheme();
				FheapScheme fs = new FheapScheme();
				
				fs.msTree(limit, vertexGroup);			
				//calling the two functions and measuring performances
				ss.msTree(limit, vertexGroup);
				
				ss.finalOutput();
				fs.finalOutput();				
				
			} catch (NumberFormatException numFormat) {  //to check for number input
				System.out.println("Invalid Input - " + numFormat.getMessage());			 
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} else {
			System.out.println("Please enter valid input");
		}
	}

}
