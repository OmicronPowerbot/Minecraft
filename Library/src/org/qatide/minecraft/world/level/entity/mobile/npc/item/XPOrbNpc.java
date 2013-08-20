package org.qatide.minecraft.world.level.entity.mobile.npc.item;

import org.qatide.minecraft.nbt.*;
import org.qatide.minecraft.world.level.entity.mobile.npc.Npc;

import java.util.List;

/**
 * @author Omicron
 */
public final class XPOrbNpc extends Npc {
	private int health;
	private int age;
	private int value;

	public XPOrbNpc() {
		super("XPOrb");
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		health = tree.getSubTag("Health", ShortTag.class).getValue();
		age = tree.getSubTag("Age", ShortTag.class).getValue();
		value = tree.getSubTag("Value", ShortTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new ShortTag("Health", health));
		tags.add(new ShortTag("Age", age));
		tags.add(new ShortTag("Value", value));
		return tags;
	}
}
