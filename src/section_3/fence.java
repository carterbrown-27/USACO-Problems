package section_3;
/*
ID: carter.2
LANG: JAVA
TASK: fence
 */
import java.io.*;
import java.util.*;
public class fence {
	
	static int[][] edges = new int[500][500];
	static Set<Integer> nodes = new HashSet<Integer>();
	
	// Solution to Section 3.3: "Riding the Fences"
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();

		BufferedReader in;
		String file = "fence.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}

		int F = Integer.parseInt(in.readLine());
		StringTokenizer st;
		for(int i = 0; i < F; i++) {
			st = new StringTokenizer(in.readLine());
			// -1 adjusts the 1-500 range to 0-499.
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			edges[a][b]++;
			edges[b][a]++;
			nodes.add(a);
			nodes.add(b);
		}

		int lowOddDegNode = 500;
		int lowNonZero = 500;
		for(Integer i: nodes) {
			lowNonZero = Math.min(i, lowNonZero);
			int degree = 0;
			for(int j = 0; j < 500; j++) {
				degree += edges[i][j];
			}
			if(degree % 2 == 1 && i < lowOddDegNode) {
				lowOddDegNode = i;
			}
		}

		circuit = new ArrayList<Integer>();
		/*
		 *  If there's an odd node, start at the lowest of the two odd degree nodes
		 *  (because the problem states that there's >= 1 solutions, this means that if an odd degree node exists,
		 *  1, and only 1 other odd degree node in the graph, otherwise there would be no solutions.
		 *  If all nodes are even, then start at the lowest index node.
		 */
		findCircuit(lowOddDegNode < 500 ? lowOddDegNode : lowNonZero);
		Collections.reverse(circuit);

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fence.out")));
		// DEBUG: circuit.forEach(i -> System.out.println(i));
		circuit.forEach(i -> out.println(i));
		out.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}

	static ArrayList<Integer> circuit;
	public static void findCircuit(int node) {
		Set<Integer> neighbours = getNeighbours(node);
		while(!neighbours.isEmpty()) {
			int j = smallestInSet(neighbours);
			// delete edge
			edges[node][j]--;
			edges[j][node]--;
			findCircuit(j);
			
			neighbours = getNeighbours(node);
		}
		circuit.add(node+1);
	}

	public static HashSet<Integer> getNeighbours(int node) {
		HashSet<Integer> set = new HashSet<Integer>();
		for(int j: nodes) {
			if(edges[node][j] > 0) set.add(j);
		}
		return set;
	}
	
	public static int smallestInSet(Set<Integer> set){
		int smallest = Integer.MAX_VALUE;
		for(int i: set) {
			smallest = Math.min(i, smallest);
		}
		return smallest;
	}
}
