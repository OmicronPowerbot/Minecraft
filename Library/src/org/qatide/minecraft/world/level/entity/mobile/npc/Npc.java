package org.qatide.minecraft.world.level.entity.mobile.npc;

import org.qatide.minecraft.MinecraftFormatException;
import org.qatide.minecraft.nbt.*;
import org.qatide.minecraft.world.level.entity.mobile.Mobile;
import org.qatide.minecraft.world.level.entity.mobile.npc.attackable.*;
import org.qatide.minecraft.world.level.entity.mobile.npc.item.ItemNpc;
import org.qatide.minecraft.world.level.entity.mobile.npc.item.PaintingNpc;
import org.qatide.minecraft.world.level.entity.mobile.npc.item.XPOrbNpc;
import org.qatide.minecraft.world.level.entity.mobile.npc.projectile.*;
import org.qatide.minecraft.world.level.entity.mobile.npc.tile.FallingSandNpc;
import org.qatide.minecraft.world.level.entity.mobile.npc.tile.PrimedTntNpc;
import org.qatide.minecraft.world.level.entity.mobile.npc.vehicle.*;

import java.util.List;

/**
 * @author Omicron
 */
public abstract class Npc extends Mobile {
	public static Npc parse(CompoundTag tag) throws MinecraftFormatException {
		String id = tag.getSubTag("id", StringTag.class).getValue();
		//Attackable
		if (id.equals("Blaze")) {
			return new BlazeNpc();
		}
		if (id.equals("CaveSpider")) {
			return new CaveSpiderNpc();
		}
		if (id.equals("Chicken")) {
			return new ChickenNpc();
		}
		if (id.equals("Cow")) {
			return new CowNpc();
		}
		if (id.equals("Creeper")) {
			return new CreeperNpc();
		}
		if (id.equals("EnderDragon")) {
			return new EnderDragonNpc();
		}
		if (id.equals("Enderman")) {
			return new EndermanNpc();
		}
		if (id.equals("Ghast")) {
			return new GhastNpc();
		}
		if (id.equals("Giant")) {
			return new GiantNpc();
		}
		if (id.equals("LavaSlime")) {
			return new LavaSlimeNpc();
		}
		if (id.equals("Mob")) {
			return new MobNpc();
		}
		if (id.equals("Monster")) {
			return new MonsterNpc();
		}
		if (id.equals("MushroomCow")) {
			return new MushroomCowNpc();
		}
		if (id.equals("Ozelot")) {
			return new OzelotNpc();
		}
		if (id.equals("Pig")) {
			return new PigNpc();
		}
		if (id.equals("PigZombie")) {
			return new PigZombieNpc();
		}
		if (id.equals("Sheep")) {
			return new SheepNpc();
		}
		if (id.equals("Silverfish")) {
			return new SilverfishNpc();
		}
		if (id.equals("Skeleton")) {
			return new SkeletonNpc();
		}
		if (id.equals("Slime")) {
			return new SlimeNpc();
		}
		if (id.equals("SnowMan")) {
			return new SnowManNpc();
		}
		if (id.equals("Spider")) {
			return new SpiderNpc();
		}
		if (id.equals("Squid")) {
			return new SquidNpc();
		}
		if (id.equals("Villager")) {
			return new VillagerNpc();
		}
		if (id.equals("Wolf")) {
			return new WolfNpc();
		}
		if (id.equals("Zombie")) {
			return new ZombieNpc();
		}
		//Projectile
		if (id.equals("Arrow")) {
			return new ArrowNpc();
		}
		if (id.equals("Egg")) {
			return new EggNpc();
		}
		if (id.equals("Fireball")) {
			return new FireballNpc();
		}
		if (id.equals("SmallFireball")) {
			return new SmallFireballNpc();
		}
		if (id.equals("Snowball")) {
			return new SnowballNpc();
		}
		if (id.equals("ThrownEnderpearl")) {
			return new ThrownEnderpearlNpc();
		}
		//Item
		if (id.equals("Item")) {
			return new ItemNpc();
		}
		if (id.equals("Painting")) {
			return new PaintingNpc();
		}
		if (id.equals("XPOrb")) {
			return new XPOrbNpc();
		}
		//Vehicle
		if (id.equals("Boat")) {
			return new BoatNpc();
		}
		if (id.equals("Minecart")) {
			switch (MinecartNpc.Type.values()[tag.getSubTag("type", IntTag.class).getValue()]) {
				case EMPTY: {
					return new EmptyMinecartNpc();
				}
				case CHEST: {
					return new ChestMinecartNpc();
				}
				case FURNANCE: {
					return new FurnanceMinecartNpc();
				}
			}
			throw new AssertionError();
		}
		//Tile
		if (id.equals("FallingSand")) {
			return new FallingSandNpc();
		}
		if (id.equals("PrimedTnt")) {
			return new PrimedTntNpc();
		}
		throw new MinecraftFormatException("Unknown Npc: " + id);
	}

	protected String id;

	public Npc(String id) {
		this.id = id;
	}

	@Override
	public void deserialize(CompoundTag tree) {
		super.deserialize(tree);
		id = tree.getSubTag("id", StringTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new StringTag("id", id));
		return tags;
	}
}
