package org.qatide.minecraft.world.level.entity.mobile.npc.projectile;

import org.qatide.minecraft.nbt.ByteTag;
import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.ShortTag;
import org.qatide.minecraft.nbt.Tag;
import org.qatide.minecraft.world.level.entity.mobile.npc.Npc;

import java.util.List;

/**
 * @author Omicron
 */
public abstract class ProjectileNpc extends Npc {
	private int tileX;
	private int tileY;
	private int tileZ;
	private int inTile;
	private int shake;
	private boolean inGround;

	public ProjectileNpc(String id) {
		super(id);
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		tileX = tree.getSubTag("xTile", ShortTag.class).getValue();
		tileY = tree.getSubTag("yTile", ShortTag.class).getValue();
		tileZ = tree.getSubTag("zTile", ShortTag.class).getValue();
		inTile = tree.getSubTag("inTile", ByteTag.class).getValue();
		shake = tree.getSubTag("shake", ByteTag.class).getValue();
		inGround = tree.getSubTag("inGround", ByteTag.class).getValue() == 1;
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new ShortTag("xTile", tileX));
		tags.add(new ShortTag("yTile", tileY));
		tags.add(new ShortTag("zTile", tileZ));
		tags.add(new ByteTag("inTile", inTile));
		tags.add(new ByteTag("shake", shake));
		tags.add(new ByteTag("inGround", inGround ? 1 : 0));
		return tags;
	}
}
