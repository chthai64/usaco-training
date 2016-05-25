/*
ID: conchim1
LANG: JAVA
TASK: sprime
 */

package section_1_5;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class sprime {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("sprime.in");
		
		int N = in.nextInt();
		String result = solve(N);
		
		PrintWriter pw = new PrintWriter(new File("sprime.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static String solve(int N) {
		List<Integer> result = new ArrayList<Integer>();
		permute(result, N, 0, 1);
		Collections.sort(result);
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < result.size(); i++) {
			sb.append(result.get(i));
			if (i != result.size() - 1)
				sb.append('\n');
		}
		
		return sb.toString();
	}
	
	static void permute(List<Integer> list, int N, int number, int digitCount) {
		if (digitCount > N) {
			list.add(number);
			return;
		}
		
		for (int digit = 0; digit <= 9; digit++) {
			int nextNumber = number;
			nextNumber *= 10;
			nextNumber += digit;
			
			if (isPrime(nextNumber)) {
				permute(list, N, nextNumber, digitCount + 1);
			}
		}
	}
	
	static boolean isPrime(int n) {
		if (n < 2)
			return false;
		
		for (int i = 2; i*i <= n; i++) {
			if (n % i == 0)
				return false;
		}
		
		return true;
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
