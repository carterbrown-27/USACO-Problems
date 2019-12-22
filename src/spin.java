/*
ID: carter.2
LANG: JAVA
TASK: spin
 */
import java.io.*;
import java.util.*;
public class spin {
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();

		BufferedReader in;
		String file = "spin.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		// INPUT
		Wheel[] wheels = new Wheel[5];
		StringTokenizer st;
		for(int i = 0; i < 5; i++) {
			st = new StringTokenizer(in.readLine());
			int speed = Integer.parseInt(st.nextToken());
			int W = Integer.parseInt(st.nextToken());
			int[][] wedges = new int[W][2];
			
			for(int j = 0; j < W; j++) {
				int start = Integer.parseInt(st.nextToken());
				int extent = Integer.parseInt(st.nextToken());
				wedges[j] = new int[] {start, (start+extent)%360};
			}
			wheels[i] = new Wheel(speed, wedges);
		}
		
		in.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [I]");
		
		int second = 0;
		final int MAX_SECONDS = 360;
		while(second <= MAX_SECONDS) {
			// System.out.println("=="+second+"==");
			boolean solved = false;
			for(int angle = 0; angle < 360; angle++) {
				boolean flag = true;
				for(Wheel wheel: wheels) {
					if(!wheel.shinesThrough(angle)) {
						flag = false;
						break;
					}
				}
				if(flag) {
					solved = true;
					break;
				}
			}
	
			if(solved) {
				break;
			}else {
				second++;
				for(Wheel wheel: wheels) {
					wheel.passSecond();
				}
			}			
		}
		
		String answer = (second <= MAX_SECONDS) ? String.valueOf(second) : "none";
		
		System.out.println(answer);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("spin.out")));
		out.println(answer);
		out.close();
		
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [O]");
	}
	
	public static class Wheel {
		int[][] wedges;
		int speed;
		int angle = 0;
		Wheel(int speed, int[][] wedges){
			this.wedges = wedges;
			this.speed = speed;
		}
		
		public void passSecond() {
			angle += speed;
			angle %= 360;
		}
		
		public boolean shinesThrough(int testAngle) {
			for(int[] wedge: wedges) {
				// System.out.println((wedge[0]+angle)%360+" <= "+testAngle+" <= "+(wedge[1]+angle)%360);
				int start = (wedge[0]+angle)%360;
				int end = (wedge[1]+angle)%360;
				
				if(start <= end) {
					if(start <= testAngle && testAngle <= end) {
						return true;
					}
				// otherwise it wraps around
				// therefore tA must be between the (greater) start and 360, or 0 and the (lesser) end.
				}else if(start > end) {
					if((testAngle >= start && testAngle <= 360) || (testAngle >= 0 && testAngle <= end)) {
						return true;
					}
				}
			}
			return false;
		}
	}
}
