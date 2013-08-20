package org.qatide.minecraft.world.level.entity.mobile.npc.attackable;

import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.IntTag;
import org.qatide.minecraft.nbt.Tag;

import java.util.List;

/**
 * @author Omicron
 */
public final class SlimeNpc extends AttackableNpc {
	private int size;

	public SlimeNpc() {
		super("Slime", 0);
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		size = tree.getSubTag("Size", IntTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new IntTag("Size", size));
		return tags;
	}
}
