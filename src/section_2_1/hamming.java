/*
ID: conchim1
LANG: JAVA
TASK: hamming
 */

package section_2_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class hamming {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("hamming.in");
		
		int N = in.nextInt();
		int B = in.nextInt();
		int D = in.nextInt();
		
		String result = solve(N, B, D);
		
		PrintWriter pw = new PrintWriter(new File("hamming.out"));
		pw.println(result);
		pw.close();
		in.close();
	}

	static String solve(int N, int B, int D) {
		List<Integer> chosen = new ArrayList<Integer>();
		recurse(N, B, D, chosen, 0);
		
		String result = "";
		
		for (int i = 0; i < chosen.size(); i += 10) {
			for (int j = i; j < Math.min(i + 10, chosen.size()); j++) {
				result += chosen.get(j);
				if (j != Math.min(i + 10, chosen.size()) - 1)
					result += " ";
			}
			result += '\n';
		}
		
		result =  result.substring(0, result.length() - 1);
		return result;
	}
	
	static boolean recurse(int N, int B, int D, List<Integer> chosen, int startNumber) {
		if (chosen.size() == N)
			return true;
		
		if (startNumber > Math.pow(2, B))
			return false;
		
		for (int number = startNumber; number <= Math.pow(2, B); number++) {
			if (shouldAdd(B, D, chosen, number)) {
				chosen.add(number);
				
				boolean done = recurse(N, B, D, chosen, number + 1);
				if (done) {
					return true;
				}
				
				chosen.remove(chosen.size() - 1);
			}
		}
		
		return false;
	}
	
	static boolean shouldAdd(int B, int D,  List<Integer> chosen, int number) {
		for (int other : chosen) {
			if (getDistance(B, number, other) < D)
				return false;
		}
		
		return true;
	}
	
	static int getDistance(int B, int a, int b) {
		int distance = 0;
		
		for (int offset = 0; offset < B; offset++) {
			if ((a & 1) != (b & 1)) {
				distance++;
			}
			
			a = a >> 1;
			b = b >> 1;
		}
		
		return distance;
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
