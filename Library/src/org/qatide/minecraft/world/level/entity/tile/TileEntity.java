package org.qatide.minecraft.world.level.entity.tile;

import org.qatide.minecraft.MinecraftFormatException;
import org.qatide.minecraft.nbt.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omicron
 */
public abstract class TileEntity implements NBTSerializable<CompoundTag, List<Tag>> {
	public static TileEntity parse(CompoundTag tree) throws MinecraftFormatException {
		String id = tree.getSubTag("id", StringTag.class).getValue();
		if (id.equals("Cauldron")) {
			return new CauldronTileEntity();
		}
		if (id.equals("Chest")) {
			return new ChestTileEntity();
		}
		if (id.equals("EnchantTable")) {
			return new EnchantTableTileEntity();
		}
		if (id.equals("EndPortal")) {
			return new EndPortalTileEntity();
		}
		if (id.equals("Furnace")) {
			return new FurnanceTileEntity();
		}
		if (id.equals("MobSpawner")) {
			return new MobSpawnerTileEntity();
		}
		if (id.equals("Music")) {
			return new MusicTileEntity();
		}
		if (id.equals("Piston")) {
			return new PistonTileEntity();
		}
		if (id.equals("RecordPlayer")) {
			return new RecordPlayerTileEntity();
		}
		if (id.equals("Sign")) {
			return new SignTileEntity();
		}
		if (id.equals("Trap")) {
			return new TrapTileEntity();
		}
		throw new MinecraftFormatException("Unknown Tile Entity: " + id);
	}

	private String id;
	private int z, x, y;

	TileEntity(String id) {
		this.id = id;
	}

	@Override
	public void deserialize(CompoundTag tag) {
		z = tag.getSubTag("z", IntTag.class).getValue();
		x = tag.getSubTag("x", IntTag.class).getValue();
		y = tag.getSubTag("y", IntTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(new StringTag("id", id));
		tags.add(new IntTag("z", z));
		tags.add(new IntTag("x", x));
		tags.add(new IntTag("y", y));
		return tags;
	}
}
