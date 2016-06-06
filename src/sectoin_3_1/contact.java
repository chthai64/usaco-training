/*
ID: conchim1
LANG: JAVA
TASK: contact
 */

package sectoin_3_1;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.text.*;
import java.math.*;

public class contact {

	public static void main(String[] args) throws Exception {
		// C:/Users/Chau Thai/Desktop/contact.in
		Input in = fromFile("contact.in");
		
		int A = in.nextInt();
		int B = in.nextInt();
		int N = in.nextInt();
		
		StringBuilder sb = new StringBuilder();
		String line = in.nextLine();
		while (line != null) {
			sb.append(line);
			line = in.nextLine();
		}
		String data = sb.toString();
		
		String result = solve(A, B, N, data);
		
		PrintWriter pw = new PrintWriter(new File("contact.out"));
		pw.println(result);
		pw.close();
		in.close();
	}

	static String solve(int A, int B, int N, String data) {
		Map<String, Integer> mapCount = new HashMap<String, Integer>();
		
		for (int i = 0; i < data.length(); i++) {
			for (int patternLength = A; patternLength <= B; patternLength++) {
				if (i + patternLength <= data.length()) {
					String pattern = data.substring(i, i + patternLength);
					int count = mapCount.containsKey(pattern)? mapCount.get(pattern) + 1 : 1;
					mapCount.put(pattern, count);
				}
			}
		}
		
		Map<Integer, List<String>> map = new TreeMap<Integer, List<String>>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return -(o1.compareTo(o2));
			}
		});
		
		for (Map.Entry<String, Integer> entry : mapCount.entrySet()) {
			int count = entry.getValue();
			String pattern = entry.getKey();
			
			List<String> list = (map.containsKey(count))? map.get(count) : new ArrayList<String>();
			list.add(pattern);
			map.put(count, list);
		}
		
		StringBuilder sb = new StringBuilder();
		
		int count = 0;
		for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
			if (count == N)
				break;
			
			List<String> list = entry.getValue();
			Collections.sort(list, new Comparator<String>() {
				@Override
				public int compare(String a, String b) {
					if (a.length() != b.length()) {
						return ((Integer) a.length()).compareTo(b.length());
					}
					
					return a.compareTo(b);
				}
			});
			int patternCount = entry.getKey();
			
			sb.append(patternCount + "\n");
			
			for (int i = 0; i < list.size(); i++) {
				if (i != 0 && i % 6 == 0) {
					sb.deleteCharAt(sb.length() - 1);
					sb.append("\n");
				}
				
				sb.append(list.get(i));
				if (i != list.size() - 1) {
					sb.append(" ");
				}
			}
			
			sb.append("\n");
			count++;
		}
		
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	static Comparator<Entry<String, Integer>> cmp = new Comparator<Map.Entry<String,Integer>>() {
		
		@Override
		public int compare(Entry<String, Integer> a, Entry<String, Integer> b) {
			return -((Integer) a.getValue()).compareTo(b.getValue());
		}
	};
	
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
