package section_3;
/*
ID: carter.2
LANG: JAVA
TASK: shopping
 */
import java.io.*;
import java.util.*;
public class shopping {

	static HashMap<Integer[], Integer> offers;
	static int[][][][][] dp = new int[6][6][6][6][6];
	
	// Solution to Section 3.3: "Shopping Offers"
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();

		BufferedReader in;
		String file = "shopping.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		// outer maps bundles to prices, inner maps codes to quantities.
		ArrayList<Integer> ids = new ArrayList<>();
		offers = new HashMap<Integer[], Integer>();
		
		final int ITEM_AMT = 5;
		
		int s = Integer.parseInt(in.readLine());
		StringTokenizer st;
		for(int i = 0; i < s; i++) {
			st = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(st.nextToken());
			
			Integer[] arr = new Integer[ITEM_AMT];
			for(int x = 0; x < arr.length; x++) arr[x] = 0;
			
			// read in quantities
			for(int j = 0; j < n; j++) {
				// c,k
				// each c (ID) uniquely corresponds to an index (0-4) in arrays.
				int c = Integer.parseInt(st.nextToken());
				if(!ids.contains(c)) ids.add(c);
				int k = Integer.parseInt(st.nextToken());
				arr[ids.indexOf(c)] = k;
			}
			
			// p
			int price = Integer.parseInt(st.nextToken());
			offers.put(arr, Math.min(offers.getOrDefault(arr,Integer.MAX_VALUE),price));
		}
		
		// DEBUG:
//		offers.forEach((k,v) -> {
//			for(Integer i: k) {
//				System.out.print(i+" ");
//			}
//			System.out.println(v);
//		});
		
		int _b = Integer.parseInt(in.readLine());
		int[] prices = new int[ITEM_AMT];
		int[] needed = new int[ITEM_AMT];
		
		for(int i = 0; i < _b; i++) {
			st = new StringTokenizer(in.readLine());
			int id = Integer.parseInt(st.nextToken());
			int amt = Integer.parseInt(st.nextToken());
			int price = Integer.parseInt(st.nextToken());
			
			if(!ids.contains(id)) ids.add(id);
			int index = ids.indexOf(id);
			prices[index] = price;
			needed[index] = amt;
			
			// add "offer" of single item for single item price.
			Integer[] arr = new Integer[ITEM_AMT];
			for(int x = 0; x < arr.length; x++) arr[x] = 0;
			arr[index] = 1;
			
			offers.put(arr,Math.min(offers.getOrDefault(arr,Integer.MAX_VALUE),price));
		}
		
		// these are the times you wish Java had memset
		for(int a = 0; a <= 5; a++) {
			for(int b = 0; b <= 5; b++) 
				for(int c = 0; c <= 5; c++) 
					for(int d = 0; d <= 5; d++)
						for(int e = 0; e <= 5; e++)
							dp[a][b][c][d][e] = -1;
		}
		
		dp[0][0][0][0][0] = 0;
		
		// aPrice * amt + ... + ePrice * amt
		int price = knapsack(needed);
		
		System.out.println(price);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("shopping.out")));
		out.println(price);
		out.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}
	
	// Integer Knapsack
	public static int knapsack(int[] needed) {
		int knownValue = dp[needed[0]][needed[1]][needed[2]][needed[3]][needed[4]];
		if(knownValue > -1) {
			return knownValue;
		}
		
		// max cost could be 999~1000, 1000*5*5 = 25,000.
		// overflow-safe min
		int min = 100000;
		loop:
		for(Integer[] offer: offers.keySet()) {
			int[] temp = new int[needed.length];
			for(int i = 0; i < temp.length; i++) {
				temp[i] = needed[i] - offer[i];
			}
			
			for(int i: temp) {
				// DEBUG: System.out.print(i+" ");
				if(i<0) {
					// DEBUG: System.out.println();
					continue loop;
				}
			}
			// DEBUG: System.out.println();
			
			int val = knapsack(temp);
			min = Math.min(min, val + offers.get(offer));
		}
		
		dp[needed[0]][needed[1]][needed[2]][needed[3]][needed[4]] = min;
		return min;
	}
}	
