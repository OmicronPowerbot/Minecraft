package org.qatide.minecraft;

import org.qatide.minecraft.graphics.TexturePack;
import org.qatide.minecraft.world.World;
import org.qatide.util.OperatingSystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

/**
 * @author Omicron
 */
public final class Minecraft {
	public static File getDirectory() {
		switch (OperatingSystem.getCurrentOperatingSystem()) {
			case OperatingSystem.WINDOWS: {
				return new File(System.getProperty("user.home") + "/Appdata/Roaming/.minecraft/");
			}
			case OperatingSystem.MAC: {
				return new File("~/Library/Application Support/minecraft/");
			}
			case OperatingSystem.LINUX: {
				return new File("~/.minecraft/");
			}
		}
		return null;
	}

	public static Minecraft load() throws IOException, MinecraftFormatException {
		File directory = Minecraft.getDirectory();
		World[] worlds;
		{
			List<World> worldList = new ArrayList<World>();
			File[] worldDirectories = new File(directory, "saves").listFiles();
			int worldCount = worldDirectories.length;
			for (int i= 0; i < worldCount; i++) {
				try {
					worldList.add(new World(worldDirectories[i]));
				} catch (IOException ignored) {
				} catch (MinecraftFormatException ignored) {
				}
			}
			worlds = worldList.toArray(new World[worldList.size()]);
		}
		TexturePack defaultTexturePack;
		{
			File binDirectory = new File(directory, "bin");
			if (!binDirectory.exists()) {
				throw new MinecraftFormatException("bin directory does not exist.");
			}
			File minecraftJar = new File(binDirectory, "minecraft.jar");
			if (!minecraftJar.exists()) {
				throw new MinecraftFormatException("Minecraft jar does not exist.");
			}
			defaultTexturePack = new TexturePack("Default", minecraftJar);
		}
		TexturePack[] texturePacks;
		{
			List<TexturePack> texturePacksList = new ArrayList<TexturePack>();
			File[] texturePackDirectories = new File(directory, "texturepacks").listFiles();
			int texturePackCount = texturePackDirectories.length;
			for (int i= 0; i < texturePackCount; i++) {
				try {
					File file = texturePackDirectories[i];
					texturePacksList.add(new TexturePack(file.getName(), file));
				} catch (IOException ignored) {
				} catch (MinecraftFormatException ignored) {
				}
			}
			texturePacks = texturePacksList.toArray(new TexturePack[texturePacksList.size()]);
		}
		return new Minecraft(worlds, defaultTexturePack, texturePacks);
	}

	private final World[] worlds;
	private final TexturePack defaultTexturePack;
	private final TexturePack[] texturePacks;

	public Minecraft(World[] worlds, TexturePack defaultTexturePack, TexturePack[] texturePacks) {
		this.worlds = worlds;
		this.defaultTexturePack = defaultTexturePack;
		this.texturePacks = texturePacks;
	}

	public World[] getWorlds() {
		return worlds;
	}

	public TexturePack getDefaultTexturePack() {
		return defaultTexturePack;
	}

	public TexturePack[] getTexturePacks() {
		return texturePacks;
	}
}
