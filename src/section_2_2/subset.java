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
		Input in = fromFile("subset.in");
		int N = in.nextInt();
		long result = solve(N);
		
		PrintWriter pw = new PrintWriter(new File("subset.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static long solve(int N) {
		int total = (N * (N + 1)) / 2;
		if (total % 2 != 0)
			return 0;
		
		long[][] DP = new long[N + 1][total/2 + 1];
		
		for (int i = 0; i <= N; i++) {
			DP[i][0] = 1;
		}
		
		for (int sum = 1; sum < DP[0].length; sum++) {
			for (int n = 1; n <= N; n++) {
				if (sum - n >= 0) {
					DP[n][sum] = DP[n - 1][sum] + DP[n - 1][sum - n];
				} else {
					DP[n][sum] = DP[n - 1][sum];
				}
			}
		}
		
		
		return DP[N][total/2] / 2;
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
