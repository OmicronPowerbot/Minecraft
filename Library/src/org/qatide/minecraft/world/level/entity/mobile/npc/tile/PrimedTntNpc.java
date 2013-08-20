package org.qatide.minecraft.world.level.entity.mobile.npc.tile;

import org.qatide.minecraft.nbt.ByteTag;
import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.Tag;
import org.qatide.minecraft.world.level.entity.mobile.npc.Npc;

import java.util.List;

/**
 * @author Omicron
 */
public final class PrimedTntNpc extends Npc {
	private int fuse;

	public PrimedTntNpc() {
		super("PrimedTnt");
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		fuse = tree.getSubTag("Fuse", ByteTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new ByteTag("Fuse", fuse));
		return tags;
	}
}