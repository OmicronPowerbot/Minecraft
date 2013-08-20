package org.qatide.minecraft.world.level.entity.mobile.npc.attackable;

import org.qatide.minecraft.nbt.*;

import java.util.List;

/**
 * @author Omicron
 */
public final class OzelotNpc extends AttackableNpc {
	private String owner;
	private int age;
	private int inLove;
	private boolean sitting;
	private int catType;

	public OzelotNpc() {
		super("Ozelot", 10);
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		owner = tree.getSubTag("Owner", StringTag.class).getValue();
		age = tree.getSubTag("Age", IntTag.class).getValue();
		inLove = tree.getSubTag("InLove", IntTag.class).getValue();
		sitting = tree.getSubTag("Sitting", ByteTag.class).getValue() == 1;
		catType = tree.getSubTag("CatType", IntTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new StringTag("Owner", owner));
		tags.add(new IntTag("Age", age));
		tags.add(new IntTag("InLove", inLove));
		tags.add(new ByteTag("Sitting", sitting ? 1 : 0));
		tags.add(new IntTag("CatType", catType));
		return tags;
	}
}
