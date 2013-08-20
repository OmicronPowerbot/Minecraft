package org.qatide.minecraft.world.level.entity.mobile.npc.attackable;

import org.qatide.minecraft.nbt.ByteTag;
import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.StringTag;
import org.qatide.minecraft.nbt.Tag;

import java.util.List;

/**
 * @author Omicron
 */
public final class WolfNpc extends AttackableNpc {
	private String owner;
	private boolean sitting;
	private boolean angry;

	public WolfNpc() {
		super("Wolf", 8);
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		owner = tree.getSubTag("Owner", StringTag.class).getValue();
		sitting = tree.getSubTag("Sitting", ByteTag.class).getValue() == 1;
		angry = tree.getSubTag("Angry", ByteTag.class).getValue() == 1;
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new StringTag("Owner", owner));
		tags.add(new ByteTag("Sitting", sitting ? 1 : 0));
		tags.add(new ByteTag("Angry", angry ? 1 : 0));
		return tags;
	}
}
