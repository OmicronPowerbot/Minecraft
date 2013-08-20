package org.qatide.minecraft.world.level.entity.tile;

import org.qatide.minecraft.nbt.*;

import java.util.List;

/**
 * @author Omicron
 */
public final class PistonTileEntity extends TileEntity {
	private int blockId;
	private int blockData;
	private int facing;
	private float progress;
	private int extending;

	public PistonTileEntity() {
		super("Piston");
	}

	@Override
	public void deserialize(CompoundTag tag) {
		super.deserialize(tag);
		blockId = tag.getSubTag("blockId", IntTag.class).getValue();
		blockData = tag.getSubTag("blockData", IntTag.class).getValue();
		facing = tag.getSubTag("facing", IntTag.class).getValue();
		progress = tag.getSubTag("progress", FloatTag.class).getValue();
		extending = tag.getSubTag("extending", ByteTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new IntTag("blockId", blockId));
		tags.add(new IntTag("blockData", blockData));
		tags.add(new IntTag("facing", facing));
		tags.add(new FloatTag("progress", progress));
		tags.add(new ByteTag("extending", extending));
		return tags;
	}
}
