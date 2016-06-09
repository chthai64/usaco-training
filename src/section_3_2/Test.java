package section_3_2;

import static org.junit.Assert.*;

public class Test {

	@org.junit.Test
	public void test1() {
		int N = 5;
		int L = 3;
		int I = 19;
		assertEquals("10011", kimbits.solve(N, L, I));
	}

	@org.junit.Test
	public void test2() {
		int N = 4;
		int L = 2;
		int I = 1;
		assertEquals("0000", kimbits.solve(N, L, I));
	}
}
