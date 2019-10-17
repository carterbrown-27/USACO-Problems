/*
ID: carter.2
TASK: zerosum
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class zerosum {

	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		
		BufferedReader in;
		String file = "zerosum.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		int N = Integer.parseInt(in.readLine());
		in.close();
		
		// generates all +/-/space values.
		genCombos("",N);
		
		ArrayList<String> solutions = new ArrayList<String>();
		for(String c: combos) {
			/*
			 * $s = string with spaces between each individual "number" (series of digits combined)
			 * $pMStr = string with only the +/-'s, will always have N elements, where N is the number
			 * of tokens in $s minus 1.
			 * $finalStr = string formatted for output purposes, e.g. "1+23-4" for s="1 23 4" pMStr="+-"
			 */
			String s = "";
			String pMStr = "";
			String finalStr = "";
			
			// for each # from 1 to N, add it to the sequence, then if # < N, add the operand after it.
			for(int x = 1; x <= N; x++) {
				s+=x;
				finalStr+=x;
				
				if(x==N) continue;
				
				char ch = c.charAt(x-1);
				if(ch != ' ') {
					s += " ";
					pMStr += ch;
					finalStr += ch;
				}else {
					finalStr += " ";
				}
			}
			
			// split the string into the individual numbers
			// this is much easier than keeping track of place values for digit concatenation
			StringTokenizer st = new StringTokenizer(s);
			int total = 0;
			int counter = 0;
			while(st.hasMoreTokens()) {
				int x = Integer.parseInt(st.nextToken());
				if(counter == 0 || pMStr.charAt(counter-1) == '+') {
					total += x;
				}else {
					total -= x;
				}
				counter++;
			}
			
			if(total == 0) {
				solutions.add(finalStr);
			}
		}
		
		// question asks for ASCII sorted values, which is covered by the default string comparator.
		Collections.sort(solutions);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("zerosum.out")));
		
		for(String s: solutions) {
			System.out.println(s);
			out.println(s);
		}
		
		out.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}
	
	// recursively gen all +/-/space combos, this is fine because N<=9.
	public static Set<String> combos = new HashSet<String>();
	public static void genCombos(String s, int N) {
		if(s.length() == N-1) {
			if(!combos.contains(s)) combos.add(s);
			return;
		}
		genCombos(s+'+',N);
		genCombos(s+'-',N);
		genCombos(s+' ',N);
	}
}
