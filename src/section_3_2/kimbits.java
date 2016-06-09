/*
ID: conchim1
LANG: JAVA
TASK: kimbits
 */

package section_3_2;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class kimbits {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("kimbits.in");
		int N = in.nextInt();
		int L = in.nextInt();
		int I = in.nextInt();
		
		String result = solve(N, L, I);
		
		PrintWriter pw = new PrintWriter(new File("kimbits.out"));
		pw.println(result);
		pw.close();
		in.close();
		
//		System.out.println(solve(5, 3, 19));
//		System.out.println(countOne(5));
	}
	
	static String solve(int N, int L, int I) {
		int number = I - 1;
		
		for (int l = 31; l >= L; l--) {
			while (countOne(number) > l) {
				number++;
			}
		}
		
		String result = Integer.toBinaryString(number);
		while (result.length() < N) {
			result = "0" + result;
		}
		
		return result;
	}
	
	static int countOne(int n) {
		int count = 0;
		
		for (int offset = 0; offset < 32; offset++) {
			if (((n >>> offset) & 1) == 1) {
				count++;
			}
		}
		
		return count;
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
