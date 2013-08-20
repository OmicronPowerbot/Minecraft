package org.qatide.minecraft.world;

import org.qatide.minecraft.MinecraftFormatException;
import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.Tag;
import org.qatide.minecraft.world.level.Level;
import org.qatide.minecraft.world.region.Block;
import org.qatide.minecraft.world.region.MapMath;
import org.qatide.minecraft.world.region.Region;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author Omicron
 */
public final class World {
	private final File directory;
	private final File levelFile;
	private final File regionDirectory;
	private final Level level = new Level();
	private Region[][] regions = new Region[0][0];

	public World(File directory) throws IOException, MinecraftFormatException {
		this.directory = directory;
		this.levelFile = new File(directory, "level.dat");
		if (!levelFile.exists()) {
			throw new IOException("Directory does not contain level.dat");
		}
		this.regionDirectory = new File(directory, "region");
		if (!regionDirectory.exists()) {
			throw new IOException("Region directory does not exist.");
		}
		CompoundTag nbtTree = Tag.parseNBTFile(new GZIPInputStream(new FileInputStream(levelFile)));
		level.deserialize(nbtTree.getSubTag("Data", CompoundTag.class));
	}

	public Level getLevel() {
		return level;
	}

	public Region createRegion(int x, int z) throws IOException, MinecraftFormatException {
		int regionX = x / 512;
		if (x < 0) {
			regionX -= 1;
		}
		int regionZ = z / 512;
		if (z < 0) {
			regionZ -= 1;
		}
		if (regionX < regions.length && regionZ < regions[regionX].length) {
			Region region = regions[regionX][regionZ];
			if (region != null) {
				return region;
			}
		}
		File regionFile = new File(regionDirectory, "r." + regionX + '.' + regionZ + ".mca");
		Region region = new Region(regionX, regionZ);
		if (regionFile.exists()) {
			region.deserialize(regionFile);
		} else {
			//todo
		}
		if (regionX >= regions.length) {
			regions = Arrays.copyOf(regions, regionX + 1);
		}
		if (regionZ >= regions[regionX].length) {
			regions[regionX] = Arrays.copyOf(regions[regionX], regionZ + 1);
		}
		regions[regionX][regionZ] = region;
		return region;
	}

	public Block getBlock(int x, int z, int y) throws IOException, MinecraftFormatException {
		Region region = this.createRegion(x, z);
		if (region == null) {
			return null;
		}
		return region.getBlock(MapMath.remainder(x, 512), MapMath.remainder(z, 512), y);
	}

	public String getTitle() {
		String levelName = level.getLevelName();
		String directoryName = directory.getName();
		if (!levelName.equalsIgnoreCase(directoryName)) {
			return levelName + " (" + directoryName + ')';
		}
		return levelName;
	}

	public void save() throws IOException {
		GZIPOutputStream outputStream = null;
		try {
			outputStream = new GZIPOutputStream(new FileOutputStream(new File(directory, "level.dat")));
			List<Tag> tags = level.serialize();
			Tag tag = new CompoundTag("Data", tags.toArray(new Tag[tags.size()]));
			tags = new ArrayList<Tag>();
			tags.add(tag);
			outputStream.write(Tag.createNbtFile(new CompoundTag("", tags.toArray(new Tag[tags.size()]))));
			outputStream.finish();
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		File regionDirectory = new File(directory, "region");
		for (Region[] regions : this.regions) {
			if (regions == null) {
				continue;
			}
			for (Region region : regions) {
				if (region == null) {
					continue;
				}
				region.serialize(regionDirectory);
			}
		}
	}

	@Override
	public String toString() {
		return directory.getName();
	}
}
