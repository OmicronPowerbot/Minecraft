package org.qatide.minecraft.world.level.entity.tile;

import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.ListTag;
import org.qatide.minecraft.nbt.ShortTag;
import org.qatide.minecraft.nbt.Tag;
import org.qatide.minecraft.world.level.ItemCollection;

import java.util.List;

/**
 * @author Omicron
 */
public final class FurnanceTileEntity extends TileEntity {
	private int burnTime;
	private int cookTime;
	private ItemCollection items = new ItemCollection();

	public FurnanceTileEntity() {
		super("Furnance");
	}

	@Override
	public void deserialize(CompoundTag tag) {
		super.deserialize(tag);
		burnTime = tag.getSubTag("BurnTime", ShortTag.class).getValue();
		cookTime = tag.getSubTag("CookTime", ShortTag.class).getValue();
		items.deserialize(tag.getSubTag("Items", ListTag.class));
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new ShortTag("BurnTime", burnTime));
		tags.add(new ShortTag("CookTime", cookTime));
		tags.add(new ListTag("Items", Tag.TAG_COMPOUND, items.serialize()));
		return tags;
	}
}
