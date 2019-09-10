/*
ID: carter.2
LANG: JAVA
TASK: combo
*/
import java.io.*;
import java.util.*;
public class combo {
	// WORKS
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("combo.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("combo.out")));
		
		int N = Integer.parseInt(in.readLine());
		
		// if N super small
		if(N <= 5) {
			out.println((int) Math.pow(N,3));
			out.close();
			return;
		}
		
		
		// populates array with 1->N vals
		int[] digits = new int[N];
		for(int i = 0; i < N; i++) {
			digits[i] = i+1;
		}
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		ArrayList<int[]> combos = new ArrayList<int[]>();
		
		// john's combos
		int ja = Integer.parseInt(st.nextToken());
		int jb = Integer.parseInt(st.nextToken());
		int jc = Integer.parseInt(st.nextToken());
		
		for(int x = ja - 1 - 2; x < ja + 2; x++) {
			int a = wrap(x,digits);
			for(int y = jb - 1 - 2; y < jb + 2; y++) {
				int b = wrap(y,digits);
				for(int z = jc - 1 - 2; z < jc + 2; z++) {
					int c = wrap(z,digits);
					
					int[] arr = {a,b,c};
					System.out.println(a+","+b+","+c);
					combos.add(arr);
				}
			}
		}
		
		System.out.println(combos.size());
		// works
		
		
		// slick copy/paste job, master combos
		st = new StringTokenizer(in.readLine());
		
		int ma = Integer.parseInt(st.nextToken());
		int mb = Integer.parseInt(st.nextToken());
		int mc = Integer.parseInt(st.nextToken());
		
		
		for(int x = ma - 1 - 2; x < ma + 2; x++) {
			int a = wrap(x,digits);
			for(int y = mb - 1 - 2; y < mb + 2; y++) {
				int b = wrap(y,digits);
				for(int z = mc - 1 - 2; z < mc + 2; z++) {
					int c = wrap(z,digits);
					
					int[] arr = {a,b,c};
					System.out.println(a+","+b+","+c);
					
					// catches duplicates. (this could've just been a map ilo a list)
					if(!containsArr(combos,arr))combos.add(arr);
				}
			}
		}
		
		System.out.println(combos.size());
		out.println(combos.size());
		out.close();
	}
	
	public static int wrap(int i, int[] arr) {
		if(i < 0) return arr[arr.length+i];
		if(i >= arr.length) return arr[i-arr.length]; 
		return arr[i];
	}
	
	// very program-specific
	public static boolean containsArr(ArrayList<int[]> list, int[] arr) {
		for(int[] r: list) {
			boolean flag = true;
			for(int i = 0; i < r.length; i++) {
				if(r[i] != arr[i]) {
					flag = false;
					break;
				}
			}
			if(flag) return flag;
		}
		return false;
	}
}
