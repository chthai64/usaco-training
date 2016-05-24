/*
ID: conchim1
LANG: JAVA
TASK: milk3
 */

package section_1_4;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class milk3 {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("milk3.in");
		
		int A = in.nextInt();
		int B = in.nextInt();
		int C = in.nextInt();
		String result = solve(A, B, C);
		
		PrintWriter pw = new PrintWriter(new File("milk3.out"));
		pw.println(result);
		pw.close();
		in.close();
	}

	static String solve(int A, int B, int C) {
		List<Integer> list = new ArrayList<Integer>();
		State allFull = new State(A, B, C);
		
		State start = new State(0, 0, C);
		Set<State> visited = new HashSet<State>();
		Queue<State> queue = new LinkedList<State>();
		
		visited.add(start);
		queue.add(start);
		
		while (!queue.isEmpty()) {
			State state = queue.poll();
			if (isTarget(state)) {
				list.add(state.array[2]);
			}
			
			for (State nextState : getNeighbors(state, allFull)) {
				if (!visited.contains(nextState)) {
					visited.add(nextState);
					queue.add(nextState);
				}
			}
		}
		
		Collections.sort(list);
		return toString(list);
	}
	
	static List<State> getNeighbors(State state, State fullState) {
		List<State> list = new ArrayList<State>();
		
		for (int first = 0; first < 2; first++) {
			for (int second = first + 1; second < 3; second++) {
				// forward direction
				int milkToPour = Math.min(state.array[first], fullState.array[second] - state.array[second]);
				
				int[] arrayForward = state.array.clone();
				arrayForward[first] -= milkToPour;
				arrayForward[second] += milkToPour;
				list.add(new State(arrayForward));
				
				// backward direction
				milkToPour = Math.min(fullState.array[first] - state.array[first], state.array[second]);
				
				int[] arrayBackward = state.array.clone();
				arrayBackward[first] += milkToPour;
				arrayBackward[second] -= milkToPour;
				list.add(new State(arrayBackward));
			}
		}
		
		return list;
	}
	
	static boolean isTarget(State state) {
		return state.array[0] == 0;
	}
	
	static String toString(List<Integer> list) {
		String s = "";
		
		for (int i = 0; i < list.size(); i++) {
			s += list.get(i);
			if (i != list.size() - 1) {
				s += " ";
			}
		}
		
		return s;
	}
	
	static class State {
		int[] array = new int[3];
		
		public State(int a, int b, int c) {
			array[0] = a;
			array[1] = b;
			array[2] = c;
		}
		
		public State(int[] array) {
			this.array = array;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof State) {
				State state = (State) obj;
				return Arrays.equals(array, state.array);
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(array);
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
