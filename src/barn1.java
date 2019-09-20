/*
ID: carter.2
LANG: JAVA
TASK: barn1
*/
import java.io.*;
import java.util.*;

public class barn1 {
	
	// Solution for the problem "Barn Repair"
	public static void main(String[] args) throws IOException {
		BufferedReader in;
		String file = "barn1.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}	
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("barn1.out")));
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int M = Integer.parseInt(st.nextToken());
		@SuppressWarnings("unused")
		int S = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		
		// if more boards then stalls, stalls each have one
		if(M >= C) {
			out.println(C);
			out.close();
			in.close();
			return;
		}
		
		int[] stalls = new int[C];
		
		for(int i = 0; i < C; i++) {
			stalls[i] = Integer.parseInt(in.readLine());
		} 
		in.close();
		
		Arrays.sort(stalls);
		
		int[] cuts = new int[M-1];
		
		// make M-1 cuts
		for(int i = 0; i < cuts.length; i++) {
			int maxGap = 0;
			int cutLoc = -1;
			
			// find biggest gap, can't be first, last
			for(int x = 2; x < stalls.length; x++) {
				int a = stalls[x-1];
				int b = stalls[x];
				
				if(b-a > maxGap && !inArray(cuts,a)) {
					maxGap = b-a;
					cutLoc = a; // *
				}
			}
			System.out.println(cutLoc);
			cuts[i] = cutLoc;
		}
		
		Arrays.sort(cuts);
		
		int[] boardLengths = new int[M];
		
		int boardNo = 0;
		int boardStart = stalls[0];
		for(int x = 1; x < stalls.length; x++) {
			
			// if not last board
			boolean lastBoard = (boardNo == M-1);
				// unless end
			boolean end = (x == stalls.length-1);
			// if cut
			if((!lastBoard && cuts[boardNo] == stalls[x]) || end) { // cut indicates end of board
				System.out.println("boardNo "+boardNo+" - s"+boardStart+" e"+stalls[x]);
				boardLengths[boardNo] = stalls[x] - boardStart; // len of board
				boardNo++;
				if(!end) boardStart = stalls[x+1]; // loc of next stall
			}
		}
		
		int total = 0;
		for(int i: boardLengths) {
			total+=i;
		}
		
		total += M; // each covers one extra
		
		System.out.println(total);
		out.println(total);
		out.close();
	}
	
	// useful for later
	public static boolean inArray(int[] arr, int k) {
		for(int i: arr) {
			if(i == k) return true;
		}
		return false;
	}
}
