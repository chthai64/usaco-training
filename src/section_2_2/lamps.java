/*
ID: conchim1
LANG: JAVA
TASK: lamps
 */

package section_2_2;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class lamps {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("lamps.in");
		
		int N = in.nextInt();
		int C = in.nextInt();
		
		List<Integer> lampsOn = new ArrayList<Integer>();
		int n = in.nextInt();
		while (n != -1) {
			lampsOn.add(n);
			n = in.nextInt();
		}
		
		List<Integer> lampsOff = new ArrayList<Integer>();
		n = in.nextInt();
		while (n != -1) {
			lampsOff.add(n);
			n = in.nextInt();
		}
		
		String result = solve(N, C, lampsOn, lampsOff);
		
		PrintWriter pw = new PrintWriter(new File("lamps.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static String solve(int N, int C, List<Integer> lampsOn, List<Integer> lampsOff) {
		int maxLevel = Math.min(2, C);
		Set<String> result = new HashSet<String>();
		
		boolean[] startLamps = new boolean[N];
		Arrays.fill(startLamps, true);
		
		permute(result, lampsOn, lampsOff, startLamps, 0, maxLevel);
		
		if (result.isEmpty())
			return "IMPOSSIBLE";

		List<String> list = new ArrayList<String>(result);
		Collections.sort(list);
		return getString(list);
	}
	
	static void permute(Set<String> result, List<Integer> lampsOn, List<Integer> lampsOff, 
			boolean[] lamps, int level, int maxLevel) {
		if (level == maxLevel) {
			if (isValid(lampsOn, lampsOff, lamps)) {
				result.add(getLampString(lamps));
			}
			return;
		}
		
		if (isValid(lampsOn, lampsOff, lamps)) {
			result.add(getLampString(lamps));
		}
		
		// btn one
		for (int i = 0; i < lamps.length; i++) {
			lamps[i] = !lamps[i];
		}
		permute(result, lampsOn, lampsOff, lamps, level + 1, maxLevel);
		for (int i = 0; i < lamps.length; i++) {
			lamps[i] = !lamps[i];
		}
		
		// btn two
		for (int i = 0; i < lamps.length; i++) {
			int lampNumber = i + 1;
			if (lampNumber % 2 != 0) {
				lamps[i] = !lamps[i];
			}
		}
		permute(result, lampsOn, lampsOff, lamps, level + 1, maxLevel);
		for (int i = 0; i < lamps.length; i++) {
			int lampNumber = i + 1;
			if (lampNumber % 2 != 0) {
				lamps[i] = !lamps[i];
			}
		}
		
		// btn three
		for (int i = 0; i < lamps.length; i++) {
			int lampNumber = i + 1;
			if (lampNumber % 2 == 0) {
				lamps[i] = !lamps[i];
			}
		}
		permute(result, lampsOn, lampsOff, lamps, level + 1, maxLevel);
		for (int i = 0; i < lamps.length; i++) {
			int lampNumber = i + 1;
			if (lampNumber % 2 == 0) {
				lamps[i] = !lamps[i];
			}
		}
		
		// btn four
		for (int i = 0; i < lamps.length; i++) {
			int lampNumber = i + 1;
			if (shouldChangeFour(lampNumber)) {
				lamps[i] = !lamps[i];
			}
		}
		permute(result, lampsOn, lampsOff, lamps, level + 1, maxLevel);
		for (int i = 0; i < lamps.length; i++) {
			int lampNumber = i + 1;
			if (shouldChangeFour(lampNumber)) {
				lamps[i] = !lamps[i];
			}
		}
	}
	
	static boolean shouldChangeFour(int lampNumber) {
		return ((lampNumber - 1) % 3) == 0;
	}
	
	static boolean isValid(List<Integer> lampsOn, List<Integer> lampsOff, boolean[] lamps) {
		for (int indexOn : lampsOn) {
			if (!lamps[indexOn - 1])
				return false;
		}
		
		for (int indexOff : lampsOff) {
			if (lamps[indexOff - 1])
				return false;
		}
		
		return true;
	}
	
	static String getString(List<String> result) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < result.size(); i++) {
			sb.append(result.get(i));
			if (i != result.size() - 1)
				sb.append("\n");
		}
		
		return sb.toString();
	}
	
	static String getLampString(boolean[] lamps) {
		StringBuilder sb = new StringBuilder();
		for (boolean isOn : lamps) {
			sb.append(isOn? "1" : "0");
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
