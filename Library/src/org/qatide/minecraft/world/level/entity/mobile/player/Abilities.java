package org.qatide.minecraft.world.level.entity.mobile.player;

import org.qatide.minecraft.nbt.ByteTag;
import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.NBTSerializable;
import org.qatide.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omicron
 */
public final class Abilities implements NBTSerializable<CompoundTag, Tag[]> {
	private boolean flying;
	private boolean instabuild;
	private boolean mayfly;
	private boolean invulnerable;

	public Abilities() {
	}

	public Abilities(boolean flying, boolean instabuild, boolean mayfly, boolean invulnerable) {
		this.flying = flying;
		this.instabuild = instabuild;
		this.mayfly = mayfly;
		this.invulnerable = invulnerable;
	}

	@Override
	public void deserialize(CompoundTag tag) {
		flying = tag.getSubTag("flying", ByteTag.class).getValue() == 1;
		instabuild = tag.getSubTag("instabuild", ByteTag.class).getValue() == 1;
		mayfly = tag.getSubTag("mayfly", ByteTag.class).getValue() == 1;
		invulnerable = tag.getSubTag("invulnerable", ByteTag.class).getValue() == 1;
	}

	@Override
	public Tag[] serialize() {
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(new ByteTag("flying", (byte) (flying ? 1 : 0)));
		tags.add(new ByteTag("instabuild", (byte) (instabuild ? 1 : 0)));
		tags.add(new ByteTag("mayfly", (byte) (flying ? 1 : 0)));
		tags.add(new ByteTag("invulnerable", (byte) (instabuild ? 1 : 0)));
		return tags.toArray(new Tag[tags.size()]);
	}
}
