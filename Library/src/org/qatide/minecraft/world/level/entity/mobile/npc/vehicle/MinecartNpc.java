package org.qatide.minecraft.world.level.entity.mobile.npc.vehicle;

import org.qatide.minecraft.nbt.IntTag;
import org.qatide.minecraft.nbt.Tag;
import org.qatide.minecraft.world.level.entity.mobile.npc.Npc;

import java.util.List;

/**
 * @author Omicron
 */
public abstract class MinecartNpc extends Npc {
	private final Type type;

	MinecartNpc(Type type) {
		super("Minecart");
		this.type = type;
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new IntTag("Type", type.ordinal()));
		return tags;
	}

	public static enum Type {
		EMPTY, CHEST, FURNANCE
	}
}
