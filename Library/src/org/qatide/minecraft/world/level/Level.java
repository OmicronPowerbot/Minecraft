package org.qatide.minecraft.world.level;

import org.qatide.minecraft.nbt.*;
import org.qatide.minecraft.world.level.entity.mobile.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Omicron
 */
public final class Level implements NBTSerializable<CompoundTag, List<Tag>> {
	public static final int VERSION_1_2_5 = 19133;

	private static final Random random = new Random();

	private int version;
	private long sizeOnDisk;
	private String levelName;
	private long randomSeed;
	private GameType gameType;
	private boolean mapFeatures;
	private boolean hardcore;
	private long time;
	private long lastPlayed;
	private int spawnX, spawnY, spawnZ;
	private boolean raining;
	private int rainTime;
	private boolean thundering;
	private int thunderTime;
	private String generatorName;
	private int generatorVersion;
	private final Player player = new Player();

	{
		randomSeed = (long) random.nextInt() << 32 | random.nextInt();
		gameType = GameType.CREATIVE;
		lastPlayed = System.currentTimeMillis();
		rainTime = random.nextInt(100000);
		thunderTime = random.nextInt(100000);
		generatorName = "default";
		generatorVersion = 1;
	}

	public int getVersion() {
		return version;
	}

	public long getSizeOnDisk() {
		return sizeOnDisk;
	}

	public String getLevelName() {
		return levelName;
	}

	public int getSpawnX() {
		return spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public int getSpawnZ() {
		return spawnZ;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public void deserialize(CompoundTag tag) {
		version = tag.getSubTag("version", IntTag.class).getValue();
		sizeOnDisk = tag.getSubTag("SizeOnDisk", LongTag.class).getValue();
		levelName = tag.getSubTag("LevelName", StringTag.class).getValue();
		randomSeed = tag.getSubTag("RandomSeed", LongTag.class).getValue();
		gameType = GameType.values()[tag.getSubTag("GameType", IntTag.class).getValue()];
		mapFeatures = tag.getSubTag("MapFeatures", ByteTag.class).getValue() == 1;
		hardcore = tag.getSubTag("hardcore", ByteTag.class).getValue() == 1;
		time = tag.getSubTag("Time", LongTag.class).getValue();
		lastPlayed = tag.getSubTag("LastPlayed", LongTag.class).getValue();
		spawnX = tag.getSubTag("SpawnX", IntTag.class).getValue();
		spawnY = tag.getSubTag("SpawnY", IntTag.class).getValue();
		spawnZ = tag.getSubTag("SpawnZ", IntTag.class).getValue();
		raining = tag.getSubTag("raining", ByteTag.class).getValue() == 1;
		rainTime = tag.getSubTag("rainTime", IntTag.class).getValue();
		thundering = tag.getSubTag("thundering", ByteTag.class).getValue() == 1;
		thunderTime = tag.getSubTag("thunderTime", IntTag.class).getValue();
		generatorName = tag.getSubTag("generatorName", StringTag.class).getValue();
		generatorVersion = tag.getSubTag("generatorVersion", IntTag.class).getValue();
		player.deserialize(tag.getSubTag("Player", CompoundTag.class));

	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(new IntTag("version", version));
		tags.add(new LongTag("SizeOnDisk", sizeOnDisk));
		tags.add(new StringTag("LevelName", levelName));
		tags.add(new LongTag("RandomSeed", randomSeed));
		tags.add(new IntTag("GameType", gameType.ordinal()));
		tags.add(new ByteTag("MapFeatures", (byte) (mapFeatures ? 1 : 0)));
		tags.add(new ByteTag("hardcore", (byte) (hardcore ? 1 : 0)));
		tags.add(new LongTag("Time", time));
		tags.add(new LongTag("LastPlayed", lastPlayed));
		tags.add(new IntTag("SpawnX", spawnX));
		tags.add(new IntTag("SpawnY", spawnY));
		tags.add(new IntTag("SpawnZ", spawnZ));
		tags.add(new ByteTag("raining", (byte) (raining ? 1 : 0)));
		tags.add(new IntTag("rainTime", rainTime));
		tags.add(new ByteTag("thundering", (byte) (thundering ? 1 : 0)));
		tags.add(new IntTag("thunderTime", thunderTime));
		tags.add(new StringTag("generatorName", generatorName));
		tags.add(new IntTag("generatorVersion", generatorVersion));
		List<Tag> playerTags = player.serialize();
		tags.add(new CompoundTag("Player", playerTags.toArray(new Tag[playerTags.size()])));
		return tags;
	}
}
