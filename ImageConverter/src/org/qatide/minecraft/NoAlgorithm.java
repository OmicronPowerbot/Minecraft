package org.qatide.minecraft;

import com.sun.istack.internal.NotNull;
import org.qatide.image.DitherAlgorithm;
import org.qatide.image.Image;
import org.qatide.image.PixelMatcher;

/**
 * @author Omicron
 */
public class NoAlgorithm implements DitherAlgorithm {
	@Override
	public Image dither(@NotNull Image image, @NotNull PixelMatcher matcher) {
		int[] imagePixels = image.getPixels();
		int width = image.getWidth();
		int height = image.getHeight();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int offset = x + (y * width);
				int pixel = imagePixels[offset];
				int match = matcher.getClosestMatch(pixel);
				imagePixels[offset] = match;
			}
		}
		return new Image(imagePixels, width, height);
	}
}
