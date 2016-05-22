/*
ID: conchim1
LANG: JAVA
TASK: crypt1
 */

package section_1_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class crypt1 {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("crypt1.in");
		
		int n = in.nextInt();
		Integer[] digits = new Integer[n];
		for (int i = 0; i < n; i++) {
			digits[i] = in.nextInt();
		}
		
		int result = solve(new HashSet<Integer>(Arrays.asList(digits)));
		
		PrintWriter pw = new PrintWriter(new File("crypt1.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static int solve(Set<Integer> set) {
		int[] a = new int[5];
		return permute(set, a, 0);
	}
	
	static int permute(Set<Integer> set, int[] a, int index) {
		if (index == 5) {
			return check(set, a)? 1 : 0;
		}
		
		int count = 0;
		for (Integer digit : set) {
			a[index] = digit;
			count += permute(set, a, index + 1);
		}
		
		return count;
	}
	
	static boolean check(Set<Integer> set, int[] a) {
		int n = 0;
		for (int i = 0; i < 3; i++) {
			n *= 10;
			n += a[i];
		}
		
		int p1 = n * a[4];
		if (p1 >= 1000 || !checkNumber(set, p1))
			return false;
		
		int p2 = n * a[3];
		if (p2 >= 1000 || !checkNumber(set, p2))
			return false;
		
		int sum = p1 + p2*10;
		
		if (sum >= 10000)
			return false;
		
		return checkNumber(set, sum);
	}
	
	static boolean checkNumber(Set<Integer> set, int number) {
		String s1 = "" + number;
		for (char c : s1.toCharArray()) {
			int i = c - '0';
			if (!set.contains(i))
				return false;
		}
		
		return true;
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
