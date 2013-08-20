package org.qatide.minecraft.world.level;

import org.qatide.minecraft.nbt.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omicron
 */
public final class ContainerItem implements NBTSerializable<CompoundTag, CompoundTag> {
	private int id;
	private int damage;
	private int count;
	private int slot;

	public ContainerItem() {
	}

	@Override
	public void deserialize(CompoundTag tag) {
		id = tag.getSubTag("id", ShortTag.class).getValue();
		damage = tag.getSubTag("Damage", ShortTag.class).getValue();
		count = tag.getSubTag("Count", ByteTag.class).getValue();
		slot = tag.getSubTag("Slot", ByteTag.class).getValue();
	}

	@Override
	public CompoundTag serialize() {
		List<Tag> tags = new ArrayList<Tag>(4);
		tags.add(new ShortTag("id", id));
		tags.add(new ShortTag("Damage", damage));
		tags.add(new ByteTag("Count", count));
		tags.add(new ByteTag("Slot", slot));
		return new CompoundTag(null, tags.toArray(new Tag[tags.size()]));
	}
}
