package org.qatide.minecraft.world.level.entity.tile;

import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.IntTag;
import org.qatide.minecraft.nbt.Tag;

import java.util.List;

/**
 * @author Omicron
 */
public final class RecordPlayerTileEntity extends TileEntity {
	private int record;

	public RecordPlayerTileEntity() {
		super("RecordPlayer");
	}

	@Override
	public void deserialize(CompoundTag tag) {
		super.deserialize(tag);
		IntTag recordTag = tag.getSubTag("Record", IntTag.class);
		record = recordTag == null ? -1 : recordTag.getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		if (record != -1) {
			tags.add(new IntTag("Record", record));
		}
		return tags;
	}
}
