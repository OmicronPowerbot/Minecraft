package org.qatide.minecraft.world.level;

/**
 * @author Omicron
 */
public enum Dimension {
	OVERWORLD(0),
	NETHER(-1),
	END(1);

	public static Dimension getDimension(int id) {
		switch (id) {
			case 0: {
				return OVERWORLD;
			}
			case -1: {
				return NETHER;
			}
			case 1: {
				return END;
			}
		}
		throw new IllegalArgumentException();
	}

	private final int id;

	private Dimension(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
