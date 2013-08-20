package org.qatide.minecraft.world.level.entity.mobile.npc.vehicle;

import org.qatide.minecraft.nbt.*;

import java.util.List;

/**
 * @author Omicron
 */
public final class FurnanceMinecartNpc extends MinecartNpc {
	private double pushX;
	private double pushZ;
	private int fuel;

	public FurnanceMinecartNpc() {
		super(Type.FURNANCE);
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		pushX = tree.getSubTag("PushX", DoubleTag.class).getValue();
		pushZ = tree.getSubTag("PushZ", DoubleTag.class).getValue();
		fuel = tree.getSubTag("Fuel", ShortTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new DoubleTag("PushX", pushX));
		tags.add(new DoubleTag("PushZ", pushZ));
		tags.add(new ShortTag("Fuel", fuel));
		return tags;
	}
}
