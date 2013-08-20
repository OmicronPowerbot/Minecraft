package org.qatide.minecraft.world.level.entity.mobile.npc.attackable;

import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.ShortTag;
import org.qatide.minecraft.nbt.Tag;
import org.qatide.minecraft.world.level.entity.mobile.npc.Npc;

import java.util.List;

/**
 * @author Omicron
 */
public abstract class AttackableNpc extends Npc {
	protected int health;
	private int hurtTime;
	private int attackTime;
	private int deathTime;

	public AttackableNpc(String id, int health) {
		super(id);
		this.health = health;
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		if (health == 0) {
			System.out.println(id + " " + tree.getSubTag("Health", ShortTag.class).getValue());
		}
		health = tree.getSubTag("Health", ShortTag.class).getValue();
		hurtTime = tree.getSubTag("HurtTime", ShortTag.class).getValue();
		attackTime = tree.getSubTag("AttackTime", ShortTag.class).getValue();
		deathTime = tree.getSubTag("DeathTime", ShortTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new ShortTag("Health", health));
		tags.add(new ShortTag("HurtTime", hurtTime));
		tags.add(new ShortTag("AttackTime", attackTime));
		tags.add(new ShortTag("DeathTime", deathTime));
		return tags;
	}
}
