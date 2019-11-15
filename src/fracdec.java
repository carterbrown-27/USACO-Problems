/*
ID: carter.2
TASK: fracdec
LANG: JAVA
 */
import java.io.*;
import java.util.*;
public class fracdec {

	// Solution to Section 2.4 - Problem 79_Fractions to Decimals
	public static void main(String[] args) throws IOException {
		long first_time = System.nanoTime();

		BufferedReader in;
		String file = "fracdec.in";
		try{
			in = new BufferedReader(new FileReader(file));
		}catch(Exception e) {
			in = new BufferedReader(new FileReader("DATA/"+file));
		}
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int D = Integer.parseInt(st.nextToken());
		
		in.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [I]");
		
		// value before decimal.
		int whole = N / D;
		
		// maps values of remainder to index c
		HashMap<Integer,Integer> rems = new HashMap<Integer,Integer>();
		
		StringBuilder decimal = new StringBuilder();
		int remainder = N - (whole*D);
		int c = 0;
		
		// Long Division for Decimal Values
		do {
			remainder*=10;
			
			// integer division intentional, gets biggest whole number value
			int subDig = remainder / D;
			int subAmt = subDig *  D;
			
			remainder -= subAmt;
			
			if(rems.containsKey(remainder)) {
				// loop this part.
				int diff = 1;
				if(subDig == (decimal.charAt(rems.get(remainder)) - '0')) {
					diff = 0;
				}else {
					decimal.append(subDig);
				}
				decimal.insert(rems.get(remainder) + diff, "(");
				decimal.append(")");
				break;
			}else {
				decimal.append(subDig);
				rems.put(remainder, c);
			}
			
			c++;
		} while(remainder > 0);
		
		String output = whole + "." + decimal.toString();
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fracdec.out")));
		
		// Print 76 characters per line.
		int oLen = 0;
		do {
			String line = output.substring(oLen, Math.min(oLen+76, output.length()));
			System.out.println(line);
			out.println(line);
			oLen += 76;
		}while(output.length() > oLen);
		
		out.close();
		System.out.println((System.nanoTime() - first_time) / 1000000 + "ms [F]");
	}

}
