/*
ID: carter.2
TASK: lamps
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class lamps {
	
	// Solution for Section 2.2: Party Lamps
	public static void main(String[] args) throws IOException{
		BufferedReader in;
		String file = "lamps.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}

		int N = Integer.parseInt(in.readLine());
		int C = Integer.parseInt(in.readLine());

		StringTokenizer st = new StringTokenizer(in.readLine());

		Set<Integer> ons = new HashSet<Integer>();
		while(st.hasMoreTokens()) {
			int i = Integer.parseInt(st.nextToken());
			if(i==-1) break;
			ons.add(i);
		}

		Set<Integer> offs = new HashSet<Integer>();
		st = new StringTokenizer(in.readLine());
		while(st.hasMoreTokens()) {
			int i = Integer.parseInt(st.nextToken());
			if(i==-1) break;
			offs.add(i);
		}

		// -1: no reqs, 0: off, 1: on
		int[] neededConfig = new int[N];
		for(int i = 1; i <= N; i++) {
			int value = -1;
			if(ons.contains(i)) value = 1;
			if(offs.contains(i)) value = 0;
			neededConfig[i-1] = value;
		}	

		// 0b----, one bit per rule
		// 0 = even number of presses (incl. 0), as even presses is the same as no presses
		// 1 = odd number of presses
		ArrayList<String> ruleCombos = new ArrayList<String>();
		for(int i = 0; i < 16; i++) {
			// left padding
			String binary = Integer.toBinaryString(i);

			// see txt doc.
			int ones = 0;
			for(char c: binary.toCharArray()) {
				if(c == '1') ones++;
			}

			if(C < ones || C % 2 != ones % 2) continue;

			String format = "%" + 4 + "s";
			String binaryF = String.format(format,binary).replaceAll(" ", "0");
			ruleCombos.add(binaryF);
		}

		Set<Integer[]> solutions = new HashSet<Integer[]>();
		for(String combo: ruleCombos) {
			Integer[] states = new Integer[N];
			for(int pos = 0; pos < states.length; pos++) {
				int cState = 1;
				// rule 1
				if(combo.charAt(0) == '1') cState = ~cState & 1; // inverts it

				// rule 2
				if(combo.charAt(1) == '1') {
					// odd numbered = even position in array
					if(pos % 2 == 0) cState = ~cState & 1;
				}

				// rule 3
				if(combo.charAt(2) == '1') {
					// even numbered = odd position in array
					if(pos % 2 != 0) cState = ~cState & 1;
				}

				// rule 4
				if(combo.charAt(3) == '1') {
					// 3k, k=1,2,...
					if(pos % 3 == 0) cState = ~cState & 1;
				}
				
				states[pos] = cState;
			}
			
			// check that it's valid
			boolean valid = true;
			for(int i = 0; i < states.length; i++) {
				int l = neededConfig[i];
				if(l == -1) {
					continue;
				}else if(states[i] != l) {
					valid =  false;
					break;
				}
			}
			
			if(valid) {
				// System.out.println("works");
				solutions.add(states);
			}
		}
		
		ArrayList<String> sortedSolutions = new ArrayList<String>();
		for(Integer[] solution: solutions) {
			String s = "";
			for(int i: solution) {
				System.out.print(i);
				s+=i;
			}
			System.out.println();
			sortedSolutions.add(s);
		}
		
		// Compare binary #s that are any string length.
		Collections.sort(sortedSolutions, (String a, String b) -> {
			// System.out.println("new: "+a+" "+b);
			int index = 0;
			Integer aFirst = 0;
			Integer bFirst = 0;
			do {
				// System.out.println(index);
				aFirst = a.indexOf('1',index);
				bFirst = b.indexOf('1',index);
				// System.out.println(aFirst+" "+bFirst);
				index = aFirst+1;
			}while(aFirst==bFirst);
			
			if(aFirst == -1) {
				aFirst = a.length()+1;
			}
			if(bFirst == -1) {
				bFirst = b.length()+1;
			}
			// lowest value = bigger number
			int i = bFirst.compareTo(aFirst);
			return i;
		});
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("lamps.out")));
		for(String solution: sortedSolutions) {
			out.println(solution);
		}
		if(sortedSolutions.size() == 0) {
			out.println("IMPOSSIBLE");
		}
		out.close();
	}
}
