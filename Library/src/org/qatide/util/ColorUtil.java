package org.qatide.util;

/**
 * @author Omicron
 */
public final class ColorUtil {
	public static float[] getRatios(int red, int green, int blue) {
		red += 1;
		green += 1;
		blue += 1;
		if (red > green) {
			if (red > blue) {
				return new float[] { 1.0F, (float) green / red, (float) blue / red };
			} else {
				return new float[] { (float) red / blue, (float) green / blue, 1.0F };
			}
		} else {
			if (green > blue) {
				return new float[] { (float) red / green, 1.0F, (float) blue / green };
			} else {
				return new float[] { (float) red / blue, (float) green / blue, 1.0F };
			}
		}
	}
}
