/*
ID: conchim1
LANG: JAVA
TASK: fact4
 */

package section_3_2;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class fact4 {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("fact4.in");
		
		int N = in.nextInt();
		int result = solve(N);
		
		PrintWriter pw = new PrintWriter(new File("fact4.out"));
		pw.println(result);
		pw.close();
		in.close();
	}

	static int solve(int N) {
		int number = 1;
		
		for (int mul = 2; mul <= N; mul++) {
			int mulFive = mul;
			int countFive = 0;
			while (mulFive % 5 == 0) {
				mulFive /= 5;
				countFive++;
			}

			int reduceMul = mul;
			for (int i = 0; i < countFive; i++) {
				reduceMul /= 5;
				number /= 2;
			}
			
			number *= reduceMul;
			number %= 1000;
		}
		
		return number % 10;
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
