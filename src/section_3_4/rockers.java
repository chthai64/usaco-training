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
		Input in = fromFile("rockers.in");
		
		int N = in.nextInt();
		int T = in.nextInt();
		int M = in.nextInt();
		
		int[] songs = new int[N];
		for (int i = 0; i < N; i++) {
			songs[i] = in.nextInt();
		}
		
		int result = solve(songs, M, T);
		
		PrintWriter pw = new PrintWriter(new File("rockers.out"));
		pw.println(result);
		pw.close();
		in.close();
	}

	static int solve(int[] songs, int M, int T) {
		boolean[] selected = new boolean[songs.length];
		return recurse(songs, selected, M, T, 1, T);
	}
	
	static int recurse(int[] songs, boolean[] selected, int M, int T, int currentSet, int timeLeft) {
		int count = 0;
		for (boolean b : selected) {
			if (b) {
				count++;
			}
		}
		
		for (int i = 0; i < selected.length; i++) {
			if (!selected[i]) {
				selected[i] = true;
				if (songs[i] <= timeLeft) {
					count = Math.max(count, recurse(songs, selected, M, T, currentSet, timeLeft - songs[i]));
				}
				
				if (currentSet < M && songs[i] <= T) {
					count = Math.max(count, recurse(songs, selected, M, T, currentSet + 1, T - songs[i]));
				}
				selected[i] = false;
			}
		}
		
		return count;
	}
	
	static class State {
		boolean[] selected;
		int currentSet, timeLeft;
		
		public State(boolean[] selected, int currentSet, int timeLeft) {
			this.selected = selected.clone();
			this.currentSet = currentSet;
			this.timeLeft = timeLeft;
		}
		
		@Override
		public int hashCode() {
			return currentSet * 31 + timeLeft;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof State) {
				State state = (State) obj;
				
				return Arrays.equals(selected, state.selected)
						&& currentSet == state.currentSet 
						&& timeLeft == state.timeLeft;
			}
			
			return false;
		}
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
