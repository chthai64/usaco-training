/*
ID: conchim1
LANG: JAVA
TASK: pprime
 */

package section_1_5;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class pprime {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("pprime.in");
		int a = in.nextInt();
		int b = in.nextInt();
		String result = solve(a,b);
		
		PrintWriter pw = new PrintWriter(new File("pprime.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static String solve(int a, int b) {
		List<Integer> palindList = generatePalind(b);
		List<Integer> result = new ArrayList<Integer>();
		
		for (int n : palindList) {
			if (a <= n && n <= b && isPrime(n)) {
				result.add(n);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < result.size(); i++) {
			sb.append(result.get(i));
			if (i != result.size() - 1)
				sb.append('\n');
		}
		
		return sb.toString();
	}
	
	static List<Integer> generatePalind(int max) {
		List<Integer> list = new ArrayList<Integer>();
		int maxDigits = (max + "").length();
		
		for (int digitCount = 1; digitCount <= maxDigits; digitCount++) {
			generate(list, new int[digitCount], 0);
		}
		
		Collections.sort(list);
		return list;
	}
	
	static void generate(List<Integer> list, int[] a, int index) {
		if (index == 0) {
			for (int d = 1; d <= 9; d += 2) {
				a[index] = a[a.length - 1] = d;
				generate(list, a, index + 1);
			}
			return;
		}
		
		if ((a.length % 2 == 0 && index >= a.length / 2) || (a.length % 2 != 0 && index > a.length / 2)) {
			int number = 0;
			
			for (int d : a) {
				number *= 10;
				number += d;
			}
			
			list.add(number);
			
		} else {
			for (int d = 0; d <= 9; d++) {
				a[index] = a[a.length - index - 1] = d;
				generate(list, a, index + 1);
			}
		}
	}
	
	static boolean isPrime(int n) {
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
