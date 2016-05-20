/*
ID: conchim1
LANG: JAVA
TASK: dualpal
 */

package section_1_2;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;


public class dualpal {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("dualpal.in");
		int N = in.nextInt();
		int S = in.nextInt();
		
		PrintWriter pw = new PrintWriter(new File("dualpal.out"));
		pw.println(solve(N, S));
		
		pw.close();
		in.close();
	}
	
	static String solve(int N, int S) {
		String result = "";
		int number = S + 1;
		
		for (int i = 0; i < N; i++) {
			while (!isDual(number)) {
				number++;
			}
			
			result += number++;
			if (i != N - 1)
				result += '\n';
		}
		
		return result;
	}
	
	static boolean isDual(int number) {
		int count = 0;
		
		for (int base = 2; base <= 10; base++) {
			String toBase = fromDecimal(number, base);
			if (isPalindrome(toBase))
				count++;
			
			if (count >= 2) {
				return true;
			}
		}
		
		return false;
	}
	
	static boolean isPalindrome(String s) {
		int left = 0;
		int right = s.length() - 1;
		
		while (left < right) {
			if (s.charAt(left) != s.charAt(right)) {
				return false;
			}
			
			left++;
			right--;
		}
		
		return true;
	}

	static String fromDecimal(int n, int base) {
		String result = "";
		
		while (n > 0) {
			result = (n % base) + result;
			n /= base;
		}
		
		return result;
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
