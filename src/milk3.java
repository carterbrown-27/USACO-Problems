/*
ID: carter.2
TASK: milk3
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class milk3 {
	
	// Solution for the problem "Mother's Milk"
	public static void main(String[] args) throws IOException {
		BufferedReader in;
		String file = "milk3.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}	
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int Acap  = Integer.parseInt(st.nextToken());
		int Bcap  = Integer.parseInt(st.nextToken());
		int Ccap  = Integer.parseInt(st.nextToken());
		in.close();
		
		caps = new int[]{Acap, Bcap, Ccap};
		
		// c starts full
		Integer[] vals = {0,0,Ccap};
		
		search(vals,new HashSet<Integer[]>());
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk3.out")));
		boolean first = true;
	
		Collections.sort(solutions);
		for(Integer s: solutions) {
			if(!first) {
				out.print(" ");
			}else {
				first = false;
			}
			out.print(s);
			System.out.println(s);
		}
		out.println();
		out.close();
	}
	
	static int[] caps;
	static ArrayList<Integer> solutions = new ArrayList<Integer>();
	
	public static void search(Integer[] vals,  Set<Integer[]> previous) {
		// System.out.println(vals[0]+" "+vals[1]+" "+vals[2]);
		for(Integer[] p: previous) {
			if(vals[0] == p[0] && vals[1] == p[1] && vals[2] == p[2]) {
				return;
			}
		}
		
		if(vals[0] == 0) {
			if(!solutions.contains(vals[2])) solutions.add(vals[2]);
		}
		
		previous.add(vals);
		
		for(int fromX = 0; fromX < 3; fromX++) {
			for(int toX = 0; toX < 3; toX++) {
				if(fromX==toX) continue;
				int space = caps[toX] - vals[toX];
				if(space == 0) continue;
				
				// smallest value of milk left or space left
				int amtPoured = Math.min(space, vals[fromX]);
				
				Integer[] newVals = Arrays.copyOf(vals,vals.length);
				newVals[fromX] -= amtPoured;
				newVals[toX] += amtPoured;
				
 				search(newVals, previous);
			}
		}
	}
}