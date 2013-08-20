package org.qatide.minecraft.world.level.entity.tile;

import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.IntTag;
import org.qatide.minecraft.nbt.ListTag;
import org.qatide.minecraft.nbt.Tag;
import org.qatide.minecraft.world.level.ItemCollection;

import java.util.List;

/**
 * @author Omicron
 */
public final class CauldronTileEntity extends TileEntity {
	private ItemCollection items = new ItemCollection();
	private int brewTime;

	public CauldronTileEntity() {
		super("Cauldron");
	}

	@Override
	public void deserialize(CompoundTag tag) {
		super.deserialize(tag);
		items.deserialize(tag.getSubTag("Items", ListTag.class));
		brewTime = tag.getSubTag("BrewTime", IntTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new ListTag("Items", Tag.TAG_COMPOUND, items.serialize()));
		tags.add(new IntTag("BrewTime", brewTime));
		return tags;
	}
}
