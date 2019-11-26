package section_1;
/*
ID: carter.2
LANG: JAVA
TASK: crypt1
*/
import java.io.*;
import java.util.*;

public class crypt1 {
	
	// Solution for the problem "Prime Cryptarithm"
	public static void main(String[] args) throws IOException {
		BufferedReader in;
		String file = "crypt1.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}	
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("crypt1.out")));
		
		int N = Integer.parseInt(in.readLine());
		StringTokenizer st = new StringTokenizer(in.readLine());
		in.close();
		
		int[] digits = new int[N];
		
		int n = 0;
		while(st.hasMoreTokens()) {
			digits[n] = Integer.parseInt(st.nextToken());
			// System.out.println(digits[n]);
			n++;
		}
		
		Arrays.sort(digits);
		
		ArrayList<Integer> abcs = new ArrayList<Integer>();
		
		// build all abcs
		for(int a: digits) {
			for(int b: digits) {
				for(int c: digits) {
					abcs.add(a * 100 + b * 10 + c);
				}
			}
		}
		
		
		// if the smallest digit is too big to *= w/ abc, cull.
		
		
		// System.out.println(abcs.length);
		for(int i = 0; i < abcs.size(); i++) {
			// System.out.println(abcs.get(i));
			
			if(abcs.get(i) * digits[0] >= 1000) {
				abcs.remove(i);
				i--;
			}
		}
		
		//for(int i: abcs) {
		//	System.out.println(i);
		//}
		//System.out.println("=step 1 done=");
		// works to here
		
		
		// get all abc*e vals
		Map<Integer,ArrayList<Integer>> e_vals = new HashMap<Integer,ArrayList<Integer>>();
		
		for(int i: abcs) {
			ArrayList<Integer> arr = new ArrayList<Integer>();
			for(int e: digits) {
				int f = i*e;
				if(f < 1000 && isEligible(f,digits)) {
					arr.add(e);
				}
			}
			e_vals.put(i, arr);
		}
		
		// get all abc*d vals
		
		Map<Integer,ArrayList<Integer>> d_vals = new HashMap<Integer,ArrayList<Integer>>();
		
		for(int i: abcs) {
			ArrayList<Integer> arr = new ArrayList<Integer>();
			for(int d: digits) {
				int f = i*d;
				if(f < 1000 && isEligible(f,digits)) {
					arr.add(d);
				}
			}
			d_vals.put(i, arr);
		}
		
		int sols = 0;
		// check for sol'ns by adding i*e + i*d*10 and running is Eligible
		for(int i: abcs) {
			ArrayList<Integer> es = e_vals.get(i);
			ArrayList<Integer> ds = e_vals.get(i);
			
			for(int e: es) {
				for(int d: ds) {
					int f1 = i*e;
					int f2 = i*d*10;
					int total = f1+f2;
					if(isEligible(total,digits)) {
						System.out.println(i+"x"+d+""+e);
						sols++;
					}
				}
			}
		}
		System.out.println(sols);
		out.println(sols);
		out.close();
		
	}
	
	public static boolean isEligible(int x, int[] digits) {
		String s = String.valueOf(x);
		char[] arr = s.toCharArray();
		
		// for each digit, check
		for(char c: arr) {
			int n = Character.getNumericValue(c);
			boolean e = false;
			// find match
			for(int d: digits) {
				if(n == d) e = true;
			}
			
			if(!e) return false;
		}
		
		return true;
	}
}
