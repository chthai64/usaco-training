package section_1_3;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import section_1_3.wormhole.Point;

public class Test {

	@org.junit.Test
	public void test1() {
		List<Point> points = Arrays.asList(
				new Point(1,15),
				new Point(20,15),
				new Point(17,11),
				new Point(22,21),
				new Point(25,11),
				new Point(20,17)
		); // 6
		assertEquals(6, wormhole.solve(points));
	}

	@org.junit.Test
	public void test2() {
		List<Point> points = Arrays.asList(
				new Point(0,0),
				new Point(1,0),
				new Point(1,1),
				new Point(0,1)
		); // 2
		assertEquals(2, wormhole.solve(points));
	}
	
	@org.junit.Test
	public void test3() {
		List<Point> points = Arrays.asList(
				new Point(987878530, 332490544),
				new Point(545074228, 332490544),
				new Point(571194544, 278963943),
				new Point(32922985, 229703843),
				new Point(571194544, 851333603),
				new Point(90862786, 28227282),
				new Point(219975775, 267376202),
				new Point(219975775, 332490544),
				new Point(90862786, 62367085),
				new Point(872930617, 951881113)
		); // 210
		assertEquals(210, wormhole.solve(points));
	}
	
	@org.junit.Test
	public void test4() {
		List<Point> points = Arrays.asList(
				new Point(636437309, 704270751),
				new Point(695056713, 700147825),
				new Point(636437309, 356396548),
				new Point(921201220, 589666013),
				new Point(430411607, 671693685),
				new Point(324259336, 671693685),
				new Point(723442153, 589666013),
				new Point(528904109, 419799944),
				new Point(921201220, 356396548),
				new Point(723442153, 856537355),
				new Point(691516566, 726853849),
				new Point(941903572, 634527403)
		);
		assertEquals(2835, wormhole.solve(points));
	}
}
