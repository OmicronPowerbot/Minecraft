package org.qatide.minecraft.world.level.entity.mobile.npc.attackable;

import org.qatide.minecraft.nbt.ByteTag;
import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.IntTag;
import org.qatide.minecraft.nbt.Tag;

import java.util.List;

/**
 * @author Omicron
 */
public final class SheepNpc extends AttackableNpc {
	private int age;
	private int inLove;
	private boolean sheared;
	private int color;

	public SheepNpc() {
		super("Sheep", 8);
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		age = tree.getSubTag("Age", IntTag.class).getValue();
		inLove = tree.getSubTag("InLove", IntTag.class).getValue();
		sheared = tree.getSubTag("Sheared", ByteTag.class).getValue() == 1;
		color = tree.getSubTag("Color", ByteTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new IntTag("Age", age));
		tags.add(new IntTag("InLove", inLove));
		tags.add(new ByteTag("Sheared", sheared ? 1 : 0));
		tags.add(new ByteTag("Color", color));
		return tags;
	}
}
