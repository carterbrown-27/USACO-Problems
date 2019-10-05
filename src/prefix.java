/*
TASK: prefix
ID: carter.2
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class prefix {

	public static ArrayList<String> prefixList = new ArrayList<String>();
	public static Set<Integer> triedStringLengths = new HashSet<Integer>();
	
	// this works but is too slow for the worst case (200,000 string length = 0.7s on my machine)
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();

		BufferedReader in;
		String file = "prefix.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}

		ArrayList<String> prefixes = new ArrayList<String>();
		String line = in.readLine();
		while(!line.equals(".")) {
			prefixes.addAll(Arrays.asList(line.split(" ")));
			line = in.readLine();
		}
		
		Collections.sort(prefixes, (a,b) -> {
			return Integer.valueOf(a.length()).compareTo(b.length());
		});
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [R1]");
		

		String s = "";
		line = in.readLine();
		while(line != null) {
			s = s.concat(line);
			line = in.readLine();
		}
		in.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [R2]");
		

		// DONE INPUT
		// remove prefixes that can be comprised of others.

		for (String p: prefixes) {
			// System.out.println("next prefix ="+ p);
			if(true || !isNotUnique(p,"",prefixes)) {
				// System.out.println("valid");
				prefixList.add(p);
			}
		}

		
//		int biggestPref = prefixList.get(prefixList.size()-1).length();

		int lastPos = -1;
		int max = 0;
		ArrayList<Integer> order = new ArrayList<Integer>();
		
		int pos = 0;
		while(true) {
			boolean tried = false;
			boolean foundNext = false;
			int cL = pos;
			if (!triedStringLengths.contains(cL) || cL >= s.length()) {
				// if (cL % 500 == 0) System.out.println(cL);
				foundNext = false;
				for (int i = lastPos + 1; i < prefixList.size(); i++) {
					String p = prefixList.get(i);
					if(lastPos >= 0 && p.length() <= prefixList.get(lastPos).length()) continue;
					int cLpP = cL + p.length();
					if (cLpP <= s.length() && s.substring(cL, cLpP).contentEquals(p)) {
						order.add(i);
						pos += p.length();
						foundNext = true;
						lastPos = -1;
						break;
					}
				} 
			}else {
				tried = true;
			}
			
			if(!foundNext) {
				max = Math.max(max, cL);
				
				if(order.size() == 0) {
					break;
				}
				
				lastPos = order.remove(order.size()-1);
				pos -= prefixList.get(lastPos).length();
				if(!tried) {
					triedStringLengths.add(cL);
				}
			}
		}
		// int max = solve(s, "");

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("prefix.out")));
		System.out.println(max);
		out.println(max);
		out.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");

	}

	public static boolean isNotUnique(String p, String concat, ArrayList<String> prefixes){
		// System.out.println(concat);
		if(concat.length()>=p.length()) {	
			return concat.equals(p);
		}

		if(concat.equals(p.substring(0,concat.length()))) {
			for(String prf: prefixes) {
				if(!p.equals(prf) && concat.length()+prf.length() <= p.length()) {
					if(isNotUnique(p, concat+prf, prefixes)) return true;
				}
			}
		}

		return false;
	}


}
