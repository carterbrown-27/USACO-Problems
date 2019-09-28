/*
ID: carter.2
TASK: subset
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class subset {
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		
		BufferedReader in;
		String file = "subset.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		Integer N = Integer.parseInt(in.readLine());
		in.close();
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("subset.out")));
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [A]");
		
		/*
		 *  N
		 * ---
		 * \   
		 * /  n  == N*(N+1)/2
		 * ---
		 * n=1
		 */
		
		float sigma = N * (N+1) / 2;
		if(sigma % 2 == 0) {
			// divis by two means int division is safe
			int targetSum = (int) sigma/2;
			// amounts of sums for each number 1,2....N
			// long bc, integer overflows
			long[] sumsWays = new long[targetSum+1];
			sumsWays[0] = 1;
			
			for(int x = 1; x <= N; x++) {
				// sum of last Sx
				int max = x*(x-1)/2;
				
				// need this so that the numbers don't compound for each iteration
				long[] tempWays = Arrays.copyOf(sumsWays, sumsWays.length);
				// System.out.println(x+"--"+max);
				for(int p = 0; p <= max; p++) {
					if(p+x <= targetSum) {
						sumsWays[p+x]+=tempWays[p];						
					}else {
						break;
					}
				}
			}
		
			// div by 2 bc each way to make the targetSum has a complement S1 vs. S1C
			out.println(sumsWays[targetSum]/2);
			
		}else {
			// odd # = 0 solutions.
			out.println(0);
		}
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}
}
