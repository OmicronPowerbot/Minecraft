package org.qatide.minecraft;

import org.qatide.minecraft.graphics.TerrainImage;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Omicron
 */
public final class BlockDefinitionImage {
	private Map<TerrainImage, Map<Integer, int[]>> cachedPixels = new HashMap<TerrainImage, Map<Integer, int[]>>();
	private TerrainImage[] pixels;
	private int width;
	private int height;

	public BlockDefinitionImage(TerrainImage[] pixels, int width, int height) {
		this.pixels = pixels;
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private BufferedImage bufferedImage;

	public BufferedImage createBufferedImage() {
		if (bufferedImage != null) {
			return bufferedImage;
		}
		return bufferedImage = createBufferedImage(16);
	}

	public BufferedImage createBufferedImage(int x, int y, int xMax, int yMax, int imageSize) {
		int initialX = x;
		int initialY = y;
		BufferedImage image = new BufferedImage((xMax * imageSize) - (x * imageSize), (yMax * imageSize) - (y * imageSize), BufferedImage.TYPE_INT_ARGB);
		for (; x < xMax; x++) {
			for (y = initialY; y < yMax; y++) {
				TerrainImage terrainImage = this.pixels[x + (y * this.width)];
				Map<Integer, int[]> value = cachedPixels.get(terrainImage);
				if (value == null) {
					value = new HashMap<Integer, int[]>();
					cachedPixels.put(terrainImage, value);
				}
				int[] imagePixels = value.get(imageSize);
				if (imagePixels == null) {
					imagePixels = terrainImage.getPixels(imageSize, imageSize);
					value.put(imageSize, imagePixels);
				}
				image.setRGB((x - initialX) * imageSize, (y - initialY) * imageSize, imageSize, imageSize, imagePixels, 0, imageSize);
			}
		}
		image.flush();
		return image;
	}

	private BufferedImage createBufferedImage(int imageSize) {
		BufferedImage image = new BufferedImage(width * imageSize, height * imageSize, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				TerrainImage terrainImage = this.pixels[x + (y * width)];
				Map<Integer, int[]> value = cachedPixels.get(terrainImage);
				if (value == null) {
					value = new HashMap<Integer, int[]>();
					cachedPixels.put(terrainImage, value);
				}
				int[] imagePixels = value.get(imageSize);
				if (imagePixels == null) {
					imagePixels = terrainImage.getPixels(imageSize, imageSize);
					value.put(imageSize, imagePixels);
				}
				image.setRGB(x * imageSize, y * imageSize, imageSize, imageSize, imagePixels, 0, imageSize);
			}
		}
		image.flush();
		return image;
	}
}
