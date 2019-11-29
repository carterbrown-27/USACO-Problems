package section_3;
/*
ID: carter.2
LANG: JAVA
TASK: inflate
 */
import java.io.*;
import java.util.*;

public class inflate {	
	// Solution to Section 3.1: "Score Inflation"
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();

		BufferedReader in;
		String file = "inflate.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}

		StringTokenizer st = new StringTokenizer(in.readLine());
		int M = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());

		// cost, score. for any given cost only accept max scores
		HashMap<Integer,Integer> classes = new HashMap<Integer,Integer>();
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(in.readLine());
			int s = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			// keeps new if max
			int prev = classes.getOrDefault(c,0);
			if(s > prev) {
				classes.put(c, s);
			}
			
			// note: could put dp algorithm here
		}
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [I]");
		
		// Dynamic Programming solution ... O(n*M)
		ArrayList<Integer> classList = new ArrayList<Integer>(classes.keySet());
		
		// 0...M
		int[] opts = new int[M+1];

		for(Integer ck: classList) {
			int cv = classes.get(ck);
			for(int pos = 0; pos < M; pos++) {
				if(opts[pos] == 0 && pos > 0) continue;
				int newK = pos+ck;
				
				if(newK > M) continue;
				int newV = opts[pos]+cv;
				int putV = Math.max(opts[newK], newV);
				// System.out.println(newK+ " :: "+ putV);
				opts[newK] = putV;
			}
		}
		
		// System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [B]");
		// find best value 0...M
		int answer = 0;
		for(int i: opts) {
			answer = Math.max(answer, i);
		}
		
		System.out.println(answer);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("inflate.out")));
		out.println(answer);
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}
}