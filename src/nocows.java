/*
ID: carter.2
TASK: nocows
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class nocows {

	public static void main(String[] args) throws IOException {
		BufferedReader in;
		String file = "nocows.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("nocows.out")));

		long[][] trees = new long[N+1][K+1];
		
		for(int i = 1; i <= K; i++) {
			trees[0][i] = 1;
			trees[1][i] = 1;
		}
		
		System.out.println(N+" "+K);
		
		for(int k = 1; k <= K; k++) {
			//System.out.println("==="+k+"===");
			// start at the min possible, go up until the max
			for(int n = 1; n <= N; n+=2) {
				//System.out.println(n+" & "+k);
				for (int l = 1; l < n; l+=2) {
					//System.out.println(l+" | "+(n-l));
					long left = trees[l][k - 1];
					//System.out.println(left);
					
					long right = trees[n - l - 1][k - 1];
					//System.out.println(right);
					
					long amt = left * right;
					//System.out.println("= "+amt);
					trees[n][k] += amt;
				}
				trees[n][k] %= 9901;
			}
		}
		
		// all the trees made from the full length - those from k-1.
		long ans = (trees[N][K] - trees[N][K-1] + 9901) % 9901;
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
}
