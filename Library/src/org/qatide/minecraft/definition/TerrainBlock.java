package org.qatide.minecraft.definition;

/**
 * @author Omicron
 */
public final class TerrainBlock {

	private int id;
	private int data;
	private int sideImage;
	private int topImage;

	public TerrainBlock(int id, int data, int sideImage, int topImage) {
		this.id = id;
		this.data = data;
		this.sideImage = sideImage;
		this.topImage = topImage;
	}

	public int getId() {
		return id;
	}

	public int getData() {
		return data;
	}

	public int getSideImage() {
		return sideImage;
	}

	public int getTopImage() {
		return topImage;
	}
}
