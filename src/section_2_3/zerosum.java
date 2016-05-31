/*
ID: conchim1
LANG: JAVA
TASK: zerosum
 */

package section_2_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class zerosum {
	static final char PLUS = '+';
	static final char MINUS = '-';
	static final char BLANK = ' ';
	
	static final char[] ALL_SIGNS = {PLUS, MINUS, BLANK};

	public static void main(String[] args) throws Exception {
		Input in = fromFile("zerosum.in");
		int N = in.nextInt();
		String result = solve(N);
		
		PrintWriter pw = new PrintWriter(new File("zerosum.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static String solve(int N) {
		List<String> result = new ArrayList<String>();
		char[] signs = new char[N - 1];
		
		permute(result, signs, N, 0);
		
		Collections.sort(result);
		return getString(result);
	}
	
	static void permute(List<String> result, char[] signs, int N, int index) {
		if (index == signs.length) {
			if (sum(signs) == 0) {
				result.add(getString(signs));
			}
			
			return;
		}
		
		for (char sign : ALL_SIGNS) {
			signs[index] = sign;
			permute(result, signs, N, index + 1);
		}
	}
	
	static int sum(char[] signs) {
		int sum = 0;
		int number = 1;
		char lastSign = PLUS;
		int i = 0;
		
		while (i < signs.length && signs[i] == BLANK) {
			number *= 10;
			number += (i + 2);
			i++;
		}
		
		while (i < signs.length) {
			if (signs[i] == BLANK) {
				number *= 10;
				number += (i + 2);
			} else {
				if (lastSign == PLUS) {
					sum += number;
				} else {
					sum -= number;
				}
				
				lastSign = signs[i];
				number = (i + 2);
			}
			
			i++;
		}
		
		if (lastSign == PLUS) {
			sum += number;
		} else if (lastSign == MINUS) {
			sum -= number;
		}
		
		return sum;
	}
	
	static String getString(char[] signs) {
		StringBuilder sb = new StringBuilder();
		sb.append(1);
		
		for (int i = 0; i < signs.length; i++) {
			sb.append(signs[i] + "" + (i + 2));
		}
		
		return sb.toString();
	}
	
	static String getString(List<String> result) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < result.size(); i++) {
			sb.append(result.get(i));
			if (i != result.size() - 1)
				sb.append("\n");
		}
		
		return sb.toString();
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
