/*
ID: conchim1
LANG: JAVA
TASK: numtri
 */

package section_1_5;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class numtri {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("numtri.in");
		
		int N = in.nextInt();
		int[][] mat = new int[N][N];
		
		for (int row = 0; row < N; row++) {
			String text = in.nextLine();
			String[] split = text.split(" ");
			
			for (int col = 0; col < split.length; col++) {
				mat[row][col] = Integer.parseInt(split[col]);
			}
		}
		
		int result = solve(mat, N);
		
		PrintWriter pw = new PrintWriter(new File("numtri.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static int solve(int[][] mat, int N) {
		int[][] DP = new int[N][N];
		DP[N - 1] = mat[N - 1];
		
		for (int row = N - 2; row >= 0; row--) {
			for (int col = 0; col <= row; col++) {
				DP[row][col] = mat[row][col] + Math.max(DP[row + 1][col], DP[row + 1][col + 1]);
			}
		}
		
		return DP[0][0];
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
