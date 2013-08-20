package org.qatide.minecraft.world.level.entity.mobile.npc.attackable;

import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.IntTag;
import org.qatide.minecraft.nbt.Tag;

import java.util.List;

/**
 * @author Omicron
 */
public final class CowNpc extends AttackableNpc {
	private int age;
	private int inLove;

	public CowNpc() {
		super("Cow", 10);
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		age = tree.getSubTag("Age", IntTag.class).getValue();
		inLove = tree.getSubTag("InLove", IntTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new IntTag("Age", age));
		tags.add(new IntTag("InLove", inLove));
		return tags;
	}
}
