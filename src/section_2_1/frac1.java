/*
ID: conchim1
LANG: JAVA
TASK: frac1
 */

package section_2_1;

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
		List<Fraction> list = new ArrayList<Fraction>();
		Fraction start = new Fraction(0, 1);
		Fraction end = new Fraction(1, 1);
		
		list.add(start);
		recurse(list, start, end, N);
		list.add(end);
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i).toString());
			if (i != list.size() - 1)
				sb.append("\n");
		}
		
		return sb.toString();
	}
	
	static void recurse(List<Fraction> list, Fraction frac1, Fraction frac2, int N) {
		if (frac1.denominator + frac2.denominator > N)
			return;
		
		Fraction mid = new Fraction(frac1.numerator + frac2.numerator,
				frac1.denominator + frac2.denominator);
		
		recurse(list, frac1, mid, N);
		list.add(mid);
		recurse(list, mid, frac2, N);
	}
	
	static class Fraction implements Comparable<Fraction> {
		int numerator;
		int denominator;
		
		public Fraction(int numerator, int demoninator) {
			this.numerator = numerator;
			this.denominator = demoninator;
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
