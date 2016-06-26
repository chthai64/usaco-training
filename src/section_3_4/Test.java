package section_3_4;

import static org.junit.Assert.*;

public class Test {

	@org.junit.Test
	public void test1() {
		int n = 7;
		int m = 5;
		int p = 10;
		
		assertEquals(20, fence9.solve(n, m, p));
	}
	
	@org.junit.Test
	public void test2() {
		int n = 10000;
		int m = 100;
		int p = 10;
		
		assertEquals(441, fence9.solve(n, m, p));
	}
	
	@org.junit.Test
	public void test3() {
		int n = 0;
		int m = 200;
		int p = 20000;
		
		assertEquals(1989801, fence9.solve(n, m, p));
	}

}
