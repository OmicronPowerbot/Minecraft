package org.qatide.minecraft.world.level.entity.tile;

import org.qatide.minecraft.nbt.ByteTag;
import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.Tag;

import java.util.List;

/**
 * @author Omicron
 */
public final class MusicTileEntity extends TileEntity {
	private int note;

	public MusicTileEntity() {
		super("Music");
	}

	@Override
	public void deserialize(CompoundTag tag) {
		super.deserialize(tag);
		note = tag.getSubTag("note", ByteTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new ByteTag("note", note));
		return tags;
	}
}
