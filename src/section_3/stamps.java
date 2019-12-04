/*
ID: carter.2
LANG: JAVA
TASK: stamps
 */
import java.io.*;
import java.util.*;

public class stamps {

	// Solution to Section 3.1: "Stamps"
	public static void main(String[] args)  throws IOException {
		long first_time = System.nanoTime();
		BufferedReader in;
		String file = "stamps.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		int K = Integer.parseInt(st.nextToken());
		
		@SuppressWarnings("unused")
		int N = Integer.parseInt(st.nextToken());
		
		ArrayList<Integer> stamps = new ArrayList<Integer>();
		// for each line
		String line = in.readLine();
		while(line!=null) {
			st = new StringTokenizer(line);
			// for each stamp value on line
			while(st.hasMoreTokens()) {
				stamps.add(Integer.parseInt(st.nextToken()));
			}
			
			line = in.readLine();
		}
		
		Collections.sort(stamps,Collections.reverseOrder());
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [I]");
		
		// DP
		
		final int maxStamp = stamps.get(0);
		// amt, num of stamps
		int[] opts = new int[maxStamp*K+1];
		opts[0] = 0;
		
		for(Integer stamp: stamps) {
			for(int i = 0; i <= (maxStamp*K)-stamp; i++) {
				int v = opts[i];
				// update next value with smallest amt to get there.
				if((v > 0 || i == 0) && v < K) {
					if(opts[i+stamp] == 0) {
						// no value
						opts[i+stamp] = v+1;
					}else {
						// existing value
						opts[i+stamp] = Math.min(opts[i+stamp], v+1);
					}
				}
			}
		}
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [A]");
		
		// DEBUG
//		for(int i = 0; i < opts.length; i++) {
//			System.out.print(i+"::"+opts[i]+"\t");
//			if(i%10==0) System.out.println();
//		}
		
		int maxStreak = 0;
		int streak = 0;
		for(int i = 1; i <= maxStamp*K; i++) {
			if(opts[i] > 0) {
				streak++;
			}else {
				maxStreak = Math.max(streak, maxStreak);
				streak = 0;
			}
		}
		maxStreak = Math.max(streak, maxStreak);
		
		System.out.println(maxStreak);

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("stamps.out")));
		out.println(maxStreak);
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}

}
