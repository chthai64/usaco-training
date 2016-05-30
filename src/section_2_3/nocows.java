/*
ID: conchim1
LANG: JAVA
TASK: nocows
 */

package section_2_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;


public class nocows {
	static final int MOD = 9901;

	public static void main(String[] args) throws Exception {
		Input in = fromFile("nocows.in");
		int N = in.nextInt();
		int K = in.nextInt();
		int result = solve(N, K);
		
		PrintWriter pw = new PrintWriter(new File("nocows.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static int solve(int N, int K) {
		int[][] DP = new int [K + 1][N + 1];
		DP[1][1] = 1;
		
		for (int n = 3; n <= N; n += 2) {
			for (int k = 1; k <= Math.min(K, maxLevel(n)); k++) {
				int ways = 0;
				
				for (int nLeft = 1; nLeft < n - 1; nLeft++) {
					int nRight = n - 1 - nLeft;
					
					for (int kRight = 1; kRight < k - 1; kRight++) {
						ways += DP[k - 1][nLeft] * DP[kRight][nRight] * 2 % MOD;
					}
					
					ways += DP[k - 1][nLeft] * DP[k - 1][nRight] % MOD;
				}
				
				DP[k][n] = ways % MOD;
			}
		}
		
		return DP[K][N];
	}

	static int maxLevel(int count) {
		return (count + 1) / 2;
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
