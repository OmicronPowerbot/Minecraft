package org.qatide.minecraft.world.level.entity.tile;

import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.ShortTag;
import org.qatide.minecraft.nbt.StringTag;
import org.qatide.minecraft.nbt.Tag;

import java.util.List;

/**
 * @author Omicron
 */
public final class MobSpawnerTileEntity extends TileEntity {
	private String entityId;
	private int delay;

	public MobSpawnerTileEntity() {
		super("MobSpawner");
	}

	@Override
	public void deserialize(CompoundTag tag) {
		super.deserialize(tag);
		entityId = tag.getSubTag("EntityId", StringTag.class).getValue();
		delay = tag.getSubTag("Delay", ShortTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new StringTag("EntityId", entityId));
		tags.add(new ShortTag("Delay", delay));
		return tags;
	}
}
