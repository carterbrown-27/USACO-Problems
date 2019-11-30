/*
ID: carter.2
LANG: JAVA
TASK: stamps
 */
import java.io.*;
import java.util.*;

public class stamps {

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
		
		// DP, works for case 1-9
		// amt, num of stamps
		HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
		// 0c, 0s
		map.put(0, 0);
		
		final int maxStamp = stamps.get(0);
		
		for(Integer stamp: stamps) {
			for(int i = 0; i <= (maxStamp*K)-stamp; i++) {
				int v = map.getOrDefault(i, -1);
				// update next value with smallest amt to get there.
				if(v >= 0 && v < K) {
					map.put(i+stamp,Math.min(map.getOrDefault(i+stamp,Integer.MAX_VALUE),v+1));
				}
			}
		}
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [A]");
		
		// DEBUG
//		for(Integer i: map.keySet()) {
//			System.out.println(i+"::"+map.get(i));
//		}
		
		int maxStreak = 0;
		int streak = 0;
		for(int i = 1; i <= maxStamp*K; i++) {
			if(map.containsKey(i)) {
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
