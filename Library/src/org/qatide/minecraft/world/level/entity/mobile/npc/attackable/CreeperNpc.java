package org.qatide.minecraft.world.level.entity.mobile.npc.attackable;

import org.qatide.minecraft.nbt.ByteTag;
import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.Tag;

import java.util.List;

/**
 * @author Omicron
 */
public final class CreeperNpc extends AttackableNpc {
	private boolean powered;

	public CreeperNpc() {
		super("Creeper", 20);
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		ByteTag poweredTag = tree.getSubTag("powered", ByteTag.class);
		powered = poweredTag != null && poweredTag.getValue() == 1;
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		if (powered) {
			tags.add(new ByteTag("powered", 1));
		}
		return tags;
	}
}
