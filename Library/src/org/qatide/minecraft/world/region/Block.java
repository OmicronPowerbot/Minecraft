package org.qatide.minecraft.world.region;

/**
 * @author Omicron
 */
public final class Block {
	private final Section section;
	private final int index;

	public Block(Section section, int index) {
		this.section = section;
		this.index = index;
	}

	public int getBlock() {
		return section.getBlock(index);
	}

	public int getData() {
		return section.getData(index);
	}

	public int getSkyLight() {
		return section.getSkyLight(index);
	}

	public int getBlockLight() {
		return section.getBlockLight(index);
	}

	public void setBlock(int block) {
		section.setBlock(block, index);
	}

	public void setData(int data) {
		section.setData(data, index);
	}

	public void setSkyLight(int skyLight) {
		section.setSkyLight(skyLight, index);
	}

	public void setBlockLight(int blockLight) {
		section.setBlockLight(blockLight, index);
	}
}
