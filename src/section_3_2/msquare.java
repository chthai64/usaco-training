/*
ID: conchim1
LANG: JAVA
TASK: msquare
 */

package section_3_2;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class msquare {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("msquare.in");
		State target = new State(in.nextLine());
		
		String result = solve(target);
		
		PrintWriter pw = new PrintWriter(new File("msquare.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static String solve(State target) {
		State initial = new State();
		
		Map<String, State> mapStates = new HashMap<String, State>();
		Set<State> visited = new HashSet<State>();
		Queue<String> queue = new LinkedList<String>();
		
		queue.add("");
		mapStates.put("", initial);
		visited.add(initial);
		
		String shortestSeq = "";
		
		while (!queue.isEmpty()) {
			String seq = queue.poll();
			State state = mapStates.get(seq);
			
			if (state.equals(target)) {
				shortestSeq = seq;
				break;
			}
			
			for (char c = 'A'; c <= 'C'; c++) {
				State nextState = transform(state, c);
				String nextSeq = seq + c;
				
				if (!visited.contains(nextState)) {
					visited.add(nextState);
					queue.add(nextSeq);
					mapStates.put(nextSeq, nextState);
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		List<String> lines = splitString(shortestSeq, 60);
		
		sb.append(shortestSeq.length() + "\n");
		for (int i = 0; i < lines.size(); i++) {
			sb.append(lines.get(i));
			if (i != lines.size() - 1)
				sb.append("\n");
		}
		
		return sb.toString();
	}
	
	static List<String> splitString(String text, int splitAfter) {
		List<String> strings = new ArrayList<String>();
		int index = 0;
		
		while (index < text.length()) {
		    strings.add(text.substring(index, Math.min(index + splitAfter, text.length())));
		    index += splitAfter;
		}
		
		return strings;
	}
	
	static State transform(State state, char c) {
		switch (c) {
		case 'A': {
			int mat[][] = new int[2][4];
			mat[0] = state.mat[1];
			mat[1] = state.mat[0];
			return new State(mat);
		}
			
		case 'B': {
			int mat[][] = new int[2][4];
			
			for (int row = 0; row < 2; row++) {
				for (int col = 0; col < 4; col++) {
					if (col == 0) {
						mat[row][col] = state.mat[row][3];
					} else {
						mat[row][col] = state.mat[row][col - 1];
					}
				}
			}
			
			return new State(mat);
		}
		
		case 'C':
			int mat[][] = new int[2][4];
			for (int row = 0; row < 2; row++) {
				mat[row] = state.mat[row].clone();
			}
			
			mat[0][1] = state.mat[1][1];
			mat[0][2] = state.mat[0][1];
			mat[1][2] = state.mat[0][2];
			mat[1][1] = state.mat[1][2];
			
			return new State(mat);

		default:
			return null;
		}
	}
	
	static class State {
		int[][] mat;
		
		public State(String line) {
			mat = new int[2][4];
			
			String[] a = line.split(" ");
			for (int row = 0; row < 2; row++) {
				for (int col = 0; col < 4; col++) {
					if (row == 0) {
						mat[row][col] = Integer.parseInt(a[col]);
					} else {
						mat[row][3 - col] = Integer.parseInt(a[col + row * 4]);
					}
				}
			}
		}
		
		public State(int[][] mat) {
			this.mat = mat;
		}
		
		public State() {
			mat = new int[][] {
				{1,2,3,4},
				{8,7,6,5}
			};
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof State) {
				State state = (State) obj;
				return Arrays.deepEquals(mat, state.mat);
			}
			
			return false;
		}
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(new int[] {mat[0][0], mat[0][1], mat[0][2], mat[0][3],
											  mat[1][0], mat[1][1], mat[1][2], mat[1][3]});
		}
		
		@Override
		public String toString() {
			return Arrays.toString(mat[0]) + "\n" + Arrays.toString(mat[1]);
		}
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
