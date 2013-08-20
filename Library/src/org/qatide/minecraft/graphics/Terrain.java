package org.qatide.minecraft.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Omicron
 */
public final class Terrain {
	private static final int BLOCK_SIZE = 16;

	static Terrain load(InputStream inputStream) throws IOException {
		BufferedImage controlImage = ImageIO.read(inputStream);
		int xEntryCount = controlImage.getWidth() / BLOCK_SIZE;
		int yEntryCount = controlImage.getHeight() / BLOCK_SIZE;
		TerrainImage[] images = new TerrainImage[xEntryCount * yEntryCount];
		for (int y = 0; y < yEntryCount; y++) {
			for (int x = 0; x < xEntryCount; x++) {
				int[] pixels = new int[BLOCK_SIZE * BLOCK_SIZE];
				controlImage.getRGB(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, pixels, 0, BLOCK_SIZE);
				images[x + (y * yEntryCount)] = new TerrainImage(pixels, BLOCK_SIZE, BLOCK_SIZE);
				//ImageIO.write(images[x + (y * yEntryCount)].createBufferedImage(), "png", new File(x + (y * yEntryCount) + ".png"));
			}
		}
		return new Terrain(images);
	}

	private final TerrainImage[] images;

	public Terrain(TerrainImage[] images) {
		this.images = images;
	}

	public TerrainImage getImage(int index) {
		return images[index];
	}
}
