package section_2;
/*
ID: carter.2
TASK: runround
LANG: JAVA 
 */
import java.io.*;
import java.util.*;
public class runround {
	
	// Solution to "Problem 57_Runaround Numbers"
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		
		BufferedReader in;
		String file = "runround.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		int M = Integer.parseInt(in.readLine());
		// Pattern p = Pattern.compile("(\\d).*\\1");
		// Matcher m;
		// filtrative solution, but it runs in time.
		long sol = 0;
		for(long i = M+1; i <= 987654321; i++) {
			String str = String.valueOf(i);
			if(str.contains("0")) continue;
			//m = p.matcher(str);
			//if(m.find()) continue;
			
			char[] arr = str.toCharArray();
			boolean[] visited = new boolean[arr.length];
			Set<Integer> digits = new HashSet<Integer>();
			int x = 0;
			int d = arr[x]-'0';
			boolean flag = false;
			do {
				x+=d;
				if(x>=arr.length) x%=arr.length;
				
				d = arr[x]-'0';
				if(!visited[x] && !digits.contains(d)) {
					visited[x] = true;
					digits.add(d);
				}else {
					// loop back to not 1st, or invalid #
					flag = true;
					break;
				}
			}while(x!=0);
			
			if(!flag && allTrue(visited)) {
				sol = i;
				break;
			}
		}
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("runround.out")));
		out.println(sol);
		out.close();
		System.out.println(sol);
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}
	
	public static boolean allTrue(boolean[] boolArr) {
		for(boolean b: boolArr) {
			if(!b) return false;
		}
		return true;
	}
}
