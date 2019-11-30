package section_3;
/*
ID: carter.2
LANG: JAVA
TASK: contact
*/
import java.io.*;
import java.util.*;

public class contact {
	
	// Solution to Section 3.1 - Problem 11 "Contact"
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		BufferedReader in;
		String file = "contact.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		int A = Integer.parseInt(st.nextToken());
		int B = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());
		
		
		StringBuilder str = new StringBuilder();
		
		String line = in.readLine();
		while(line!=null) {
			str.append(line);
			line = in.readLine();
		}
		
		HashMap<String,Integer> freqs = new HashMap<String,Integer>();
		
		// find the strings. this is the main algorithm
		for(int x = 0; x < str.length(); x++) {
			for(int len = A; len <= B && x+len <= str.length(); len++) {
				String sub = str.substring(x,x+len);
				freqs.put(sub,freqs.getOrDefault(sub, 0) + 1);
			}
		}
		
		// output
		
		ArrayList<Map.Entry<String,Integer>> entrySet = new ArrayList<Map.Entry<String,Integer>>(freqs.entrySet());
		
		// sort entries by frequency, descending
		Collections.sort(entrySet, (Map.Entry<String,Integer> a, Map.Entry<String,Integer> b) -> {
			return -a.getValue().compareTo(b.getValue());
		});
		
		// DEBUG
//		for(Map.Entry<String,Integer> e: entrySet) {
//			System.out.println(e.getKey()+" :: "+e.getValue());
//		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("contact.out")));
	
		// off = number of extra accesses needed due to ties
		int last = -1;
		int off = 0;
		for(int i = 0; i < N+off && i < entrySet.size(); i++) {
			Map.Entry<String,Integer> entry = entrySet.get(i);
			int eV = entry.getValue();
			
			// if this is one of the values looked-ahead (see below/look ahead ;))
			if(eV == last) {
				off++;
				continue;
			}else {
				last = eV;
			}
			out.println(eV);
			// System.out.println(entry.getKey());
			
			// look ahead in entrySet until next element's value != current value
			ArrayList<String> ties = new ArrayList<String>();
			for(int t = i; (t < entrySet.size()) && (entrySet.get(t).getValue() == eV); t++) {
				ties.add(entrySet.get(t).getKey());
			}
			
			// sort by length first, tiebreaker = binary value
			Collections.sort(ties, (String a,String b) -> {
				int lenC = Integer.valueOf(a.length()).compareTo(Integer.valueOf(b.length()));
				if(lenC != 0) {
					return lenC;
				}else {					
					return Integer.valueOf(a,2).compareTo(Integer.valueOf(b,2));
				}
			});
			
			// output 6 per line.
			int c = 0;
			for(int x = 0; x < ties.size(); x++) {
				c++;
				out.print(ties.get(x));
				if(x!=ties.size()-1 && c > 0 && c < 6) out.print(" ");
				if(c==6) {
					out.println();
					c = 0;
				}
			}
			if(c!=0) out.println();
		}
		
		out.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}
}
