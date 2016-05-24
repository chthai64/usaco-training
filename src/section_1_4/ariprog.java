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
		Input in = fromFile("ariprog.in");
		
		int N = in.nextInt();
		int M = in.nextInt();
		String result = solve(N, M);
		
		PrintWriter pw = new PrintWriter(new File("ariprog.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static String solve(int N, int M) {
		boolean[] correct = getNumberSet(M);
		
		int MAX_VALUE = Integer.MIN_VALUE;
		for (int i = correct.length - 1; i >= 0; i--) {
			if (correct[i]) {
				MAX_VALUE = i;
				break;
			}
		}
		
		List<Sequence> seqList = new ArrayList<Sequence>();
		
		for (int a = 0; a <= MAX_VALUE - N; a++) {
			if (!correct[a])
				continue;
			
			int b = 1;
			
			while (a + (N - 1) * b <= MAX_VALUE) {
				Sequence seq = new Sequence(a, Integer.MAX_VALUE);
				boolean valid = true;
				int prev = -1;
				
				for (int n = 0; n < N && valid; n++) {
					int number = a + n*b;
					if (correct[number]) {
						if (prev >= 0) {
							seq.diff = Math.min(number - prev, seq.diff);
						}
						prev = number;
					} else {
						valid = false;
					}
				}
				
				if (valid) {
					seqList.add(seq);
				}
				
				b++;
			}
		}
		
		
		Collections.sort(seqList);
		return toString(seqList);
	}
	
	static String toString(List<Sequence> seqList) {
		if (seqList.isEmpty())
			return "NONE";
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < seqList.size(); i++) {
			Sequence seq = seqList.get(i);
			sb.append(seq.first + " " + seq.diff);
			if (i != seqList.size() - 1) {
				sb.append("\n");
			}
		}
		
		return sb.toString();
	}

	static boolean[] getNumberSet(int M) {
		boolean[] a = new boolean[125000 + 1];
		
		for (int p = 0; p <= M; p++) {
			for (int q = 0; q <= M; q++) {
				int number = p*p + q*q;
				a[number] = true;
			}
		}
		
		return a;
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

		@Override
		public String toString() {
			return "[first=" + first + ", diff=" + diff + "]";
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
