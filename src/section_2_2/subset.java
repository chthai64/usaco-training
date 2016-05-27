/*
ID: conchim1
LANG: JAVA
TASK: subset
 */

package section_2_2;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class subset {

	public static void main(String[] args) throws Exception {
//		Input in = fromFile("subset.in");
//		int N = in.nextInt();
//		int result = solve(N);
//		
//		PrintWriter pw = new PrintWriter(new File("result.out"));
//		pw.println(result);
//		pw.close();
//		in.close();
		
		solve(7);
	}
	
	static int solve(int N) {
		int count = 0;
		int total = (N * (N + 1)) / 2;
		if (total % 2 != 0)
			return 0;
		
		int[][] DP = new int[N + 1][total/2 + 1];
		
		for (int n = 0; n <= N; n++) {
			DP[n][0] = 1;
		}
		
		for (int sum = 0; sum < DP[0].length; sum++) {
			for (int n = 1; n < DP.length; n++) {
				if (sum - n >= 0) {
					DP[n][sum] = DP[n - 1][sum] + DP[n][sum - n];
				} else {
					DP[n][sum] = DP[n - 1][sum];
				}
			}
		}
		
		for (int[] a : DP) {
			System.out.println(Arrays.toString(a));
		}
		
		return count;
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
				stt = new StringTokenizer(nextLine());
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
