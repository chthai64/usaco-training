/*
ID: conchim1
LANG: JAVA
TASK: runround
 */

package section_2_2;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class runround {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("runround.in");
		long M = in.nextLong();
		long result = solve(M);
		
		PrintWriter pw = new PrintWriter(new File("runround.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static long solve(long M) {
		int digits = (M + "").length();
		
		for (int totalDigits = digits; totalDigits <= 9; totalDigits++) {
			long result = permute(new boolean[10], 0, 0, totalDigits, M);
			if (result >= 0)
				return result;
		}
		
		return 0;
	}
	
	static long permute(boolean[] usedNumbers, long number, int index, int totalDigits, long M) {
		if (index == totalDigits) {
			if (number > M && isValid(number))
				return number;
			
			return -1;
		}
		
		for (char digit = '0'; digit <= '9'; digit++) {
			if (!usedNumbers[digit - '0']) {
				usedNumbers[digit - '0'] = true;
				
				long result = permute(usedNumbers, number * 10 + (digit - '0'), index + 1, totalDigits, M);
				if (result >= 0)
					return result;
				
				usedNumbers[digit - '0'] = false;
			}
		}
		
		return -1;
	}
	
	static boolean isValid(long n) {
		char[] a = (n + "").toCharArray();
		boolean[] visitedPos = new boolean[a.length];
		boolean[] usedNumbers = new boolean[10];
		
		int currPos = 0;
		int count = 0;
		
		while (!visitedPos[currPos] && !usedNumbers[a[currPos] - '0']) {
			visitedPos[currPos] = true;
			usedNumbers[a[currPos] - '0'] = true;
			currPos = (currPos + (a[currPos] - '0')) % a.length;
			count++;
		}
		
		if (currPos != 0)
			return false;
		
		return count == a.length;
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
