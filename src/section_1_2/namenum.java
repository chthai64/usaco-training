/*
ID: conchim1
LANG: JAVA
TASK: namenum
 */

package section_1_2;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class namenum {

	static Map<Character, Character> map = new HashMap<Character, Character>();
	static {
		map.put('A', '2');
		map.put('B', '2');
		map.put('C', '2');
		
		map.put('D', '3');
		map.put('E', '3');
		map.put('F', '3');
		
		map.put('G', '4');
		map.put('H', '4');
		map.put('I', '4');
		
		map.put('J', '5');
		map.put('K', '5');
		map.put('L', '5');
		
		map.put('M', '6');
		map.put('N', '6');
		map.put('O', '6');
		
		map.put('P', '7');
		map.put('R', '7');
		map.put('S', '7');
		
		map.put('T', '8');
		map.put('U', '8');
		map.put('V', '8');
		
		map.put('W', '9');
		map.put('X', '9');
		map.put('Y', '9');
	}
	
	public static void main(String[] args) throws Exception {
		Input in = fromFile("namenum.in");
		String number = in.nextLine();
		
		Input inDict = fromFile("dict.txt");
		List<String> dict = new ArrayList<String>();
		
		String next = inDict.nextLine();
		while (next != null) {
			dict.add(next);
			next = inDict.nextLine();
		}
		
		String result = solve(number, dict);
		
		PrintWriter pw = new PrintWriter(new File("namenum.out"));
		pw.println(result);
		pw.close();
		in.close();
		inDict.close();
	}
	
	public static String solve(String number, List<String> dict) {
		List<String> list = new ArrayList<String>();
		
		for (String word : dict) {
			if (word.length() != number.length())
				continue;
			
			boolean valid = true;
			for (int i = 0; i < word.length(); i++) {
				if (!map.containsKey(word.charAt(i)) || map.get(word.charAt(i)) != number.charAt(i)) {
					valid = false;
					break;
				}
			}
			
			if (valid) {
				list.add(word);
			}
		}
		
		if (list.isEmpty()) {
			return "NONE";
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
			if (i != list.size() - 1) {
				sb.append("\n");
			}
		}
		return sb.toString();
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
