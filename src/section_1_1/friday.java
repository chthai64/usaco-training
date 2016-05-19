/*
ID: conchim1
LANG: JAVA
TASK: friday
 */

package section_1_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class friday {

	public static void main(String[] args) throws Exception {
		Input in = new Input(new FileInputStream(new File("friday.in")));
		int n = in.nextInt();
		String freq = getFreq(n);
		
		PrintWriter pw = new PrintWriter(new File("friday.out"));
		pw.println(freq);
		
		pw.close();
		in.close();
	}
	
	public static String getFreq(int n) {
		int[] frequency = new int[7];
		
		int year = 1900;
		int dayOfWeek = 2;
		
		while (year < 1900 + n) {
			int month = 1;
			
			while (month <= 12) {
				int dayOfMonth = 1;
				int totalDays = getDaysInMonth(year, month);
				
				while (dayOfMonth <= totalDays) {
					if (dayOfMonth == 13) {
						frequency[dayOfWeek]++;
					}
					
					dayOfMonth++;
					dayOfWeek = (++dayOfWeek > 6)? 0 : dayOfWeek;
				}
				month++;
			}
			year++;
		}
		
		String result = "";
		for (int i = 0; i < frequency.length; i++) {
			result += frequency[i];
			if (i != frequency.length - 1)
				result += " ";
		}
		return result;
	}
	
	static int getDaysInMonth(int year, int month) {
		if (month == 9 || month == 4 || month == 6 || month == 11)
			return 30;
		if (month == 2) {
			return isLeap(year)? 29 : 28;
		}
		return 31;
	}
	
	static int getTotalDays(int n) {
		int total = 0;
		int year = 1900;
		
		for (int i = 0; i < n; i++) {
			total += isLeap(year)? 365 : 366;
			year++;
		}
		
		return total;
	}
	
	static boolean isLeap(int year) {
		if (year % 100 == 0) {
			return year % 400 == 0;
		}
		
		return year % 4 == 0;
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
