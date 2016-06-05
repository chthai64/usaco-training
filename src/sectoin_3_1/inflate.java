/*
ID: conchim1
LANG: JAVA
TASK: inflate
 */

package sectoin_3_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class inflate {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("inflate.in");

		int M = in.nextInt();
		int N = in.nextInt();
		
		

		int[] points = new int[N];
		int[] times = new int[N];
		
		for (int i = 0; i < N; i++) {
			points[i] = in.nextInt();
			times[i] = in.nextInt();
		}
		
		int result = solve(M, points, times);
		
		PrintWriter pw = new PrintWriter(new File("inflate.out"));
		pw.println(result);
		pw.close();
		in.close();
	}

	static int solve(int M, int[] points, int[] times) {
		int[] DP = new int[M + 1];

		for (int problem = 0; problem < points.length; problem++) {
			for (int time = 1; time <= M; time++) {
				if (time - times[problem] >= 0) {
					DP[time] = Math.max(DP[time], points[problem] + DP[time - times[problem]]);
				}
			}
		}

		return DP[M];
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
