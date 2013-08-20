package org.qatide.minecraft.world.region;

/**
 * @author Omicron
 */
public final class MapMath {

	public static int remainder(int num, int rem) {
		num = num % rem;
		if (num < 0) {
			return num + rem;
		}
		return num;
	}
}
