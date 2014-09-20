// Implementation of Fibonacci heap for Prim's algorithm

public class FibonacciHeap {

	Node root = null;
	int nodesNo;
	
	// to check if heap is empty
	public boolean isEmpty() {
		if (nodesNo == 0)
			return true;
		return false;
	}

	
	public void insert(Node n) { //insertion in heap
		if (n == null)
			return;
		
		if (this.root == null) { //for insertion of first node
			this.root = n;
			this.root.leftSib = this.root;
			this.root.rightSib = this.root;
			this.root.child = null;
		} else { // else adding node to the top level to the 
				// right of the root
			Node temp = this.root.rightSib;
			this.root.rightSib = n;
			n.rightSib = temp;
			temp.leftSib = n;
			n.leftSib = this.root;
		}
		n.child = null;
		n.parent = null;
		n.deg = 0;
		n.childCut = false;
		if (this.root.data > n.data)// if root value is greater than
			this.root = n;         // new node value, then update root
		nodesNo++;
	}
	
	
	public Node removeMin() { //for min value
		if (nodesNo == 0)
			return null;
		Node temp = this.root;
		if (this.root.leftSib != this.root) {
			
			this.root.leftSib.rightSib = this.root.rightSib; //if root has siblings
			this.root.rightSib.leftSib = this.root.leftSib;
			Node temp1 = this.root.rightSib;
			
			updateParentNull(); //update parents of top level nodes
			meld(temp1, this.root.child);
		} else {			// for no siblings
			updateParentNull();
			meld(null, this.root.child);
		}
		// pairwise combine after removeMin operation
		pairwiseCombine();
		nodesNo--;
		return temp;
	}

	
	public void updateParentNull() {  //for making child's parent pointers null
		if (this.root.child != null) {
			Node rChild = this.root.child;
			do {
				rChild.parent = null;
				rChild = rChild.rightSib;
			} while (rChild != this.root.child);
		}
	}

	
	private void meld(Node node1, Node node2) { //meld operation
		if (node1 == null && node2 == null) {
			this.root = null;
			return;
		}
		if (node1 == null) {
			this.root = getMin(node2);
			return;
		}
		if (node2 == null) {
			this.root = getMin(node1);
			return;
		}
		Node temp1 = node1.rightSib;
		Node temp2 = node2.leftSib;
		node1.rightSib = node2;
		node2.leftSib = node1;
		temp1.leftSib = temp2;
		temp2.rightSib = temp1;
		node1.parent = null;
		node2.parent = null;
		this.root = getMin(node1);
	}

	
	private Node getMin(Node node) { //for min at each level
		Node min = node;
		Node temp = node.rightSib;

		while (temp != node) {
			if (min.data > temp.data)
				min = temp;
			temp = temp.rightSib;
		}
		return min;
	}

	private void pairwiseCombine(){
		
		Node[] a = new Node[nodesNo]; //top level nodes
		Node temp = this.root;
		Node[] b = new Node[nodesNo];
		int index = 0;

		if (temp == null)
			return;
		do {
			b[index++] = temp;
			temp = temp.rightSib;
		} while (!temp.equals(this.root));

		for (int i = 0; i < b.length; i++) {
			if (b[i] == null) {
				break;
			}
			b[i].rightSib = b[i].leftSib = b[i];
			b[i].parent = null;
			
			while (a[b[i].deg] != null) {
				Node temp2 = a[b[i].deg];
				a[b[i].deg] = null;
				
				b[i] = mergeNodes(b[i], temp2); //if of the same degree, then combine
			}
			a[b[i].deg] = b[i];
		}

		this.root = null;
		for (int i = 0; i < a.length; i++) {
			if (a[i] != null) {
				meld(this.root, a[i]);
			}
		}
	}

	private Node mergeNodes(Node node1, Node node2) {
		if (node1 == null && node2 == null) {
			return null;
		} else if (node1 == null && node2 != null) {
			node2.rightSib = node2;
			node2.leftSib = node2;
			return node2;
		} else if (node1 != null && node2 == null) {
			node1.rightSib = node1;
			node1.leftSib = node1;
			return node1;
		}

		node1.childCut = node2.childCut = false;
		
		if (node1.data > node2.data) { //to check which node will become the parent 
			if (node2.child == null) {
				node2.child = node1;   //smaller will be parent of bigger
				node1.parent = node2;
			} else {
  			   
				Node temp = node2.child.rightSib;
				node2.child.rightSib = node1;
				node1.leftSib = node2.child;
				node1.rightSib = temp;
				temp.leftSib = node1;
				node1.parent = node2;
			}
			node2.deg+=1;
			node2.rightSib = node2.leftSib = node2;
			return node2;
		} else {
			if (node1.child == null) {
				node1.child = node2;
				node2.parent = node1;
			} else {
				Node temp = node1.child.rightSib;
				node1.child.rightSib = node2;
				node2.leftSib = node1.child;
				node2.rightSib = temp;
				temp.leftSib = node2;
				node2.parent = node1;
			}
			node1.deg+=1;
			node1.rightSib = node1.leftSib = node1;
			return node1;
		}
	}
}
