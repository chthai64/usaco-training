package section_2_4;

import static org.junit.Assert.*;

public class Test {

	@org.junit.Test
	public void test1() {
		int N = 1;
		int D = 3;
		assertEquals("0.(3)", fracdec.solve(N, D));
	}
	
	@org.junit.Test
	public void test2() {
		int N = 22;
		int D = 5;
		assertEquals("4.4", fracdec.solve(N, D));
	}
	
	@org.junit.Test
	public void test3() {
		int N = 1;
		int D = 7;
		assertEquals("0.(142857)", fracdec.solve(N, D));
	}
	
	@org.junit.Test
	public void test4() {
		int N = 2;
		int D = 2;
		assertEquals("1.0", fracdec.solve(N, D));
	}
	
	@org.junit.Test
	public void test5() {
		int N = 3;
		int D = 8;
		assertEquals("0.375", fracdec.solve(N, D));
	}
	
	@org.junit.Test
	public void test6() {
		int N = 45;
		int D = 56;
		assertEquals("0.803(571428)", fracdec.solve(N, D));
	}

}
