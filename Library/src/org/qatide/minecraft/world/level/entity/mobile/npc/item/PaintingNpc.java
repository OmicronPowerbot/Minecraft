package org.qatide.minecraft.world.level.entity.mobile.npc.item;

import org.qatide.minecraft.nbt.*;
import org.qatide.minecraft.world.level.entity.mobile.npc.Npc;

import java.util.List;

/**
 * @author Omicron
 */
public final class PaintingNpc extends Npc {
	private Direction direction;
	private String motive;
	private int tileX;
	private int tileY;
	private int tileZ;

	public PaintingNpc() {
		super("Painting");
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		direction = Direction.values()[tree.getSubTag("Direction", ByteTag.class).getValue()];
		motive = tree.getSubTag("Motive", StringTag.class).getValue();
		tileX = tree.getSubTag("TileX", IntTag.class).getValue();
		tileY = tree.getSubTag("TileY", IntTag.class).getValue();
		tileZ = tree.getSubTag("TileZ", IntTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new ByteTag("Direction", direction.ordinal()));
		tags.add(new StringTag("Motive", motive));
		tags.add(new IntTag("TileX", tileX));
		tags.add(new IntTag("TileY", tileY));
		tags.add(new IntTag("TileZ", tileZ));
		return tags;
	}

	public static enum Direction {
		EAST, NORTH, WEST, SOUTH
	}
}
