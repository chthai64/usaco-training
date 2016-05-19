/*
ID: conchim1
LANG: JAVA
TASK: ride
 */
package section_1_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class ride {

	public static void main(String[] args) throws IOException {
		Input input = new Input(new FileInputStream(new File("ride.in")));
		
		String comet = input.nextLine();
		String group = input.nextLine();
		
		PrintWriter pw = new PrintWriter(new File("ride.out"));
		pw.println(solve(comet, group));
		pw.close();
		input.close();
	}
	
	static String solve(String comet, String group) {
		if (getMod(comet) == getMod(group))
			return "GO";
		return "STAY";
	}
	
	static int getMod(String s) {
		int totalMul = 1;
		for (char c : s.toCharArray()) {
			totalMul *= (c - 'A' + 1);
		}
		return totalMul % 47;
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
