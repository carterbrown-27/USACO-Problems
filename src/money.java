/*
ID: carter.2
TASK: money
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class money {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		long first_time = System.nanoTime();

		BufferedReader in;
		String file = "money.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}

		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int V = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());

		// new input format
		ArrayList<Integer> coins = new ArrayList<Integer>(V);
		String line = in.readLine();
		while(line!=null) {
			st = new StringTokenizer(line);
			while(st.hasMoreTokens()) {
				coins.add(Integer.parseInt(st.nextToken()));
			}
			line = in.readLine();
		}
		
		Collections.sort(coins);

		long[] ways = new long[N+1];
		ways[0] = 1;

		/*
		 * for each coin value, go through all the sums, working upwards, calc ways to make number
		 * 
		 */
		
		// make sure that c is outer loop, otherwise results compound.
		for(int c: coins) {
			for(int x = 1; x <= N; x++) {
				if(x < c) continue;
				// since x-c is the solution - the last coin added (c), add the # of ways (ways[x-c])
				// to ways[x], this is will give f(x) = f(x-coin1) + f(x-coin2) + ... + f(coinN) 
				ways[x] += ways[x-c];
				// [DEBUG] System.out.println("c = "+c+" $"+x+" "+ways[x]);
			}
		}

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("money.out")));
		System.out.println(ways[N]);
		out.println(ways[N]);
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}
}
