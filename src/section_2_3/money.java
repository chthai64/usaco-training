/*
ID: conchim1
LANG: JAVA
TASK: money
 */

package section_2_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class money {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("money.in");
		
		int V = in.nextInt();
		int N = in.nextInt();
		int[] coins = new int[V];
		
		for (int i = 0; i < V; i++) {
			coins[i] = in.nextInt();
		}
		
		long result = solve(N, coins);
		
		PrintWriter pw = new PrintWriter(new File("money.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static long solve(int N, int[] coins) {
		long[] DP = new long[N + 1];
		DP[0] = 1;
		
		for (int coin : coins) {
			for (int money = N; money >= 0; money--) {
				int mul = 1;	
				int moneyRemain = money - mul * coin;
				
				while (moneyRemain >= 0) {
					DP[money] += DP[moneyRemain];
					moneyRemain = money - ++mul * coin;
				}
			}
		}
		
		return DP[N];
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
