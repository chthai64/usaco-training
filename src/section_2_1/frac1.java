/*
ID: conchim1
LANG: JAVA
TASK: frac1
 */

//package section_2_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
	
public class frac1 {

	public static void main(String[] args) throws Exception {
		Input in  = fromFile("frac1.in");
		int N = in.nextInt();
		String result = solve(N);
		
		PrintWriter pw = new PrintWriter(new File("frac1.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static String solve(int N) {
		Set<Fraction> set = new HashSet<Fraction>();
		set.add(new Fraction(0, 1));
		set.add(new Fraction(1, 1));
		
		for (int denominator = 1; denominator <= N; denominator++) {
			for (int numerator = 1; numerator < denominator; numerator++) {
				set.add(new Fraction(numerator, denominator));
			}
		}
		
		List<Fraction> list = new ArrayList<Fraction>(set);
		Collections.sort(list);
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i).toString());
			if (i != list.size() - 1)
				sb.append("\n");
		}
		
		return sb.toString();
	}

	static int gcd(int a, int b) {
		if (b == 0)
			return a;
		return gcd(b, a%b);
	}
	
	static class Fraction implements Comparable<Fraction> {
		int numerator;
		int denominator;
		
		public Fraction(int numerator, int demoninator) {
			int gcd = gcd(numerator, demoninator);
			this.numerator = numerator / gcd;
			this.denominator = demoninator / gcd;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Fraction) {
				Fraction frac = (Fraction) obj;
				return numerator == frac.numerator && denominator == frac.denominator;
			}
			return false;
		}
		
		public double getValue() {
			return (double) numerator / denominator;
		}
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(new int[] {numerator, denominator});
		}
		
		@Override
		public int compareTo(Fraction frac) {
			return ((Double) getValue()).compareTo(frac.getValue());
		}
		
		@Override
		public String toString() {
			return numerator + "/" + denominator;
		}
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
