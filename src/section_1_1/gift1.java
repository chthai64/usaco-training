/*
ID: conchim1
LANG: JAVA
TASK: gift1
 */

package section_1_1;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class gift1 {

	public static void main(String[] args) throws Exception {
		Input in = new Input(new FileInputStream(new File("gift1.in")));
		
		int NP = in.nextInt();
		Map<String, Giver> giverMap = new LinkedHashMap<String, Giver>();
		List<String> names = new ArrayList<String>();
		
		for (int i = 0; i < NP; i++) {
			names.add(in.nextString()); 
		}
		
		for (int i = 0; i < NP; i++) {
			String name = in.nextString();
			int money = in.nextInt();
			int count = in.nextInt();
			
			Giver giver = new Giver(name, money, count);
			for (int j = 0; j < count; j++) {
				giver.addReceiver(in.nextString());
			}
			
			giverMap.put(name, giver);
		}
		
		PrintWriter pw = new PrintWriter(new File("gift1.out"));
		pw.println(solve(names, giverMap));
		pw.close();
		in.close();
	}
	
	static String solve(List<String> names, Map<String, Giver> giverMap) {
		Map<String, Integer> diffMap = new HashMap<String, Integer>();
		
		for (Giver giver : giverMap.values()) {
			if (giver.count > 0) {
				int moneyToGive = giver.money / giver.count;
				
				int diff = (!diffMap.containsKey(giver.name))? 0 : diffMap.get(giver.name);
				diff -= moneyToGive * giver.count;
				diffMap.put(giver.name, diff);
				
				for (String receiver : giver.receivers) {
					int receiverDiff = (!diffMap.containsKey(receiver))? 0 : diffMap.get(receiver);
					receiverDiff += moneyToGive;
					diffMap.put(receiver, receiverDiff);
				}
			}
		}
		
		String result = "";
		int i = 0;
		for (String name : names) {
			result += name + " " + (diffMap.containsKey(name)? diffMap.get(name) : 0);
			if (i != names.size() - 1) {
				result += '\n';
			}
			i++;
		}
		
		return result;
	}
	
	static class Giver {
		String name;
		int money;
		int count;
		List<String> receivers = new ArrayList<String>();
		
		public Giver(String name, int money, int count) {
			this.name = name;
			this.money = money;
			this.count = count;
		}
		
		public void addReceiver(String receiver) {
			receivers.add(receiver);
		}
		
		@Override
		public int hashCode() {
			return name.hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Giver) {
				Giver giver = (Giver) obj;
				return name.equals(giver.name);
			}
			
			return false;
		}

		@Override
		public String toString() {
			return "Giver [name=" + name + ", money=" + money + ", count="
					+ count + ", receivers=" + receivers + "]";
		}
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
