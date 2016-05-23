/*
ID: conchim1
LANG: JAVA
TASK: combo
 */

package section_1_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class combo {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("combo.in");
		int N = in.nextInt();
		State lock1 = new State(in.nextLine());
		State lock2 = new State(in.nextLine());
		
		int result = solve(N, lock1, lock2);
		
		PrintWriter pw = new PrintWriter(new File("combo.out"));
		pw.println(result);
		pw.close();
		in.close();
		
//		test();
	}
	
	static void test() {
		State lock1 = new State(1,1,1);
		State lock2 = new State(1,1,1);
		int N = 1;
		
		System.out.println(solve(N, lock1, lock2));
	}
	
	public static int solve(int N, State lock1, State lock2) {
		Set<State> set = new HashSet<State>();
		
		for (int deltaA = -2; deltaA <= 2; deltaA++) {
			for (int deltaB = -2; deltaB <= 2; deltaB++) {
				for (int deltaC = -2; deltaC <= 2; deltaC++) {
					State unlock1 = new State(
							mod(lock1.a + deltaA, N),
							mod(lock1.b + deltaB, N),
							mod(lock1.c + deltaC, N)
					);
					
					State unlock2 = new State(
							mod(lock2.a + deltaA, N),
							mod(lock2.b + deltaB, N),
							mod(lock2.c + deltaC, N)
					);
					
					if (valid(unlock1, N))
						set.add(unlock1);
					if (valid(unlock2, N))
						set.add(unlock2);
				}
			}
		}
		
		return set.size();
	}
	
	static boolean valid(State state, int N) {
		if (state.a <= 0 || N < state.a)
			return false;
		
		if (state.b <= 0 || N < state.b)
			return false;
		
		if (state.c <= 0 || N < state.c)
			return false;
		
		return true;
	}
	
	static int mod(int a, int b) {
		if (a <= 0) {
			a = b + a;
		}
		
		if (a > b) {
			a++;
		}
		
		return a % (b + 1);
	}

	static class State {
		int a,b,c;
		
		public State(String s) {
			String[] split = s.split(" ");
			a = Integer.parseInt(split[0]);
			b = Integer.parseInt(split[1]);
			c = Integer.parseInt(split[2]);
		}
		
		public State(int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof State) {
				State state = (State) obj;
				return a == state.a && b == state.b && c == state.c;
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(new int[] {a,b,c});
		}
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
