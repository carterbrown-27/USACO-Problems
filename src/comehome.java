/*
ID: carter.2
TASK: comehome
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class comehome {
	
	// Solution for Section 2.4: "Problem 32_Bessie Come Home"
	static final int PASTURES = 52;
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();

		BufferedReader in;
		String file = "comehome.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		// [a-z]
		// boolean[] cows = new boolean[PASTURES];
		int[][] edges = new int[PASTURES][PASTURES];
		
		int P = Integer.parseInt(in.readLine());
		StringTokenizer st;
		for(int i = 0; i < P; i++) {
			st = new StringTokenizer(in.readLine());
			char ac = st.nextToken().charAt(0);
			int a = letterToInt_52(ac);
			// if(ac < 'Z') cows[a] = true; 
			
			char bc = st.nextToken().charAt(0);
			int b = letterToInt_52(bc);
			// if(bc < 'Z') cows[b] = true;
			
			int d = Integer.parseInt(st.nextToken());
			
			if(edges[a][b] == 0 || d < edges[a][b]) {
				edges[a][b] = d;
				edges[b][a] = d;				
			}
			
			// System.out.println(a + " " + b + " " + d);
		}
		in.close();
		
		// dijkstra from barn
		int[] dij = dijkstra(edges,letterToInt_52('Z'));
		
		int bestCow = -1;
		int len = Integer.MAX_VALUE;
		
		// from A -> Y
		for(int i = 26; i < dij.length - 1; i++) {
			// if(!cows[i]) continue;
			if(dij[i] < len) {
				len = dij[i];
				bestCow = i;
			}
		}
		
		String output = ((char) ('A' + bestCow - 26)) + " " + len;
		System.out.println(output);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("comehome.out")));
		out.println(output);
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}
	
	public static int[] dijkstra(int[][] edges, int src) {
		int[] dist = new int[edges.length];
		boolean[] inPath = new boolean[edges.length];
		
		for(int i = 0; i < dist.length; i++) {
			dist[i] = Integer.MAX_VALUE;
		}
		
		dist[src] = 0;
		
		for(int i = 0; i < edges.length; i++) {
			// node to look at, always starts at src
			int minDist = Integer.MAX_VALUE;
			int a = -1;
			
			for(int x = 0; x < dist.length; x++) {
				// if not already looked at, and shorter
				if(!inPath[x] && dist[x] < minDist) {
					minDist = dist[x];
					a = x;
				}
				
				if(a < 0) continue;
				
				inPath[a] = true;
				
				for(int b = 0; b < dist.length; b++) {
					// if not in path, and the edge between a&b exists
					if(!inPath[b] && edges[a][b] > 0) {
						// new node distance is the smallest between the previously stored value & the dist to a + the edge
						dist[b] = Math.min(dist[b], dist[a] + edges[a][b]);
					}
				}
			}
		}
		
		return dist;
	}
	
	public static int letterToInt_52(char c) {
		if(c >= 'a') {
			return (int) c - 'a';
		}else {
			return (int) c - 'A' + 26;
		}
	}
}
