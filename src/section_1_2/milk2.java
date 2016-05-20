/*
ID: conchim1
LANG: JAVA
TASK: milk2
 */

//package section_1_2;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class milk2 {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("milk2.in");
		int n = in.nextInt();
		
		Interval[] a = new Interval[n];
		for (int i = 0; i < n; i++) {
			a[i] = new Interval(in.nextInt(), in.nextInt());
		}
		
		PrintWriter pw = new PrintWriter(new File("milk2.out"));
		pw.println(solve(a));
		pw.close();
		in.close();
	}
	
	static String solve(Interval[] a) {
		Arrays.sort(a, new Comparator<Interval>() {
			@Override
			public int compare(Interval a, Interval b) {
				Integer n = a.end;
				Integer m = b.end;
				return n.compareTo(m);
			}
		});
		
		int longestMilking = 0;
		int longestIdle = 0;
		
		int earliestStart = a[0].start;
		int latestEnd = a[0].end;
		
		for (Interval interval : a) {
			if (interval.start <= latestEnd) {
				earliestStart = Math.min(earliestStart, interval.start);
				latestEnd = interval.end;
			} else {
				longestIdle = Math.max(interval.start - latestEnd, longestIdle);
				earliestStart = interval.start;
				latestEnd = interval.end;
			}
			
			longestMilking = Math.max(longestMilking, latestEnd - earliestStart);
		}
		
		return longestMilking + " " + longestIdle;
	}
	
	static class Interval {
		int start;
		int end;
		
		public Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}
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
