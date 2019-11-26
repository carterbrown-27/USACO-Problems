package section_3;
/*
ID: carter.2
TASK: agrinet
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class agrinet {

	public static void main(String[] args) throws IOException{
		long first_time = System.nanoTime();
		BufferedReader in;
		String file = "agrinet.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		int N = Integer.parseInt(in.readLine());
		
		int[][] edges = new int[N][N];
		
		for(int x = 0; x < N; x++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int y = 0;
			do {
				int v = Integer.parseInt(st.nextToken());
				
				edges[x][y] = v;
				
				if(!st.hasMoreTokens() && y != N-1) {
					st = new StringTokenizer(in.readLine());
				}
				y++;
			} while(st.hasMoreTokens());
		}
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [I]");
		
		
		// Prim's Algorithm
		
		boolean[] inTree = new boolean[N];
		
		// distance from each node to the tree
		int[] distances = new int[N];
		// node on tree from which the shortest distance occurs
		int[] sourceNodes = new int[N];
		
		// initially, all distances are infinity, no nodes have source nodes, and none are in the tree
		for(int i = 0; i < N; i++) {
			distances[i] = Integer.MAX_VALUE;
			sourceNodes[i] = -1;
			inTree[i] = false;
		}
		
		// add node 0 to the tree
		inTree[0] = true;
		int treeSize = 1;
		int treeCost = 0;
		
		// update neighbours to node 0
		for(int i = 0; i < N; i++) {
			// don't need "if(edges[0][i] < Integer.MAX_VALUE){...}", as all nodes are connectable
			distances[i] = edges[0][i];
			sourceNodes[i] = 0;
		}
		
		// continue until subtree is a spanning tree
		while(treeSize < N) {
			// find closest node not in the tree
			// minNode should always exist if all nodes connectable.
			int minD = Integer.MAX_VALUE;
			int minNode = -1;
			for(int i = 0; i < N; i++) {
				if(!inTree[i] && distances[i] < minD) {
					minD = distances[i];
					minNode = i;
				}
			}
			
			// connect minNode to tree
			treeSize++;
			treeCost += distances[minNode];
			inTree[minNode] = true;
			
			// update minNode's neighbours
			for(int j = 0; j < N; j++) {
				/*  if the edge from an existing node in the tree is lesser than
				 *  the previously best known edge from the smaller tree to this node, update */
				if(edges[minNode][j] < distances[j]) {
					distances[j] = edges[minNode][j];
					sourceNodes[j] = minNode;
				}
			}
		}
		
		System.out.println(treeCost);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("agrinet.out")));
		out.println(treeCost);
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}
}
