/*
ID: carter.2
LANG: JAVA
TASK: butter
 */
import java.io.*;
import java.util.*;
public class butter {
	// Solution for Section 3.2: "Sweet Butter"
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		
		BufferedReader in;
		String file = "butter.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(st.nextToken());
		int P = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		
		int[] cows = new int[P];
		
		for(int i = 0; i < N; i++) {
			cows[Integer.parseInt(in.readLine()) - 1]++;
		}
		
		// all edges are non-zero, therefore 0 = inf.
		int[][] edges = new int[P][P];
		for(int i = 0; i < C; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken());
			
			edges[a][b] = d;
			edges[b][a] = d;
			
			// System.out.printf("%d,%d,%d\n",a,b,d);
		}
		
		// all set to inf except when x=y (same point, no dist).
		final int INF = 1000000000;
		int[][] paths = new int[P][P];
		for(int i = 0; i < P; i++) {
			for(int j = 0; j < P; j++) {
				if(edges[i][j] == 0 && i!=j) edges[i][j] = INF;
				paths[i][j] = edges[i][j];
			}
		}
		
		// Floyd-Warshall ... P^3.
		for(int k = 0; k < P; k++) {
			for(int i = 0; i < P; i++) {
				for(int j = 0; j < P; j++) {
					paths[i][j] = Math.min(paths[i][j], paths[i][k] + paths[k][j]);
				}
			}
		}
		
		int min = Integer.MAX_VALUE;
		for(int src = 0; src < P; src++) {
			int sum = 0;
			for(int i = 0; i < P; i++) {
				sum += cows[i]*paths[src][i];
				// System.out.println(paths[src][i]);
			}
			// System.out.println("===");
			min = Math.min(sum, min);
		}
		
		System.out.println(min);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("butter.out")));
		out.println(min);
		out.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}
}
