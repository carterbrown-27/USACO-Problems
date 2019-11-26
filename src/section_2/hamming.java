package section_2;
/*
ID: carter.2
TASK: hamming
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class hamming {
	
	// Solution to "Problem 94_Hamming Codes"
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();

		BufferedReader in;
		String file = "hamming.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(st.nextToken());
		int B = Integer.parseInt(st.nextToken());
		int D = Integer.parseInt(st.nextToken());
		
		int highestInt = (int) Math.pow(2,B);
		String[] binaries = new String[highestInt];
		
		// generate binary strings for all ints with bit length B or less.
		for(int i = 0; i < highestInt; i++) {
			String binary = Integer.toBinaryString(i);
			String format = "%" + B + "s";
			String binaryF = String.format(format,binary).replaceAll(" ", "0");
			
			binaries[i] = binaryF;
		}
		
		recur(binaries, new HashSet<Integer>(), -1, N, D);
		
		if(solution == null) System.out.println("NULL");
		ArrayList<Integer> sortedSolution = new ArrayList<Integer>(solution);
		Collections.sort(sortedSolution);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hamming.out")));
		
		for(int i = 0; i < sortedSolution.size(); i++) {
			int u = sortedSolution.get(i);
			out.print(u);
			
			if(((i + 1) % 10 == 0) || i == sortedSolution.size() - 1) {
				out.println();
			}else {
				out.print(" ");
			}
		}
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}
	
	
	public static Set<Integer> solution;
	public static void recur(String[] binaries, Set<Integer> used, int last, int N, int D) {
		if(solution != null) {
			return;
		}
		
		if(used.size() == N) {
			solution = used;
			// System.out.println("solution");
			return;
		}
		
		for(int i = last+1; i < binaries.length; i++) {
			boolean flag = false;
			for(Integer u: used) {
				if(hammingDistance(binaries[i],binaries[u]) < D) {
					flag = true;
					break;
				}
			}
			if(!flag) {
				Set<Integer> newUsed = new HashSet<Integer>(used);
				newUsed.add(i);
				recur(binaries,newUsed,i,N,D);
			}
		}
	}
	
	// a.len must = b.len
	public static int hammingDistance(String a, String b) {
		int dist = 0;
		for(int i = 0; i < a.length(); i++) {
			if(a.charAt(i) != b.charAt(i)) dist++;
		}
		return dist;
	}
}
