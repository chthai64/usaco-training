/*
ID: conchim1
LANG: JAVA
TASK: rockers
 */

package section_3_4;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class rockers {

	public static void main(String[] args) throws Exception {
//		Input in = fromFile("rockers.in");
//		
//		int N = in.nextInt();
//		int T = in.nextInt();
//		int M = in.nextInt();
//		
//		int[] songs = new int[N];
//		for (int i = 0; i < N; i++) {
//			songs[i] = in.nextInt();
//		}
//		
//		int result = solve(songs, M, T);
//		
//		PrintWriter pw = new PrintWriter(new File("rockers.out"));
//		pw.println(result);
//		pw.close();
//		in.close();
	}

	static int solve(int[] songs, int M, int T) {
		
		
		return 0;
	}
	
	static int recurse(int[] songs, boolean[] selected, int M, int T, int currentSet, int timeLeft) {
		if (currentSet > M)
			return 0;
		
		int ans = 0;
		
		return ans;
	}

	// Helper methods, classes
	static List<String> splitString(String text, int splitAfter) {
		List<String> strings = new ArrayList<String>();
		int index = 0;

		while (index < text.length()) {
			strings.add(text.substring(index, Math.min(index + splitAfter, text.length())));
			index += splitAfter;
		}

		return strings;
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
