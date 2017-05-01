package vadrevua;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GraphMaker {
	private int nodes, edges, removeNodes;
	private static ArrayList<ArrayList<Integer>> adjacencyList;
	private static final String filename = "cnp.in";

	public GraphMaker(){
		this.nodes = 1;
		this.edges = 1;
		this.removeNodes = 0;
	}

	public GraphMaker(int V, int E, int R) { //Constructor
		this.nodes = V;
		this.edges = E;
		this.removeNodes = R;
		this.setAdjacencyList(new ArrayList<ArrayList<Integer>>());
	}


	public static GraphMaker getGraph(){

		try { //reading in from file called cnp.in and making Adj. List
			Scanner scan = new Scanner(new File(filename.trim()));	
			scan.useDelimiter(" |\\r|\\n");
			int nodes = Integer.parseInt(scan.next());
			int edges = Integer.parseInt(scan.next());
			int removableNodes = Integer.parseInt(scan.next()); 
			GraphMaker graph = new GraphMaker(nodes, edges, removableNodes);
			for(int x = 0; x<nodes; x++){
				getAdjacencyList().add(new ArrayList<Integer>());
			}
			while(scan.hasNext()){
				String line = scan.next();
				int firstNode = Integer.parseInt(scan.next());
				int secondNode = Integer.parseInt(scan.next());
				firstNode -= 1;
				
				getAdjacencyList().get(firstNode).add(secondNode);	
			}
			return graph;
		}catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			e.printStackTrace();
		}
		return null;
	}

	@Override //Overriding toString()
	public String toString(){
		String s = "";
		for(int v=0; v<this.nodes; v++){
            s += v+1 + ": " + GraphMaker.getAdjacencyList().get(v)+"\n";
        }
		return s;
		
	}
	
	//Getters and Setters
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
	public static ArrayList<ArrayList<Integer>> getAdjacencyList() {
		return adjacencyList;
	}

	public static void setAdjacencyList(ArrayList<ArrayList<Integer>> adjacencyList) {
		GraphMaker.adjacencyList = adjacencyList;
	}
	//end getters and setters
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException{
		GraphMaker graph = getGraph();
		String s = graph.toString();
		System.out.println(s);
	}



}