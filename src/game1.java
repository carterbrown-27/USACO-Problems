/*
ID: carter.2
LANG: JAVA
TASK: game1
 */
import java.io.*;
import java.util.*;
public class game1 {
	// Solution for Section 3.3: "A Game". With help from: http://wilanw.blogspot.com/2010/01/usaco-training-gateway-game.html
	// (Had the DP part but changed my initial solution to use the recursive memoisation outlined above)
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();

		BufferedReader in;
		String file = "game1.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}

		int N = Integer.parseInt(in.readLine());

		S = new int[N];
		StringTokenizer st = new StringTokenizer(in.readLine());

		int sum = 0;
		for(int i = 0; i < N; i++) {
			if(!st.hasMoreTokens()) {
				st = new StringTokenizer(in.readLine());
			}
			S[i] = Integer.parseInt(st.nextToken());
			sum += S[i];
		}

		DP = new int[N][N];
		for(int x = 0; x < N; x++) {
			for(int y = 0; y < N; y++) {
				DP[x][y] = x==y ? S[x] : -1;
			}
		}

		int p1_score = solve(0,N-1);
		int p2_score = sum - p1_score;
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("game1.out")));
		System.out.println(p1_score);
		System.out.println(p2_score);
		out.println(p1_score+" "+p2_score);
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}
	
	// this part was using the solution posted.
	static int[][] DP = new int[0][0];
	static int[] S = new int[0];
	public static int solve(int st, int e) {
		if(st>e) return 0;
		if(DP[st][e] != -1) return DP[st][e];
		
		// except this part:
		int x = Math.max(
					Math.min(solve(st+2,e),solve(st+1,e-1)) + S[st],
					Math.min(solve(st,e-2), solve(st+1,e-1)) + S[e]
				);
		
		DP[st][e] = x;
		return x;
	}
}	
