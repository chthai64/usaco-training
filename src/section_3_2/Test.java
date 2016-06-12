package section_3_2;

import static org.junit.Assert.*;

import section_3_2.ratios.Mixture;

public class Test {

	@org.junit.Test
	public void test1() {
		Mixture golden = new Mixture(3,4,5);
		Mixture[] mixtures = {
			new Mixture(1,2,3),
			new Mixture(3,7,1),
			new Mixture(2,1,2)
		};
		assertEquals("8 1 5 7", ratios.solve(mixtures, golden));
	}
	
	@org.junit.Test
	public void test2() {
		Mixture golden = new Mixture(5,8,0);
		Mixture[] mixtures = {
			new Mixture(3,5,4),
			new Mixture(1,3,0),
			new Mixture(6,2,0)
		};
		assertEquals("0 38 7 16", ratios.solve(mixtures, golden));
	}
}
