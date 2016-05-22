/*
ID: conchim1
LANG: JAVA
TASK: barn1
 */

package section_1_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;


public class barn1 {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("barn1.in");
		
		int M = in.nextInt();
		int S = in.nextInt();
		int C = in.nextInt();
		
		int[] occupied = new int[C];
		for (int i = 0; i < C; i++) {
			occupied[i] = in.nextInt();
		}
		
		int result = solve(M, S, occupied);
		
		PrintWriter pw = new PrintWriter(new File("barn1.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	public static int solve(int maxBoards, int totalStalls, int[] occupied) {
		Arrays.sort(occupied);
		List<Integer> gaps = new ArrayList<Integer>();
		
		for (int i = 1; i < occupied.length; i++) {
			gaps.add(occupied[i] - occupied[i - 1] - 1);
		}
		Collections.sort(gaps);
		
		int covered = occupied[occupied.length - 1] - occupied[0] + 1;
		int board = 1;
		int gapIndex = gaps.size() - 1;
		
		while (board < maxBoards && gapIndex >= 0) {
			covered -= gaps.get(gapIndex);
			board++;
			gapIndex--;
		}
		
		return covered;
	}

	private static Input fromFile(String path) throws IOException {
		return new Input(new FileInputStream(new File(path)));
	}

	static class Input {
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
