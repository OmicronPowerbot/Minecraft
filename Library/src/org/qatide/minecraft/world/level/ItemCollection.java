package org.qatide.minecraft.world.level;

import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.ListTag;
import org.qatide.minecraft.nbt.NBTSerializable;
import org.qatide.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omicron
 */
public final class ItemCollection implements NBTSerializable<ListTag, Tag[]> {
	private ContainerItem[] containerItems;

	public ItemCollection() {
	}

	public ItemCollection(ContainerItem[] containerItems) {
		this.containerItems = containerItems;
	}

	@Override
	public void deserialize(ListTag tag) {
		Tag[] tags = tag.getValues();
		int sectionCount = tags.length;
		containerItems = new ContainerItem[sectionCount];
		for (int i = 0; i < sectionCount; i++) {
			ContainerItem containerItem = containerItems[i] = new ContainerItem();
			containerItem.deserialize((CompoundTag) tags[i]);
		}
	}

	@Override
	public Tag[] serialize() {
		int itemCount = containerItems.length;
		List<Tag> tags = new ArrayList<Tag>(itemCount);
		for (int i = 0; i < itemCount; i++) {
			tags.add(containerItems[i].serialize());
		}
		return tags.toArray(new Tag[itemCount]);
	}
}
