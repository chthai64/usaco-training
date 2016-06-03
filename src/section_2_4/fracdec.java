/*
ID: conchim1
LANG: JAVA
TASK: fracdec
 */

package section_2_4;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class fracdec {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("fracdec.in");
		int N = in.nextInt();
		int D = in.nextInt();
		
		String result = solve(N, D);
		
		PrintWriter pw = new PrintWriter(new File("fracdec.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static String solve(int N, int D) {
		List<Integer> decimals = new ArrayList<Integer>();
		List<Integer> fractions = new ArrayList<Integer>();
		
		Map<Integer, Integer> nToFMap = new HashMap<Integer, Integer>();
		boolean onFraction = false;
		int repeatIndex = -1;
		
		while (N != 0) {
			if (N < D) {
				onFraction = true;
				N *= 10;
			}

			if (onFraction && nToFMap.containsKey(N)) {
				repeatIndex = nToFMap.get(N);
				break;
			}
			
			int mul = N / D;
			int remain = N % D;
			
			if (onFraction) {
				fractions.add(mul);
				nToFMap.put(N, fractions.size() - 1);
			} else {
				decimals.add(mul);
			}
			
			N = remain;
		}
		
		StringBuilder sb = new StringBuilder();
		if (decimals.isEmpty()) {
			sb.append("0");
		} else {
			for (int i : decimals) {
				sb.append(i);
			}
		}
		sb.append('.');
		
		if (fractions.isEmpty()) {
			sb.append("0");
		} else {
			if (repeatIndex == -1) {
				for (int i : fractions) {
					sb.append(i);
				}
			} else {
				for (int i = 0; i < repeatIndex; i++) {
					sb.append(fractions.get(i));
				}
				sb.append('(');
				
				for (int i = repeatIndex; i < fractions.size(); i++) {
					sb.append(fractions.get(i));
				}
				
				sb.append(')');
			}
		}
		
		StringBuilder result = new StringBuilder();
		
		for (int i = 0; i < sb.length(); i++) {
			if (i != 0 && i % 76 == 0) {
				result.append('\n');
			}
			
			result.append(sb.charAt(i));
		}
		
		return result.toString();
	}
	
	/**
	 * Assume that N is always greater or equal to D.
	 */
	static int numberToMul(int N, int D) {
		return N / D;
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
