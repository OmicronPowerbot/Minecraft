package org.qatide.minecraft.world.level.entity.mobile.npc.attackable;

import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.ShortTag;
import org.qatide.minecraft.nbt.Tag;

import java.util.List;

/**
 * @author Omicron
 */
public final class PigZombieNpc extends AttackableNpc {
	private int anger;

	public PigZombieNpc() {
		super("PigZombie", 0);
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		anger = tree.getSubTag("Anger", ShortTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new ShortTag("Anger", anger));
		return tags;
	}
}
