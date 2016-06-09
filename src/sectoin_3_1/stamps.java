/*
ID: conchim1
LANG: JAVA
TASK: stamps
 */

package sectoin_3_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class stamps {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("stamps.in");
		int K = in.nextInt();
		int N = in.nextInt();
		
		int[] stamps = new int[N];
		for (int i = 0; i < N; i++) {
			stamps[i] = in.nextInt();
		}
		
		int result = solve(K, stamps);
		
		PrintWriter pw = new PrintWriter(new File("stamps.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static int solve(int K, int[] stamps) {
		final int maxValue = 10000 * K;
		int[] count = new int[maxValue + 1];
		
		Arrays.fill(count, K);
		count[0] = 0;
		
		for (int totalValue = 1; totalValue < count.length; totalValue++) {
			boolean isPossible = false;
			
			for (int stamp : stamps) {
				int prevValue = totalValue - stamp;
				if (prevValue >= 0) {
					if (count[prevValue] + 1 <= count[totalValue]) {
						count[totalValue] = count[prevValue] + 1;
						isPossible = true;
					}
				}
			}
			
			if (!isPossible)
				return totalValue - 1;
		}
		
		return 0;
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
