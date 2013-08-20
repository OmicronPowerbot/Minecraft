package org.qatide.minecraft.graphics;

import org.qatide.minecraft.MinecraftFormatException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Omicron
 */
public final class TexturePack {
	private final String name;
	private final String description;
	private final Terrain terrain;

	public TexturePack(String name, File file) throws IOException, MinecraftFormatException {
		this.name = name;
		ZipFile zipFile = new ZipFile(file);
		ZipEntry packEntry = zipFile.getEntry("pack.txt");
		StringBuilder builder = new StringBuilder();
		if (packEntry != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(packEntry)));
			String line = reader.readLine();
			if (line != null) {
				builder.append(line);
				line = reader.readLine();
				if (line != null) {
					builder.append('\n');
					builder.append(line);
				}
			}
		}
		description = builder.toString();
		ZipEntry terrainEntry = zipFile.getEntry("terrain.png");
		if (terrainEntry == null) {
			throw new MinecraftFormatException("Texture Pack does not contain terrain.");
		}
		terrain = Terrain.load(zipFile.getInputStream(terrainEntry));
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	@Override
	public String toString() {
		return name;
	}
}
