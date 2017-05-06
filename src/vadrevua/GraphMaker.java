package vadrevua;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
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
			for(int x = 0; x<=nodes; x++){
				getAdjacencyList().add(new ArrayList<Integer>());
			}
			while(scan.hasNext()){
				String line = scan.next();
				int firstNode = Integer.parseInt(scan.next());
				int secondNode = Integer.parseInt(scan.next());
				firstNode -=1;
				secondNode -=1;
				getAdjacencyList().get(firstNode).add(secondNode);
				getAdjacencyList().get(secondNode).add(firstNode);
				}
			return graph;
		}catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			e.printStackTrace();
		}
		return null;
	}

	public int getOutDegree(int n){ //gets how many edges V contains
		return (adjacencyList.get(n-1)).size();
	}

	public int getInDegree(int n){ //gets number of nodes that have edged connected to V
		int count = 0;
		for(int x = 0; x < getNodes(); x++){
			if(adjacencyList.get(x).contains(n)){
				count++;
			}
		}
		return count;
	}

	public void removeNode(int x){
		for(int n = 0; n < getNodes(); n++){
			if(adjacencyList.get(n).contains(x)){//removes all edges to x
				int index = adjacencyList.get(n).indexOf(x);
				adjacencyList.get(n).remove(index);
			}
		}
		int sizeOfRemove = adjacencyList.get(x).size();
		for(int y = sizeOfRemove-1; y >= 0; y--){//"removes" x from graph. Just cuts all connections
			adjacencyList.get(x).remove(y);
		}
	}

	public static void dfs(GraphMaker graph){
		boolean [] visited = new boolean[adjacencyList.size()];
		graph.greedyRemove();
		dfs_Actual(adjacencyList, visited, 0);
	}
	public static void dfs_Actual(ArrayList<ArrayList<Integer>> adjList, boolean[] visited, int v){
		visited[v] = true;
		//System.out.print(v + " ");
		for(int x : adjacencyList.get(v)){
			if(!visited[x]){
				dfs_Actual(adjList, visited, x);
			}
		}
		
	}
	
	public int pairWiseConnect(int numNodes){
		numNodes = numNodes*(numNodes-1);
		numNodes = numNodes/2;
		return numNodes;
	}
	
	public void greedyRemove(){
		int numRemove = this.removeNodes;
		int[] removedNums = new int[numRemove];
		int counter = 0;
		while (numRemove >0){
			int mostNodes = 0;
			int numContained = -1;
			for(int x = 0; x<getNodes(); x++){
				if(adjacencyList.get(x).size() >= mostNodes){
					mostNodes = adjacencyList.get(x).size();
					numContained = x;
				}
			}
			if(numContained == -1){
				System.out.println("Something wrong in GreedyRemove");
			}
			removeNode(numContained);
			removedNums[counter] = numContained;
			numRemove--;
			counter++;
		}
		try {
			outputToCNP(removedNums);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void outputToCNP(int[] removedNodes) throws FileNotFoundException{

//Error must be in//		int newNodes = adjacencyList.size() - 1 - removeNodes;
//first four lines//		String outputString = "";
////		String inForString = "\n";
////		int x = pairWiseConnect(newNodes);
//		System.out.println("Number of Pairwise Connections " + x);
//		outputString = "" + x;
//		for(int i = 0; i<removedNodes.length; i++){
//			System.out.println("Removed "+removedNodes[i] + " from Graph");
//			inForString += removedNodes[i] + " ";
//		}
//		outputString += inForString;
//		try (PrintStream out = new PrintStream(new FileOutputStream("cnp.out"))) {
//		    out.print(outputString);
//		}
	}
	
	@Override //Overriding toString()
	public String toString(){
		String s = "";
		for(int v=0; v<this.nodes; v++){
			s += v + " (in text file " + (v+1) +")" + ": " + GraphMaker.getAdjacencyList().get(v)+"\n";
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
		dfs(graph);
		//System.out.println(s);
		//graph.greedyRemove();
		//s = graph.toString();
		//System.out.println(s);
		
	}



}