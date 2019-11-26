package section_2;
/*
ID: carter.2
TASK: sort3
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class sort3 {

	// Solution to "Sorting a Three-Valued Sequence"
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		BufferedReader in;
		String file = "sort3.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}

		int N = Integer.parseInt(in.readLine());

		ArrayList<Integer> rawList = new ArrayList<Integer>();

		for(int i = 0; i < N; i++) {
			rawList.add(Integer.parseInt(in.readLine()));
		}

		ArrayList<Integer> sortedList = new ArrayList<Integer>(rawList);
		Collections.sort(sortedList);

		// Integer[] = [(amt of) 1s, 2s, 3s]
		HashMap<Integer,Integer[]> map = new HashMap<Integer,Integer[]>();

		int n = 1;
		Integer[] section = {0,0,0};
		for(int i = 0; i < N; i++) {
			int x = sortedList.get(i);
			// System.out.println(x+":");
			if(x != n) {
				map.put(n,section);
				section = new Integer[] {0,0,0};
				n++;
			}
			int v = rawList.get(i);
			// System.out.println(v);
			section[v-1]++;
		}
		map.put(n, section);
		int moves = 0;

		for(int i = 1; i <= 3; i++) {
			if(!map.containsKey(i)) continue;
			while (map.get(i)[i-1] < sumOfArray(map.get(i))) {
				boolean optimal = false;
				int A = i-1;
				int B = -1;
				
				// try to make an optimal switch (one that puts two values in the right spot)
				for(int b = 1; b<=3; b++) {
					if(b==i) continue; // same
					if(map.get(i)[b-1] == 0) continue; // none of type b left in original
					if(map.get(b)[i-1] > 0) { // second has type i
						B = b-1;
						optimal = true;
						break;
					}
				}
				
				// if no such switch exists, pick any switch that puts a correct value in the current section
				if(!optimal) {
					for(int b = 1; b<=3; b++) {
						if(b==i) continue;
						B = b-1;
						break;
					}
				}
				
				// shouldn't happen but safety valve
				if(B == -1) continue;

				// switch values
				Integer[] secA = map.get(A+1);
				secA[B]--;
				secA[A]++;
				map.put(A+1,secA);
				
				Integer[] secB = map.get(B+1);
				secB[A]--;
				secB[B]++;
				map.put(B+1,secB);
				moves++;
			}
		}

		System.out.println("M: "+moves);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sort3.out")));
		out.println(moves);
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	} 

	public static int sumOfArray(Integer[] arr) {
		int sum = 0;
		for(int n: arr) {
			sum+=n;
		}
		return sum;
	}
}
