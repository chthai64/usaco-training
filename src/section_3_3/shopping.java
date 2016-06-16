/*
ID: conchim1
LANG: JAVA
TASK: shopping
 */

package section_3_3;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class shopping {
	static Offer[] offers;
	static Product[] products;
	static int[] purchaseCount;
	
	public static void main(String[] args) throws Exception {
		Input in = fromFile("shopping.in");
	
		offers = new Offer[in.nextInt()];
		for (int i = 0; i < offers.length; i++) {
			Pair[] productOffered = new Pair[in.nextInt()];
			
			for (int j = 0; i < productOffered.length; j++) {
				productOffered[i] = new Pair(in.nextInt(), in.nextInt());
			}
			
			offers[i] = new Offer(productOffered, in.nextInt());
		}
		
		products = new Product[in.nextInt()];
		purchaseCount = new int[1000];
		
		for (int i = 0; i < products.length; i++) {
			int code = in.nextInt();
			int count = in.nextInt();
			int price = in.nextInt();
			
			products[i] = new Product(code, price);
			purchaseCount[code] = count;
		}
		
		int result = solve();
		
//		PrintWriter pw = new PrintWriter(new File("shopping.out"));
//		pw.println(result);
//		pw.close();
//		in.close();
		
		System.out.println(result);
	}
	
	static int solve() {
		DPItem[][] DP = new DPItem[offers.length + 1][offers.length + 1];
		
		for (int i = 0; i < DP.length; i++) {
			DP[0][i] = getDefault();
			DP[i][0] = getDefault();
		}
		
		for (int n = 1; n <= offers.length; n++) {
			for (int offer = 1; offer <= offers.length; offer++) {
				DP[n][offer] = DP[n][offer - 1].clone();
				
				// check if using offer will produce better result
				processDP(DP, n, offer);
			}
		}
		
		return DP[DP.length - 1][DP.length - 1].totalCost;
	}
	
	static void processDP(DPItem[][] DP, int n, int offer) {
		for (int prevN = n - 1; prevN >= 0; prevN--) {
			for (int prevOffer = offer - 1; prevOffer >= 0; prevOffer--) {
				if (canUseOffer(DP, prevN, prevOffer, offer)) {
					
				}
			}
		}
	}
	
	static boolean canUseOffer(DPItem[][] DP, int prevN, int prevOffer, int offerIndex) {
		DPItem prevItem = DP[prevN][prevOffer];
		Offer offer = offers[offerIndex - 1];
		
		// should use map instead of pair
		for (Pair pair : offer.productOffered) {
		}
		
		return true;
	}
	
	static DPItem getDefault() {
		int totalCost = 0;
		Pair[] pairs = new Pair[products.length];
		
		for (int i = 0; i < products.length; i++) {
			pairs[i] = new Pair(products[i].code, purchaseCount[products[i].code]);
			totalCost += products[i].price * purchaseCount[products[i].code];
		}
		
		return new DPItem(pairs, totalCost, totalCost);
	}
	
	static class DPItem {
		Pair[] pairs;  // pair.count is # of count that haven't used in offer.
		int totalCost; 
		int costNoOffer; // cost of buying without using any offer.
		
		public DPItem(Pair[] pairs, int totalCost, int costNoOffer) {
			this.pairs = pairs;
			this.totalCost = totalCost;
			this.costNoOffer = costNoOffer;
		}
		
		public DPItem clone() {
			Pair[] clonePairs = new Pair[pairs.length];
			for (int i = 0; i < pairs.length; i++) {
				clonePairs[i] = new Pair(pairs[i].code, pairs[i].count);
			}
			return new DPItem(clonePairs, totalCost, costNoOffer);
		}
	}
	
	static class Offer {
		Pair[] productOffered;
		int price;
	
		public Offer(Pair[] productOffered, int price) {
			this.productOffered = productOffered;
			this.price = price;
		}
	}
	
	static class Product {
		int code;
		int price;
		
		public Product(int code, int price) {
			this.code = code;
			this.price = price;
		}
	}

	static class Pair {
		int code;
		int count;
		
		public Pair(int code, int count) {
			this.code = code;
			this.count = count;
		}
	}
	
	// Helper methods, classes
	static List<String> splitString(String text, int splitAfter) {
		List<String> strings = new ArrayList<String>();
		int index = 0;

		while (index < text.length()) {
			strings.add(text.substring(index, Math.min(index + splitAfter, text.length())));
			index += splitAfter;
		}

		return strings;
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
