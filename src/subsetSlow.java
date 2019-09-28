/*
ID: carter.2
TASK: subset
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class subsetSlow {
	
	// reversed Recursive call and it works faster, still needs more
	// N....time
	// 7....4ms
	// 15...6ms
	// 24...49ms
	// 31...4200ms
	// 32...9402ms
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
		
		ArrayList<Integer> set = new ArrayList<Integer>();
		
		for(int i = 1; i <= N; i++) {
			set.add(i);
		}
		
		int sigma = sumOfCollection(set);
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [A]");
		if(sigma % 2 == 0) {
			int targetSum = sigma/2;
			recur(N,N,sigma,targetSum);			
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("subset.out")));
		out.println(solutionsCount);
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}
	
	public static int solutionsCount = 0;
	// sums, solutions
	public static Set<Integer> invalidSums = new HashSet<Integer>();
	
	// TODO: avoid duplicates. i.e. 2,3,4,5 + 1,6,7 precludes 1,6,7 + 2,3,4,5
	public static void recur(int N, int x, int sum, int targetSum) {
		// more in b than a	
		if(sum == targetSum) {
			solutionsCount++;
			return;
		}
		
		// either x<=1 i.l.o. x<1 exists or you remove the second call for x==N
		if(x <= 1) {
			return;
		}
		
		// next Element
		recur(N,x-1,sum,targetSum);
		
		// remove current
		if(sum - x >= targetSum) {			
			recur(N,x-1,sum-x,targetSum);
		}
	}
	
	public static int sumOfCollection(Collection<Integer> c) {
		int sum = 0;
		for(int i: c) {
			sum+=i;
		}
		return sum;
	}
	
//	public static void printCollection(Collection<Integer> c) {
//		for(int i: c) {
//			System.out.print(i+" ");
//		}
//	}
}
