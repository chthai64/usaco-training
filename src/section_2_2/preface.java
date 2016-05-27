/*
ID: conchim1
LANG: JAVA
TASK: preface
 */

package section_2_2;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class preface {
	static final Map<Integer, String> map = new HashMap<Integer, String>();
	static final TreeSet<Integer> set = new TreeSet<Integer>();
	
	static {
		map.put(1, "I");
		map.put(4, "IV");
		map.put(5, "V");
		map.put(9, "IX");
		map.put(10, "X");
		map.put(40, "XL");
		map.put(50, "L");
		map.put(90, "XC");
		map.put(100, "C");
		map.put(400, "CD");
		map.put(500, "D");
		map.put(900, "CM");
		map.put(1000, "M");
		
		set.addAll(map.keySet());
	}

	public static void main(String[] args) throws Exception {
		Input in = fromFile("preface.in");
		int N = in.nextInt();
		String result = solve(N);
		
		PrintWriter pw = new PrintWriter(new File("preface.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static String solve(int N) {
		Map<Character, Integer> countMap = new HashMap<Character, Integer>();
		
		for (int n = 1; n <= N; n++) {
			String roman = toRoman(n);
			
			for (char c : roman.toCharArray()) {
				int count = countMap.containsKey(c)? countMap.get(c) : 0;
				count++;
				countMap.put(c, count);
			}
		}
		
		String result = "";
		if (countMap.containsKey('I'))
			result += "I " + countMap.get('I') + "\n";
		
		if (countMap.containsKey('V'))
			result += "V " + countMap.get('V') + "\n";
		
		if (countMap.containsKey('X'))
			result += "X " + countMap.get('X') + "\n";
					
		if (countMap.containsKey('L'))
			result += "L " + countMap.get('L') + "\n";
		
		if (countMap.containsKey('C'))
			result += "C " + countMap.get('C') + "\n";
		
		if (countMap.containsKey('D'))
			result += "D " + countMap.get('D') + "\n";
		
		if (countMap.containsKey('M'))
			result += "M " + countMap.get('M') + "\n";
		
		return result.substring(0, result.length() - 1);
	}
	
	static String toRoman(int n) {
		StringBuilder sb = new StringBuilder();
		
		while (n > 0) {
			int romanValue = set.floor(n);
			sb.append(map.get(romanValue));
			n -= romanValue;
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
