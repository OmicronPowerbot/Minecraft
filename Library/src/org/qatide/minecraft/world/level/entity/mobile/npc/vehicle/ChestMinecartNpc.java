package org.qatide.minecraft.world.level.entity.mobile.npc.vehicle;

import org.qatide.minecraft.nbt.*;
import org.qatide.minecraft.world.level.ItemCollection;

import java.util.List;

/**
 * @author Omicron
 */
public final class ChestMinecartNpc extends MinecartNpc {
	private ItemCollection items = new ItemCollection();

	public ChestMinecartNpc() {
		super(Type.CHEST);
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		items.deserialize(tree.getSubTag("Items", ListTag.class));
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new ListTag("Items", Tag.TAG_COMPOUND, items.serialize()));
		return tags;
	}
}
