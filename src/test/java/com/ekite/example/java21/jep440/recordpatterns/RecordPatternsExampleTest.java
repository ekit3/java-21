package com.ekite.example.java21.jep440.recordpatterns;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class RecordPatternsExampleTest {

	// BEFORE JAVA 21
	public String asStringValueOld(Object anyValue) {
		if (anyValue instanceof String str) {
			return str;
		} else if (anyValue instanceof BigDecimal bd) {
			return bd.toEngineeringString();
		} else if (anyValue instanceof Integer i) {
			return Integer.toString(i);
		} else if (anyValue instanceof Point p) {
			return p.x() + "/" + p.y();
		} else {
			return "n/a";
		}
	}

	public String asStringValue(Object anyValue) {
		return switch (anyValue) {
			case String str -> str;
			case BigDecimal bd -> bd.toString();
			case Integer i -> Integer.toString(i);
			case Point(int x, int y) -> x + "/" + y;
			case null, default -> "n/a";
		};
	}

	@Test
	void testRecordPattern() {
		Object obj = new WindowFrame(new Point(1, 1), new Size(100, 100));

		//The old way to get height value
		if (obj instanceof WindowFrame wf && wf.size() != null) {
			System.out.println("Height: " + wf.size().height());
		}

		//The Java 21 way using the Deconstruction
		if (obj instanceof WindowFrame(Point origin, Size(int width, int height))) {
			System.out.println("Height: " + height);
		}

		//or this way, using 'var' words might sound good on first hand because it won't break the code if the Size record attribute types change
		//BUT on the other hand having explicit type declaration is more expressive
		if (obj instanceof WindowFrame(Point origin, Size(var width, var height))) {
			System.out.println("Height: " + height);
		}


		assertThat(asStringValueOld(new Point(1, 1))).isEqualTo("1/1" );
		assertThat(asStringValue(new Point(1, 1))).isEqualTo("1/1" );

	}
}
