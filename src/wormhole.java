/*
ID: carter.2
LANG: JAVA
TASK: wormhole
 */
import java.awt.Point;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

// WORKS!
public class wormhole {
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();
		
		BufferedReader in = new BufferedReader(new FileReader("wormhole.in"));
		int N = Integer.parseInt(in.readLine());

		Point[] holes = new Point[N];

		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

			Point p = new Point(x,y);
			holes[i] = p;
		}
		in.close();
		
		// sorts wormholes based on x value (increasing)
		Arrays.sort(holes, (a,b) -> a.x - b.x);
		
		System.out.println("after init > "+(System.nanoTime() - first_time) / 1000000 + " ms");
		
		ArrayList<Point> newHoles = new ArrayList<Point>(Arrays.asList(holes));
		pairer(newHoles,new HashMap<Point,Point>());
		
		System.out.println("after pairer > "+(System.nanoTime() - first_time) / 1000000 + " ms");

		int count = 0;
//		int mapNo = 1;
		for(HashMap<Point,Point> m: masterList) {
			//System.out.println("NEW MAP - #"+mapNo);
//			mapNo++;
			boolean flag = false;
			// tries every starting point
			for(Point p: holes) {
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
			// System.out.println((System.nanoTime() - last_time)+ " ns");
			// last_time = System.nanoTime();
		}
		System.out.println("after algorithm > " + (System.nanoTime() - first_time) / 1000000 + " ms");
		System.out.println(count);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("wormhole.out")));
		out.println(count);
		out.close();
	}

	/*
	 * arr - all points
	 * map - wormhole pairs
	 * pv - points visited
	 * c - current point
	 */
	
	public static ArrayList<ArrayList<Point>> loops = new ArrayList<ArrayList<Point>>();
	public static boolean isInfinite(Point[] arr, HashMap<Point,Point> map, ArrayList<Point> pv, Point c) {
		Point newC = c;
		while(c!=null) {
			if(isSafe(arr, c)){
				//System.out.println("\tESCAPE");
				return false;
			}

			// if at previously visited wormhole.
			if(pv.contains(c)) {
				// System.out.println("\tWORKS!");
				// System.out.println(pv);
				if(!loops.contains(pv)) loops.add(pv);
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
	public static CNode tree = new CNode(); 
	// pairs = [[1,2],[4,3]] etc
	public static void pairer (ArrayList<Point> list, HashMap<Point,Point> pairs) {
		// 1 pairs thru N
		// (1,2),(1,3)...(1,N)
		// remove nums, do X pairs thru N, X = first unused
		
		tree = new CNode(list);
		
		CNode current = tree;
		Queue<CNode> q = new LinkedList<CNode>();
		q.add(tree);
		while(q.size() > 0) {
			current = q.remove();
			if(current.getUnused().size() == 0) {
				// System.out.println("endOfNode");
				HashMap<Point,Point> map = new HashMap<Point,Point>();
				CNode n = current;
				do {
					map.put(n.pair[0], n.pair[1]);
					n = n.getParent();
					// System.out.println("up");
				}while(n != null && n.getPair() != null); // TODO: make getter
				masterList.add(map);
				continue;
			}
			Point firstPoint = current.getUnused().get(0);
			for(int i = 1; i < current.getUnused().size(); i++) {
				// add new node
				Point[] pair = {firstPoint, current.getUnused().get(i)};
				CNode c = new CNode(current,pair,getUnused(current.getUnused(),pair));
				current.children.add(c);
				q.add(c);
			}		
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

	public static ArrayList<Point> getUnused(ArrayList<Point> list, Point[] pair){
		ArrayList<Point> unused = new ArrayList<Point>();
		for(Point p: list) {
			if(!Arrays.asList(pair).contains(p)) unused.add(p);
		}
		return unused;
	}

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

class CNode {
	ArrayList<CNode> children = new ArrayList<CNode>();
	Point[] pair;  
	ArrayList<Point> unused;
	CNode parent;
	
	CNode(){};
	CNode(ArrayList<Point> unused){
		this.unused = unused;
	}
	CNode(CNode parent, Point[] pair, ArrayList<Point> unused){
		this.parent = parent;
		this.pair = pair;
		this.unused = unused;
	}
	
	// getters
	public CNode getParent() { return parent; }
	public Point[] getPair() { return pair; }
	public ArrayList<CNode> getChildren(){ return children; }
	public ArrayList<Point> getUnused(){ return unused; }
	
	// setters
	public void setChildren(ArrayList<CNode> children){ this.children = children; }
}