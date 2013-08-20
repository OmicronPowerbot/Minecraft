package org.qatide.minecraft.world.region;

import org.qatide.minecraft.nbt.*;

/**
 * @author Omicron
 */
public final class TileTick implements NBTSerializable<CompoundTag, Tag[]> {
	private int blockId;
	private int ticks;
	private int x;
	private int y;
	private int z;

	public TileTick() {
	}

	@Override
	public void deserialize(CompoundTag tag) {
		blockId = tag.getSubTag("i", IntTag.class).getValue();
		ticks = tag.getSubTag("t", IntTag.class).getValue();
		x = tag.getSubTag("x", IntTag.class).getValue();
		y = tag.getSubTag("y", IntTag.class).getValue();
		z = tag.getSubTag("z", IntTag.class).getValue();
	}

	@Override
	public Tag[] serialize() {
		Tag[] tags = new Tag[5];
		tags[0] = new IntTag("i", blockId);
		tags[1] = new IntTag("t", ticks);
		tags[2] = new IntTag("x", x);
		tags[3] = new IntTag("y", y);
		tags[4] = new IntTag("z", z);
		return tags;
	}
}
