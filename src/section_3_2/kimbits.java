/*
ID: conchim1
LANG: JAVA
TASK: kimbits
 */

package section_3_2;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class kimbits {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("kimbits.in");
		int N = in.nextInt();
		int L = in.nextInt();
		long I = in.nextLong();
		
		String result = solve(N, L, I);
		
		PrintWriter pw = new PrintWriter(new File("kimbits.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static String solve(int N, int L, long I) {
		long[][] DP = new long[N + 1][L + 1];
		
		for (int n = 0; n <= N; n++) {
			for (int l = 0; l <= L; l++) {
				DP[n][l] = combination(n, l);
				if (l - 1 >= 0) {
					DP[n][l] += DP[n][l - 1];
				}
			}
		}
		
		String result = "";
		
		int n = N;
		int l = L;
		long i = I;
		
		while (n > 0) {
			// check if it's possible with first bit is zero
			if (DP[n - 1][l] >= i) {
				result += "0";
			} else {
				result += "1";
				i -= DP[n - 1][l];
				l--;
			}
			
			n--;
		}
		
		return result;
	}
	
	static int needToBoost(int n, int maxOnes) {
		String binary = Integer.toBinaryString(n);
		return (int) combination(binary.length() - 1, maxOnes + 1);
	}
	
	static int countOne(int n) {
		int count = 0;
		
		for (int offset = 0; offset < 32; offset++) {
			if (((n >>> offset) & 1) == 1) {
				count++;
			}
		}
		
		return count;
	}
	
	static long combination(int n, int r) {
		if (n < r)
			return 0;
		
		double result = 1;

		for (long i = n; i >= 1; i--) {
			if (i > r) {
				result *= i;
			}
			
			if (i <= n - r) {
				result /= i;
			}
		}
		
		return Math.round(result);
	}

	private static Input fromFile(String path) throws IOException {
		return new Input(new FileInputStream(new File(path)));
	}

	private static class Input {
		private BufferedReader reader;
		private StringTokenizer stt;

		public Input(InputStream stream) {		
			reader = new BufferedReader(new InputStreamReader(stream));
		}

		public String nextLine() {
			try {
				return reader.readLine();
			} catch (IOException e) {
				return null;
			}
		}

		public String nextString() {
			while (stt == null || !stt.hasMoreTokens()) {
				String line = nextLine();
				if (line == null)
					return null;
				
				stt = new StringTokenizer(line);
			}
			return stt.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(nextString());
		}

		public long nextLong() {
			return Long.parseLong(nextString());
		}

		public String next() {
			return nextString();
		}

		public void close() {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {}
			}
		}
	}
}
