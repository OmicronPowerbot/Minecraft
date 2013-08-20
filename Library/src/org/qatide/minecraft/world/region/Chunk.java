package org.qatide.minecraft.world.region;

import org.qatide.minecraft.MinecraftFormatException;
import org.qatide.minecraft.nbt.*;
import org.qatide.minecraft.world.level.entity.mobile.npc.Npc;
import org.qatide.minecraft.world.level.entity.tile.TileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omicron
 */
public final class Chunk implements NBTSerializable<CompoundTag, CompoundTag> {
	private static final Npc[] EMPTY_NPCS = new Npc[0];
	private static final TileEntity[] EMPTY_TILE_ENTITIES = new TileEntity[0];
	public static final int CHUNK_SIZE = 16;

	private int x;
	private int z;
	private long lastUpdate;
	private boolean terrainPopulated;
	private int[] heightMap;
	private byte[] biomes;
	private Npc[] npcs;
	private TileEntity[] tileEntities;
	private TileTick[] tileTicks;
	private Section[] sections;
	private boolean initialized;

	public Chunk(int x, int z) {
		this.x = x;
		this.z = z;
	}

	private void init() {
		if (initialized) {
			return;
		}
		lastUpdate = System.currentTimeMillis();
		terrainPopulated = false;
		heightMap = new int[256];
		biomes = new byte[256];
		npcs = EMPTY_NPCS;
		tileEntities = EMPTY_TILE_ENTITIES;
		sections = new Section[CHUNK_SIZE];
		for (int i = 0; i < CHUNK_SIZE; i++) {
			sections[i] = new Section(i);
		}
		initialized = true;
	}

	public Block getBlock(int x, int z, int y) {
		init();
		return sections[y / CHUNK_SIZE].getBlock(MapMath.remainder(x, CHUNK_SIZE), MapMath.remainder(z, CHUNK_SIZE), MapMath.remainder(y, CHUNK_SIZE));
	}

	public boolean isEmpty() {
		if (!initialized) {
			return true;
		}
		for (Section section : sections) {
			if (!section.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void deserialize(CompoundTag tag) throws MinecraftFormatException {
		init();
		CompoundTag levelTree = tag.getSubTag("Level", CompoundTag.class);
		int x = levelTree.getSubTag("xPos", IntTag.class).getValue();
		int z = levelTree.getSubTag("zPos", IntTag.class).getValue();
		if (this.x != x || this.z != z) {
			throw new MinecraftFormatException("Chuck position does not match: " + this.x + "," + x + " " + this.z + " " + z);
		}
		lastUpdate = levelTree.getSubTag("LastUpdate", LongTag.class).getValue();
		ByteTag terrainPopulatedTag = levelTree.getSubTag("TerrainPopulated", ByteTag.class);
		terrainPopulated = terrainPopulatedTag != null && terrainPopulatedTag.getValue() == 1;
		heightMap = levelTree.getSubTag("HeightMap", IntArrayTag.class).getValues();
		biomes = levelTree.getSubTag("Biomes", ByteArrayTag.class).getValues();
		{
			Tag[] tags = levelTree.getSubTag("Entities", ListTag.class).getValues();
			int sectionCount = tags.length;
			Npc[] npcs = new Npc[sectionCount];
			for (int i = 0; i < sectionCount; i++) {
				Npc npc = npcs[i] = Npc.parse((CompoundTag) tags[i]);
				npc.deserialize((CompoundTag) tags[i]);
			}
			this.npcs = npcs;
		}
		{
			Tag[] tags = levelTree.getSubTag("TileEntities", ListTag.class).getValues();
			int sectionCount = tags.length;
			TileEntity[] tileEntities = new TileEntity[sectionCount];
			for (int i = 0; i < sectionCount; i++) {
				TileEntity entity = tileEntities[i] = TileEntity.parse((CompoundTag) tags[i]);
				entity.deserialize((CompoundTag) tags[i]);
			}
			this.tileEntities = tileEntities;
		}
		{
			ListTag list = levelTree.getSubTag("TileTicks", ListTag.class);
			if (list == null) {
				this.tileTicks = null;
			} else {
				Tag[] tags = list.getValues();
				int sectionCount = tags.length;
				TileTick[] tileTicks = new TileTick[sectionCount];
				for (int i = 0; i < sectionCount; i++) {
					TileTick tileTick = tileTicks[i] = new TileTick();
					tileTick.deserialize((CompoundTag) tags[i]);
				}
				this.tileTicks = tileTicks;
			}
		}
		{
			ListTag list = levelTree.getSubTag("Sections", ListTag.class);
			Tag[] tags = list.getValues();
			int sectionCount = tags.length;
			for (int i = 0; i < sectionCount; i++) {
				CompoundTag compoundTag = (CompoundTag) tags[i];
				int y = compoundTag.getSubTag("Y", ByteTag.class).getValue();
				Section section = sections[y] = new Section(y);
				section.deserialize(compoundTag);
			}
		}
	}

	@Override
	public CompoundTag serialize() {
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(new LongTag("LastUpdate", lastUpdate));
		tags.add(new IntTag("xPos", x));
		tags.add(new IntTag("zPos", z));
		if (terrainPopulated) {
			tags.add(new ByteTag("TerrainPopulated", 1));
		}
		tags.add(new IntArrayTag("HeightMap", heightMap));
		tags.add(new ByteArrayTag("Biomes", biomes));
		{
			List<Tag> entitiesTags = new ArrayList<Tag>(npcs.length);
			for (Npc npc : npcs) {
				List<Tag> npcTags = npc.serialize();
				entitiesTags.add(new CompoundTag(null, npcTags.toArray(new Tag[npcTags.size()])));
			}
			tags.add(new ListTag("Entities", Tag.TAG_COMPOUND, entitiesTags.toArray(new Tag[entitiesTags.size()])));
		}
		{
			List<Tag> entitiesTags = new ArrayList<Tag>(npcs.length);
			for (TileEntity tileEntity : tileEntities) {
				List<Tag> tileEntityTags = tileEntity.serialize();
				entitiesTags.add(new CompoundTag(null, tileEntityTags.toArray(new Tag[tileEntityTags.size()])));
			}
			tags.add(new ListTag("TileEntities", Tag.TAG_COMPOUND, entitiesTags.toArray(new Tag[entitiesTags.size()])));
		}
		if (tileTicks != null) {
			List<Tag> tileTickTags = new ArrayList<Tag>(tileTicks.length);
			for (TileTick tileTick : tileTicks) {
				tileTickTags.add(new CompoundTag(null, tileTick.serialize()));
			}
			tags.add(new ListTag("TileTicks", Tag.TAG_COMPOUND, tileTickTags.toArray(new Tag[tileTickTags.size()])));
		}
		{
			List<Tag> sectionTags = new ArrayList<Tag>(CHUNK_SIZE);
			for (int i = 0; i < CHUNK_SIZE; i++) {
				Section section = sections[i];
				if (section.isEmpty()) {
					continue;
				}
				sectionTags.add(new CompoundTag(null, section.serialize()));
			}
			tags.add(new ListTag("Sections", Tag.TAG_COMPOUND, sectionTags.toArray(new Tag[sectionTags.size()])));
		}
		CompoundTag levelTree = new CompoundTag("Level", tags.toArray(new Tag[tags.size()]));
		return new CompoundTag("", new Tag[] { levelTree });
	}
}