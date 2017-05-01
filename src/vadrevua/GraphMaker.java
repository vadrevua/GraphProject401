package vadrevua;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class GraphMaker {
	private int nodes, edges, removeNodes;
	private static boolean[][] adjacencyMatrix;
	private static final String filename = "cnp.in";

	public GraphMaker(){
		this.nodes = 1;
		this.edges = 1;
		this.removeNodes = 0;
	}

	public GraphMaker(int V, int E, int R) {
		this.nodes = V;
		this.edges = E;
		this.removeNodes = R;
		this.adjacencyMatrix = new boolean[V][V];
	}

	public int getNodes() {
		return nodes;
	}

	public void setNodes(int nodes) {
		this.nodes = nodes;
	}

	public int getEdges() {
		return edges;
	}

	public void setEdges(int edges) {
		this.edges = edges;
	}

	public int getRemoveNodes() {
		return removeNodes;
	}

	public void setRemoveNodes(int removeNodes) {
		this.removeNodes = removeNodes;
	}


	public static GraphMaker getGraph(){

		try {
			Scanner scan = new Scanner(new File(filename.trim()));
			scan.useDelimiter(" |\\r|\\n");
			int nodes = Integer.parseInt(scan.next());
			int edges = Integer.parseInt(scan.next());
			int removableNodes = Integer.parseInt(scan.next()); 
			GraphMaker graph = new GraphMaker(nodes, edges, removableNodes);
			while(scan.hasNext()){
				String line = scan.next();
				int firstNode = Integer.parseInt(scan.next());
				int secondNode = Integer.parseInt(scan.next());
				graph.adjacencyMatrix[firstNode-1][secondNode-1] = true;
				graph.adjacencyMatrix[secondNode-1][firstNode-1] = true;

			}
			return graph;

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			e.printStackTrace();
		}
		return null;


	}
	public static void main(String[] args) throws FileNotFoundException{
		GraphMaker graph = getGraph();
		int count = 0;
		for(int x = 0; x<graph.getNodes(); x++){
			for(int y = 0; y<graph.getNodes(); y++){
				count++;
				System.out.print(graph.adjacencyMatrix[x][y] + " ");
				if(count%7 == 0){System.out.println("");}
			}
		}

		//GraphMaker gm = new GraphMaker();

		//System.out.println();
	}

}