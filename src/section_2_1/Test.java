package section_2_1;

import static org.junit.Assert.*;

public class Test {

	@org.junit.Test
	public void test1() {
		int N = 16;
		int B = 7;
		int D = 3;
		
		String expected = "0 7 25 30 42 45 51 52 75 76\n" +
						  "82 85 97 102 120 127";
		assertEquals(expected, hamming.solve(N, B, D));
	}

}
