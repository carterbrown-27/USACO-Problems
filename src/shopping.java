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
		
		// this only works as a fractional approach, meaning it is not correct.
		// TODO: instead, use Integer Knapsack Algorithm.
		ArrayList<HashMap<Integer,Integer>> orderedOffers = new ArrayList<>(offers.keySet());
		Collections.sort(orderedOffers, (Comparator<? super HashMap<Integer,Integer>>) (HashMap<Integer,Integer> a, HashMap<Integer,Integer> b) -> {
			Double aE;
			Double bE;
			
			int aOriginalP = 0;
			Integer aTQ = 0;
			for(Integer i: a.keySet()) {
				aOriginalP += a.get(i) * prices.get(i);
				aTQ += a.get(i);
			}
			
			int bOriginalP = 0;
			Integer bTQ = 0;
			for(Integer i: b.keySet()) {
				bOriginalP += b.get(i) * prices.get(i);
				bTQ += b.get(i);
			}
			
			aE = aOriginalP/ (double) offers.get(a);
			bE = bOriginalP/ (double) offers.get(b);
			
			return aE.compareTo(bE) != 0 ? -aE.compareTo(bE) : -aTQ.compareTo(bTQ);
		});
		
		// DEBUG:
		System.out.println(orderedOffers.size());
		orderedOffers.forEach((i) -> {
			i.forEach((k,v) -> System.out.print(k+" "+v+","));
			System.out.println();
		});
		
		int price = 0;
		for(HashMap<Integer,Integer> offer: orderedOffers) {
			int amt = Integer.MAX_VALUE;
			for(Integer item: offer.keySet()) {
				// if item is excessive
				if(needed.get(item) < offer.get(item)) {
					amt = 0;
					break;
				} else {
					// int division gives desired result;
					amt = Math.min(amt, needed.get(item) / offer.get(item));
				}
			}
			
			price += amt * offers.get(offer);
			for(Integer item: offer.keySet()) {
				// remove q*amt from needed.
				needed.put(item, needed.get(item) - (amt*offer.get(item)));
			}
		}
		
		System.out.println(price);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("shopping.out")));
		out.println(price);
		out.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}
}
