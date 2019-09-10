/*
ID: carter.2
LANG: JAVA
TASK: wormhole
 */
import java.awt.Point;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

// WORKS BUT TOO SLOW
public class wormhole {
	public static void main(String[] args) throws IOException {
		long last_time = System.nanoTime();
		long first_time = last_time;
		
		BufferedReader in = new BufferedReader(new FileReader("wormhole.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("wormhole.out")));

		int N = Integer.parseInt(in.readLine());

		Point[] holes = new Point[N];

		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

			Point p = new Point(x,y);
			holes[i] = p;
		}

		Arrays.sort(holes, (a,b) -> a.x - b.x);
		
		System.out.println((System.nanoTime() - last_time) / 1000000 + " ms");
		last_time = System.nanoTime();

		
		ArrayList<Point> newHoles = new ArrayList<Point>(Arrays.asList(holes));
		pairer(newHoles,new HashMap<Point,Point>());
		
		System.out.println((System.nanoTime() - last_time) / 1000000 + " ms");
		last_time = System.nanoTime();

		int count = 0;
		int mapNo = 1;
		for(HashMap<Point,Point> m: masterList) {
			//System.out.println("NEW MAP - #"+mapNo);
			mapNo++;
			boolean flag = false;
			// tries every starting point
			for(Point p: holes) {
// 				opt
//				if(m.containsKey(p)) {
//					if(isSafe(holes, m.get(p))) continue;
//				}else{
//					if(isSafe(holes, findByValue(m, p))) continue;
//				}
				Point s = new Point(p.x,p.y);
				s.x = p.x-1;
				if(s.x < 0) continue;
				//System.out.println("\tstart at: "+s);
				if(isInfinite(holes, m, new ArrayList<Point>(), s)) {
					flag = true;
					break;					
				}
			}
			if(flag) count++;
			System.out.println((System.nanoTime() - last_time)+ " ns");
			last_time = System.nanoTime();
		}
		System.out.println(count);
		System.out.println((System.nanoTime() - first_time) / 1000000 + " ms");
		out.println(count);
		out.close();
	}

	/*
	 * arr - all points
	 * map - wormhole pairs
	 * pv - points visited
	 * c - current point
	 */

	public static boolean isInfinite(Point[] arr, HashMap<Point,Point> map, ArrayList<Point> pv, Point c) {
		Point newC = c;
		while(c!=null) {
			// escape
			if(isSafe(arr, c)){
				//System.out.println("\tESCAPE");
				return false;
			}

			// if at previously visited wormhole.
			if(pv.contains(c)) {
				//System.out.println("\tWORKS!");
				return true;
			}
			
			// add visited
			pv.add(c);

			// sims going right
			Point newF = goRight(arr, c);
			//System.out.println("\tWALK to "+newF);

			// go to destination
			newC = null;
			if(map.containsKey(newF)) {
				newC = map.get(newF);
			}else{
				newC = findByValue(map, newF);
			}
			//System.out.println("\tWARP to "+newC);
			// if(isSafe(arr,newC)) return false
			
			c = newC;
		}
		return false;
	}

	public static ArrayList<HashMap<Point,Point>> masterList = new ArrayList<HashMap<Point,Point>>();
	// pairs = [[1,2],[4,3]] etc
	public static void pairer (ArrayList<Point> list, HashMap<Point,Point> pairs) {
		// 1 pairs thru N
		// (1,2),(1,3)...(1,N)
		// remove nums, do X pairs thru N, X = first unused

		// base case
		if(list.size() == 0) {
			if(!masterList.contains(pairs)) {
				masterList.add(pairs);
			}
			return;
		}


		Point firstPoint = list.remove(0);
		for(int i = 0; i < list.size(); i++) {
			HashMap<Point,Point> tempPairs = new HashMap<Point,Point>(pairs);
			tempPairs.put(firstPoint, list.get(i));

			ArrayList<Point> tempList = new ArrayList<Point>(list);
			tempList.remove(i);

			pairer(tempList, tempPairs);
		}
	}
	
	public static Point findByValue(HashMap<Point,Point> map, Point v) {
		for(Entry<Point, Point> e: map.entrySet()) {
			// System.out.println("\t"+e);
			if(e.getValue().equals(v)){
				return e.getKey();
			}
		}
		return null;
	}

//	public static ArrayList<Point> getUnused(Point[] arr, ArrayList<Point> used){
//		ArrayList<Point> unused = new ArrayList<Point>();
//		for(Point p: arr) {
//			if(!used.contains(p)) unused.add(p);
//		}
//		return unused;
//	}

//	// is this wormhole accessible as a start position?
//	public static boolean isAdjacent(Point a, Point b) {
//		if(a.y == b.y && Math.abs(b.x - a.x) <= 1) return true;
//		return false;
//	}

	public static Point goRight(Point[] arr, Point s) {
		for(Point p: arr) {
			// bc it's sorted by x, this will be the first
			if(p.y == s.y && p.x > s.x) {
				return p;
			}
		}
		return null;
	}

//	public static boolean arrContainsPoint(Point[] arr, int x, int y) {
//		for(Point p: arr) {
//			if(p.x == x && p.y == y) return true;
//		}
//		return false;
//	}

	public static boolean isSafe(Point[] arr, Point h) {
		for(Point p: arr) {
			if(p.y == h.y && p.x > h.x) return false;
		}
		return true;
	}
}
