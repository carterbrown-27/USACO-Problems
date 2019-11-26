package section_2;
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
	
	// WORKING SOLUTION for the problem "Section 2.3 - Longest Prefix" 0.35s (0.93s on grader) in worst case
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();

		BufferedReader in;
		String file = "prefix.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}

		ArrayList<String> prefixes = new ArrayList<String>(200);
		String line = in.readLine();
		while(!line.equals(".")) {
			prefixes.addAll(Arrays.asList(line.split(" ")));
			line = in.readLine();
		}
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [R1]");
		
		Collections.sort(prefixes, (a,b) -> {
			return Integer.valueOf(b.length()).compareTo(a.length());
		});
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [R1B]");
		
		StringBuilder sb = new StringBuilder(200000);
		line = in.readLine();
		while(line != null) {
			sb.append(line);
			line = in.readLine();
		}
		in.close();
		
		String s = sb.toString();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [R2]");
		

		// DONE INPUT
		// remove prefixes that can be comprised of others.

		for (String p: prefixes) {
			// System.out.println("next prefix ="+ p);
			if(!isNotUnique(p,"",prefixes)) {
				// System.out.println("valid");
				prefixList.add(p);
			}
		}

		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [A]");

		int lastPos = -1;
		int max = 0;
		ArrayList<Integer> order = new ArrayList<Integer>();
		int sL = s.length();
		
		int pos = 0;
		boolean tried = false;
		boolean foundNext = false;
		int cL = pos;
		
		while(true) {
			tried = false;
			foundNext = false;
			cL = pos;
			if (!triedStringLengths.contains(cL) && cL < sL) {
				if (cL == 199049) {
					System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [ANS]");
				}
				foundNext = false;
				for (int i = lastPos + 1; i < prefixList.size(); i++) {
					String p = prefixList.get(i);
					int pL = p.length();
					if(lastPos >= 0 && pL >= prefixList.get(lastPos).length()) continue;
					
					if (pos+pL <= sL && s.substring(cL, pos+pL).contentEquals(p)) {
						order.add(i);
						pos += pL;
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
