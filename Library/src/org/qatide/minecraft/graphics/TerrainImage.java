package org.qatide.minecraft.graphics;

import org.qatide.util.ColorUtil;

import java.awt.image.BufferedImage;

/**
 * @author Omicron
 */
public final class TerrainImage {
	private int[] pixels;
	private int width;
	private int height;
	private final int averageColor;
	private final float averageRedRatio;
	private final float averageGreenRatio;
	private final float averageBlueRatio;
	private BufferedImage image;

	public TerrainImage(int[] pixels, int width, int height) {
		this.pixels = pixels;
		this.width = width;
		this.height = height;
		int totalRed = 0;
		int totalGreen = 0;
		int totalBlue = 0;
		int imageSize = width * height;
		int averageRed, averageGreen, averageBlue;
		{
			for (int i = 0; i < imageSize; i++) {
				int pixel = pixels[i];
				totalRed += (pixel >> 16) & 0xFF;
				totalGreen += (pixel >> 8) & 0xFF;
				totalBlue += pixel & 0xFF;
			}
			averageRed = totalRed / imageSize;
			averageGreen = totalGreen / imageSize;
			averageBlue = totalBlue / imageSize;
			this.averageColor = (255 << 24) | (averageRed << 16) | (averageGreen << 8) | averageBlue;
		}
		float[] ratios = ColorUtil.getRatios(averageRed, averageGreen, averageBlue);
		averageRedRatio = ratios[0];
		averageGreenRatio = ratios[1];
		averageBlueRatio = ratios[2];
	}

	public int[] getPixels(int width, int height) {
		int[] pixels = new int[width * height];
		float divX = (float) this.width / (float) width;
		float divY = (float) this.height / (float) height;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + (y * width)] = this.pixels[(int) ((x * divX) + ((int) (y * divY) * this.width))];
			}
		}
		return pixels;
	}

	public int getAverageColor() {
		return averageColor;
	}

	public int getAverageRed() {
		return (averageColor >> 16) & 0xFF;
	}

	public int getAverageGreen() {
		return (averageColor >> 8) & 0xFF;
	}

	public int getAverageBlue() {
		return averageColor & 0xFF;
	}

	public float getAverageRedRatio() {
		return averageRedRatio;
	}

	public float getAverageGreenRatio() {
		return averageGreenRatio;
	}

	public float getAverageBlueRatio() {
		return averageBlueRatio;
	}

	public void clone(int[] pixels, int x, int y, int step) {
		for (int yOff = 0; yOff < height; yOff++) {
			System.arraycopy(this.pixels, yOff * width, pixels, x + ((y + yOff) * step), width);
		}
	}

	public BufferedImage createBufferedImage() {
		if (image != null) {
			return image;
		}
		if (width <= 0 || height <= 0) {
			return null;
		}
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		image.setRGB(0, 0, width, height, pixels, 0, width);
		image.flush();
		return this.image = image;
	}
}
