package org.qatide.minecraft.world.level.entity.mobile.npc.attackable;

import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.ShortTag;
import org.qatide.minecraft.nbt.Tag;

import java.util.List;

/**
 * @author Omicron
 */
public final class EndermanNpc extends AttackableNpc {
	private int carried;
	private int carriedData;

	public EndermanNpc() {
		super("EnderDragon", 40);
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		carried = tree.getSubTag("carried", ShortTag.class).getValue();
		carriedData = tree.getSubTag("carriedData", ShortTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new ShortTag("carried", carried));
		tags.add(new ShortTag("carriedData", carriedData));
		return tags;
	}
}