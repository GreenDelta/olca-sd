package org.openlca.sd.eqn;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.openlca.sd.xmile.Xmile;

public class NonNegTest {

	private Xmile xmile;

	@Before
	public void setup() throws Exception {
		try (var stream = getClass().getResourceAsStream("non-neg-stock.xml")) {
			xmile = Xmile.readFrom(stream).orElseThrow();
		}
	}

	@Test
	public void testSimulation() {

		var sim = Simulator.of(xmile).orElseThrow();
		// Stock starts at 5, inflow=1, outflow=2, net=-1 per step
		// But stock is non-negative, so it should stop at 0
		double[][] expected = {
			{0, 1.00, 5.0},   // initial
			{1, 2.00, 4.0},   // 5 + 1 - 2 = 4
			{2, 3.00, 3.0},   // 4 + 1 - 2 = 3
			{3, 4.00, 2.0},   // 3 + 1 - 2 = 2
			{4, 5.00, 1.0},   // 2 + 1 - 2 = 1
			{5, 6.00, 0.0},   // 1 + 1 - 2 = 0
			{6, 7.00, 0.0},   // 0 + 1 - 1 = 0 (outflow limited)
			{7, 8.00, 0.0},   // 0 + 1 - 1 = 0 (outflow limited)
			{8, 9.00, 0.0},   // 0 + 1 - 1 = 0 (outflow limited)
			{9, 10.00, 0.0}   // 0 + 1 - 1 = 0 (outflow limited)
		};

		var actual = new ArrayList<double[]>();
		sim.forEach(res -> {
			if (res.isError()) {
				fail(res.error());
			} else {
				var state = res.value();
				double stockValue = state.valueOf(Id.of("Stock"))
					.orElseThrow()
					.asNum();
				actual.add(new double[]{
					state.iteration(),
					state.time(),
					stockValue});
			}
		});

		assertEquals(expected.length, actual.size());
		for (int i = 0; i < expected.length; i++) {
			double[] ai = actual.get(i);
			double[] ei = expected[i];
			assertArrayEquals(ei, ai, 0.001);
		}
	}
}
