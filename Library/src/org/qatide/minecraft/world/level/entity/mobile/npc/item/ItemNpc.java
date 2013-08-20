package org.qatide.minecraft.world.level.entity.mobile.npc.item;

import org.qatide.minecraft.nbt.*;
import org.qatide.minecraft.world.level.entity.mobile.npc.Npc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omicron
 */
public final class ItemNpc extends Npc {
	private int health;
	private int age;
	private Item item = new Item();

	public ItemNpc() {
		super("Item");
	}

	@Override
	public void deserialize(CompoundTag tag) {
		super.deserialize(tag);
		health = tag.getSubTag("Health", ShortTag.class).getValue();
		age = tag.getSubTag("Age", ShortTag.class).getValue();
		item.deserialize(tag.getSubTag("Item", CompoundTag.class));
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new ShortTag("Health", health));
		tags.add(new ShortTag("Age", age));
		tags.add(new CompoundTag("Item", item.serialize()));
		return tags;
	}

	public static final class Item implements NBTSerializable<CompoundTag, Tag[]> {
		private int id;
		private int damage;
		private int count;

		@Override
		public void deserialize(CompoundTag tag) {
			//System.out.println(tag);
			id = tag.getSubTag("id", ShortTag.class).getValue();
			damage = tag.getSubTag("Damage", ShortTag.class).getValue();
			count = tag.getSubTag("Count", ByteTag.class).getValue();
		}

		@Override
		public Tag[] serialize() {
			List<Tag> tags = new ArrayList<Tag>(4);
			tags.add(new ShortTag("id", id));
			tags.add(new ShortTag("Damage", damage));
			tags.add(new ByteTag("Count", count));
			return tags.toArray(new Tag[tags.size()]);
		}

		public static final class Enchantment implements NBTSerializable<CompoundTag, Tag[]> {
			private int id;
			private int level;

			@Override
			public void deserialize(CompoundTag tag) {
				id = tag.getSubTag("id", ShortTag.class).getValue();
				level = tag.getSubTag("lvl", ShortTag.class).getValue();
			}

			@Override
			public Tag[] serialize() {
				List<Tag> tags = new ArrayList<Tag>(2);
				tags.add(new ShortTag("id", id));
				tags.add(new ShortTag("lvl", level));
				return tags.toArray(new Tag[tags.size()]);
			}
		}
	}
}
