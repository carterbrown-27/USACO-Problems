/*
ID: carter.2
LANG: JAVA
TASK: shopping
 */
import java.io.*;
import java.util.*;
public class shopping {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		long first_time = System.nanoTime();

		BufferedReader in;
		String file = "shopping.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		// outer maps bundles to prices, inner maps codes to quantities.
		HashMap<HashMap<Integer,Integer>, Integer> offers = new HashMap<>();
		
		int s = Integer.parseInt(in.readLine());
		StringTokenizer st;
		for(int i = 0; i < s; i++) {
			st = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(st.nextToken());
			
			HashMap<Integer,Integer> q_map = new HashMap<>();
			// read in quantities
			for(int j = 0; j < n; j++) {
				// c,k
				q_map.put(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			}
			
			int price = Integer.parseInt(st.nextToken());
			// p
			offers.put(q_map, Math.min(offers.getOrDefault(q_map,Integer.MAX_VALUE),price));
		}
		
		int _b = Integer.parseInt(in.readLine());
		HashMap<Integer,Integer> prices = new HashMap<>();
		HashMap<Integer,Integer> needed = new HashMap<>();
		
		for(int i = 0; i < _b; i++) {
			st = new StringTokenizer(in.readLine());
			int id = Integer.parseInt(st.nextToken());
			
			needed.put(id, Integer.parseInt(st.nextToken()));
			int price = Integer.parseInt(st.nextToken());
			prices.put(id, price);
			
			// add "offer" of single item for single item price.
			HashMap<Integer,Integer> q_map = new HashMap<>();
			q_map.put(id,1);
			offers.put(q_map,Math.min(offers.getOrDefault(q_map,Integer.MAX_VALUE),price));
		}
		
		int[][][][][] solutions = new int[6][6][6][6][6];
		
		
		// solution = # a-e.
		int price = 0;
		System.out.println(price);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("shopping.out")));
		out.println(price);
		out.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}
}
