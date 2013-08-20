package org.qatide.minecraft.world.level.entity.mobile.npc.attackable;

import org.qatide.minecraft.nbt.ByteTag;
import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.IntTag;
import org.qatide.minecraft.nbt.Tag;

import java.util.List;

/**
 * @author Omicron
 */
public final class PigNpc extends AttackableNpc {
	private int age;
	private int inLove;
	private boolean saddle;

	public PigNpc() {
		super("Pig", 10);
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		age = tree.getSubTag("Age", IntTag.class).getValue();
		inLove = tree.getSubTag("InLove", IntTag.class).getValue();
		saddle = tree.getSubTag("Saddle", ByteTag.class).getValue() == 1;
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new IntTag("Age", age));
		tags.add(new IntTag("InLove", inLove));
		tags.add(new ByteTag("Saddle", saddle ? 1 : 0));
		return tags;
	}
}
