package section_1_4;

import static org.junit.Assert.*;

public class Test {

	@org.junit.Test
	public void test1() {
		int A = 8;
		int B = 9;
		int C = 10;
		assertEquals("1 2 8 9 10", milk3.solve(A, B, C));
	}
	
	@org.junit.Test
	public void test2() {
		int A = 2;
		int B = 5;
		int C = 10;
		assertEquals("5 6 7 8 9 10", milk3.solve(A, B, C));
	}

}
