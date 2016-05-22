/*
ID: conchim1
LANG: JAVA
TASK: milk
 */

package section_1_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class milk {

	public static void main(String[] args) throws Exception {
		Input in = fromFile("milk.in");
		
		String[] split = in.nextLine().split(" ");
		int N = Integer.parseInt(split[0]);
		int M = Integer.parseInt(split[1]);
		
		List<Farmer> farmers = new ArrayList<Farmer>();
		for (int i = 0; i < M; i++) {
			farmers.add(new Farmer(in.nextLine()));
		}
		
		long result = getMinMoney(N, farmers);
		
		PrintWriter pw = new PrintWriter(new File("milk.out"));
		pw.println(result);
		pw.close();
		in.close();
	}

	public static int getMinMoney(int N, List<Farmer> farmers) {
		int[] amountForMoney = new int[1001];
		for (Farmer farmer : farmers) {
			amountForMoney[farmer.price] += farmer.limit;
		}
		
		int money = 0;
		int milkBought = 0;
		int price = 0;
		
		while (milkBought < N) {
			int milkNeeded = N - milkBought;
			int milkToBuy = Math.min(milkNeeded, amountForMoney[price]);
			
			milkBought += milkToBuy;
			money += milkToBuy * price;
			price++;
		}
		
		return money;
	}
	
	static class Farmer {
		int price;
		int limit;
		
		public Farmer(String s) {
			String[] a = s.split(" ");
			price = Integer.parseInt(a[0]);
			limit = Integer.parseInt(a[1]);
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
