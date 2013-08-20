package org.qatide.minecraft.world.level.entity.mobile.player;

import org.qatide.minecraft.nbt.*;
import org.qatide.minecraft.world.level.Dimension;
import org.qatide.minecraft.world.level.ItemCollection;
import org.qatide.minecraft.world.level.entity.mobile.Mobile;

import java.util.List;

/**
 * @author Omicron
 */
public final class Player extends Mobile {
	private Dimension dimension;
	private int health = 20;
	private int hurtTime;
	private int deathTime;
	private int attackTime;
	private int foodLevel = 20;
	private float foodExhaustionLevel;
	private float foodSaturationLevel = 5.0F;
	private int foodTickTimer;
	private boolean sleeping;
	private int sleepTimer;
	private int score;
	private int xpLevel;
	private int xpTotal;
	private float xpP;
	private ItemCollection inventory = new ItemCollection();
	private Abilities abilities = new Abilities();

	public Player() {
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		dimension = Dimension.getDimension(tree.getSubTag("Dimension", IntTag.class).getValue());
		health = tree.getSubTag("Health", ShortTag.class).getValue();
		hurtTime = tree.getSubTag("HurtTime", ShortTag.class).getValue();
		deathTime = tree.getSubTag("DeathTime", ShortTag.class).getValue();
		attackTime = tree.getSubTag("AttackTime", ShortTag.class).getValue();
		foodLevel = tree.getSubTag("foodLevel", IntTag.class).getValue();
		foodExhaustionLevel = tree.getSubTag("foodExhaustionLevel", FloatTag.class).getValue();
		foodSaturationLevel = tree.getSubTag("foodSaturationLevel", FloatTag.class).getValue();
		foodTickTimer = tree.getSubTag("foodTickTimer", IntTag.class).getValue();
		sleeping = tree.getSubTag("Sleeping", ByteTag.class).getValue() == 1;
		sleepTimer = tree.getSubTag("SleepTimer", ShortTag.class).getValue();
		score = tree.getSubTag("Score", IntTag.class).getValue();
		xpLevel = tree.getSubTag("XpLevel", IntTag.class).getValue();
		xpTotal = tree.getSubTag("XpTotal", IntTag.class).getValue();
		xpP = tree.getSubTag("XpP", FloatTag.class).getValue();
		inventory.deserialize(tree.getSubTag("Inventory", ListTag.class));
		abilities.deserialize(tree.getSubTag("abilities", CompoundTag.class));
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new IntTag("Dimension", dimension.getId()));
		tags.add(new ShortTag("Health", health));
		tags.add(new ShortTag("HurtTime", hurtTime));
		tags.add(new ShortTag("DeathTime", deathTime));
		tags.add(new ShortTag("AttackTime", attackTime));
		tags.add(new IntTag("foodLevel", foodLevel));
		tags.add(new FloatTag("foodExhaustionLevel", foodExhaustionLevel));
		tags.add(new FloatTag("foodSaturationLevel", foodSaturationLevel));
		tags.add(new IntTag("foodTickTimer", foodTickTimer));
		tags.add(new ByteTag("Sleeping", (byte) (sleeping ? 1 : 0)));
		tags.add(new ShortTag("SleepTimer", sleepTimer));
		tags.add(new IntTag("Score", score));
		tags.add(new IntTag("XpLevel", xpLevel));
		tags.add(new IntTag("XpTotal", xpTotal));
		tags.add(new FloatTag("XpP", xpP));
		tags.add(new ListTag("Inventory", Tag.TAG_COMPOUND, inventory.serialize()));
		tags.add(new CompoundTag("abilities", abilities.serialize()));
		return tags;
	}
}
