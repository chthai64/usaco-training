/*
ID: conchim1
LANG: JAVA
TASK: humble
 */

package sectoin_3_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class humble {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("humble.in");
		
		int K = in.nextInt();
		int N = in.nextInt();
		
		int[] S = new int[K];
		for (int i = 0; i < K; i++) {
			S[i] = in.nextInt();
		}
		Arrays.sort(S);
		
		int result = solve(S, N);
		
		PrintWriter pw = new PrintWriter(new File("humble.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	
	static int solve(int[] S, int N) {
		int[] a = new int[N + 1];
		int[] P = new int[S.length];
		Arrays.fill(a, Integer.MAX_VALUE);
		a[0] = 1;
		
		for (int i = 1; i <= N; i++) {
			for (int primeIndex = 0; primeIndex < S.length; primeIndex++) {
				int divResult = a[i - 1] / S[primeIndex];
				int index = higher(a, P[primeIndex], i, divResult);
				int number = a[index] * S[primeIndex];
				P[primeIndex] = index;

				if (number < a[i]) {
					a[i] = number;
				}
			}
		}
		
		return a[N];
	}
	
	static int higher(int[] a, int start, int end, int n) {
		int left = start;
		int right = end - 1;
		int result = Integer.MAX_VALUE;
		
		while (left <= right) {
			int mid = (left + right) / 2;
			if (a[mid] > n) {
				result = mid;
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		
		return result;
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
