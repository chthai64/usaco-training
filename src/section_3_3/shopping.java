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
	public static void main(String[] args) throws Exception {
		Input in = fromFile("shopping.in");
	
		offers = new Offer[in.nextInt()];
		for (int i = 0; i < offers.length; i++) {
			int n = in.nextInt();
			Map<Integer, Integer> productCount = new HashMap<Integer, Integer>();
			
			for (int j = 0; j < n; j++) {
				int productCode = in.nextInt();
				int count = in.nextInt();
				productCount.put(productCode, count);
			}
			
			offers[i] = new Offer(productCount, in.nextInt());
		}
		
		int totalProducts = in.nextInt();
		products = new Product[totalProducts];
		purchaseCount = new int[5];
		
		for (int i = 0; i < totalProducts; i++) {
			int code = in.nextInt();
			int count = in.nextInt();
			int price = in.nextInt();
		
			mapCodeToIndex.put(code, i);
			products[i] = new Product(code, price);
			purchaseCount[i] = count;
		}
		
		int result = solve();
		
		PrintWriter pw = new PrintWriter(new File("shopping.out"));
		pw.println(result);
		pw.close();
		in.close();
	}
	
	static Offer[] offers;
	static Product[] products;
	static int[] purchaseCount;
	static Map<Integer, Integer> mapCodeToIndex = new HashMap<Integer, Integer>();
	
	static int solve() {
		int[][][][][] DP = new int[6][6][6][6][6];
		initDP(DP);
		
		for (int n0 = 0; n0 <= purchaseCount[0]; n0++) {
			for (int n1 = 0; n1 <= purchaseCount[1]; n1++) {
				for (int n2 = 0; n2 <= purchaseCount[2]; n2++) {
					for (int n3 = 0; n3 <= purchaseCount[3]; n3++) {
						for (int n4 = 0; n4 <= purchaseCount[4]; n4++) {
							
							for (Offer offer : offers) {
								int[] state = new int[] {n0, n1, n2, n3, n4};
								
								if (canUseOffer(DP, state, offer)) {
									int payed = DP[n0][n1][n2][n3][n4];
									
									for (Map.Entry<Integer, Integer> entry : offer.productCount.entrySet()) {
										int offerProdIndex = getIndex(entry.getKey());
										int offerProdCount = entry.getValue();
										
										state[offerProdIndex] += offerProdCount;
									}
									payed += offer.price;
									
									if (payed < 0) {
										System.out.println("wrong");
									}
									
									if (payed < DP[state[0]][state[1]][state[2]][state[3]][state[4]]) {
										DP[state[0]][state[1]][state[2]][state[3]][state[4]] = payed;
									}
								}
							}
						}
					}
				}
			}
		}
		
		return DP[purchaseCount[0]]
				 [purchaseCount[1]]
				 [purchaseCount[2]]
			     [purchaseCount[3]]
			     [purchaseCount[4]];
	}
	
	static void initDP(int[][][][][] DP) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				for (int m = 0; m < 6; m++) {
					for (int n = 0; n < 6; n++) {
						for (int k = 0; k < 6; k++) {
							int[] state = {i,j,m,n,k};
							int pay = 0;
							
							for (int product = 0; product < products.length; product++) {
								pay += state[product] * products[product].price;
							}
							
							DP[i][j][m][n][k] = pay;
						}
					}
				}
			}
		}
		
		DP[0][0][0][0][0] = 0;
	}
	
	static boolean canUseOffer(int DP[][][][][], int[] state, Offer offer) {
		for (Map.Entry<Integer, Integer> entry : offer.productCount.entrySet()) {
			int offerProdIndex = getIndex(entry.getKey());
			int offerProdCount = entry.getValue();
			
			int prodLeft = purchaseCount[offerProdIndex] - state[offerProdIndex];
			
			if (prodLeft < offerProdCount) {
				return false;
			}
		}
		
		return true;
	}
	
	static int getIndex(int code) {
		if (!mapCodeToIndex.containsKey(code))
			return -1;
		return mapCodeToIndex.get(code);
	}
	
	static class Offer {
		Map<Integer, Integer> productCount;
		int price;
	
		public Offer(Map<Integer, Integer> productCount, int price) {
			this.price = price;
			this.productCount = productCount;
		}

		@Override
		public String toString() {
			return "Offer [productCount=" + productCount + ", price=" + price + "]";
		}
		
		
	}
	
	static class Product {
		int code;
		int price;
		
		public Product(int code, int price) {
			this.code = code;
			this.price = price;
		}

		@Override
		public String toString() {
			return "Product [code=" + code + ", price=" + price + "]";
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
