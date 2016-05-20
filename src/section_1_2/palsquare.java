/*
ID: conchim1
LANG: JAVA
TASK: palsquare
 */

package section_1_2;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class palsquare {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("palsquare.in");
		int base = in.nextInt();
		String result = solve(base);
		
		PrintWriter pw = new PrintWriter(new File("palsquare.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static String solve(int base) {
		StringBuilder sb = new StringBuilder();
		
		for (int n = 1; n <= 300; n++) {
			int square = n*n;
			String toBase = fromDecimal(square, base);
			
			if (isPalidrome(toBase)) {
				if (sb.length() != 0) {
					sb.append("\n");
				}
				sb.append(fromDecimal(n, base) + " " + toBase);
			}
		}
		
		return sb.toString();
	}
	
	static String fromDecimal(int n, int base) {
		String s = "";
		
		while (n > 0) {
			int digit = n % base;
			
			if (digit > 9) {
				s = (char) ('A' + digit - 10)  + s;
			} else {
				s = digit + s;
			}

			n /= base;
		}
		
		return s;
	}
	
	static boolean isPalidrome(String s) {
		int left = 0;
		int right = s.length() - 1;
		
		while (left < right) {
			if (s.charAt(left) != s.charAt(right))
				return false;
			
			left++;
			right--;
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
