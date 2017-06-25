/*
ID: conchim1
LANG: JAVA
TASK: shuttle
 */

package section_4_3;

import java.math.*;
import java.util.*;
import java.io.*;

public class shuttle {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("shuttle.in");
		int n = in.nextInt();
		computePath(n);
	}
	
	static void computePath(int n) throws Exception {
		String start = getStart(n);
		String end = getEnd(n);
		
		Queue<String> queue = new ArrayDeque<>();
		Map<String, Integer> mapMove = new HashMap<>();
		Map<String, String> mapPrev = new HashMap<>();
		
		queue.add(start);

		while (!queue.isEmpty()) {
			String curr = queue.poll();
			
			if (curr.equals(end)) {
				continue;
			}
			
			for (Next next : getNext(curr)) {
				if (!mapPrev.containsKey(next.str)) {
					queue.add(next.str);
					mapPrev.put(next.str, curr);
					mapMove.put(next.str, next.index);
				} 
				else if (next.index < mapMove.get(next.str)) {
					mapMove.put(next.str, next.index);
					mapPrev.put(next.str, curr);
				}
			}
		}
		
		printMoves(mapMove, mapPrev, end);
	}
	
	static void printMoves(Map<String, Integer> mapMove, Map<String, String> mapPrev, String end) throws Exception {
		List<Integer> list = new ArrayList<>();
		String s = end;
		
		while (mapMove.containsKey(s)) {
			list.add(mapMove.get(s));
			s = mapPrev.get(s);
		}
		
		Collections.reverse(list);
		
		PrintWriter pw = new PrintWriter("shuttle.out");
		pw.print(list.get(0));
		
		for (int i = 1; i < list.size(); i++) {
			if (i % 20 == 0) {
				pw.println();
				pw.print(list.get(i));
			} else {
				pw.print(" " + list.get(i));
			}
		}
		
		pw.println();
		pw.close();
	}
	
	static List<Next> getNext(String s) {
		List<Next> list = new ArrayList<>();
		char[] a = s.toCharArray();
		int spaceIndex = s.indexOf(" ");
		
		// move 1
		if (spaceIndex - 1 >= 0 && a[spaceIndex-1] == 'W') {
			swap(a, spaceIndex - 1, spaceIndex);
			list.add(new Next(new String(a), spaceIndex));
			swap(a, spaceIndex - 1, spaceIndex);
		}
		
		if (spaceIndex + 1 < s.length() && a[spaceIndex+1] == 'B') {
			swap(a, spaceIndex, spaceIndex + 1);
			list.add(new Next(new String(a), spaceIndex+2));
			swap(a, spaceIndex, spaceIndex + 1);
		}
		
		// move 2
		if (spaceIndex-2 >= 0 && a[spaceIndex-2] != a[spaceIndex-1] && a[spaceIndex-2] == 'W') {
			swap(a, spaceIndex-2, spaceIndex);
			list.add(new Next(new String(a), spaceIndex-1));
			swap(a, spaceIndex-2, spaceIndex);
		}
		
		if (spaceIndex+2 < s.length() && a[spaceIndex+1] != a[spaceIndex+2] && a[spaceIndex+2] == 'B') {
			swap(a, spaceIndex, spaceIndex + 2);
			list.add(new Next(new String(a), spaceIndex+3));
			swap(a, spaceIndex, spaceIndex + 2);
		}
		
		return list;
	}
	
	static void swap(char[] a, int i, int j) { 
		char temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	static String getStart(int n) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < n; i++) {
			sb.append("W");
		}
		sb.append(" ");
		
		for (int i = 0; i < n; i++) {
			sb.append("B");
		}
		
		return sb.toString();
	}
	
	static String getEnd(int n) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < n; i++) {
			sb.append("B");
		}
		sb.append(" ");
		
		for (int i = 0; i < n; i++) {
			sb.append("W");
		}
		
		return sb.toString();
	}
	
	
	static class Next {
		String str;
		int index;
		
		public Next(String str, int index) {
			this.str = str;
			this.index = index;
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
