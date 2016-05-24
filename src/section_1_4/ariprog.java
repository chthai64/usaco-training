/*
ID: conchim1
LANG: JAVA
TASK: ariprog
 */

package section_1_4;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class ariprog {

	public static void main(String[] args) throws Exception {
//		Input in = fromFile("airprog.in");
//		
//		int N = in.nextInt();
//		int M = in.nextInt();
//		String result = solve(N, M);
//		
//		PrintWriter pw = new PrintWriter(new File("aairprog.out"));
//		pw.println(result);
//		pw.close();
//		in.close();
		
		
		Set<Integer> set = getNumberSet(250);
		long start = System.currentTimeMillis();
		
		for (int a = 0; a <= 10000; a++) {
			for (int b = 0; b <= 10000; b++) {
				int count = a*b;
				set.contains(count);
			}
		}
		long total = System.currentTimeMillis() - start;
		System.out.println(total + " ms");
		
	}
	
	static String solve(int N, int M) {
		Set<Integer> set = getNumberSet(M);
		
		for (int a = 0; a <= 10000; a++) {
			for (int b = 0; b <= 10000; b++) {
				int count = a*b;
			}
		}
		
		return null;
	}

	static Set<Integer> getNumberSet(int M) {
		Set<Integer> set = new HashSet<Integer>();
		
		for (int p = 0; p <= M; p++) {
			for (int q = 0; q <= M; q++) {
				int number = p*p + q*q;
				set.add(number);
			}
		}
		
		return set;
	}
	
	static class State {
		int a, b;
		
		public State(int a, int b) {
			this.a = a;
			this.b = b;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof State) {
				State state = (State) obj;
				return a == state.a && b == state.b;
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(new int[] {a,b});
		}
	}
	
	static class Sequence implements Comparable<Sequence> {
		int first;
		int diff;
		
		public Sequence(int first, int diff) {
			this.first = first;
			this.diff = diff;
		}
		
		@Override
		public int compareTo(Sequence o) {
			if (diff > o.diff)
				return 1;
			if (diff < o.diff)
				return-1;
			
			return ((Integer) first).compareTo(o.first);
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
