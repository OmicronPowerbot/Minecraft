package org.qatide.minecraft.world.level.entity.tile;

import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.ListTag;
import org.qatide.minecraft.nbt.Tag;
import org.qatide.minecraft.world.level.ItemCollection;

import java.util.List;

/**
 * @author Omicron
 */
public final class TrapTileEntity extends TileEntity {
	private ItemCollection items = new ItemCollection();

	public TrapTileEntity() {
		super("Trap");
	}

	@Override
	public void deserialize(CompoundTag tag) {
		super.deserialize(tag);
		items.deserialize(tag.getSubTag("Items", ListTag.class));
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new ListTag("Items", Tag.TAG_COMPOUND, items.serialize()));
		return tags;
	}
}
