package org.qatide.minecraft;

import org.qatide.image.DitherAlgorithm;
import org.qatide.image.PixelComparer;
import org.qatide.image.PixelMatcher;
import org.qatide.minecraft.graphics.Terrain;
import org.qatide.minecraft.graphics.TerrainImage;
import org.qatide.minecraft.ui.ChooseImagePanel;
import org.qatide.minecraft.ui.ImageTabbedPane;
import org.qatide.minecraft.ui.MinecraftApplet;
import org.qatide.minecraft.ui.event.ImagePanelChangeListener;
import org.qatide.minecraft.world.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Omicron
 */
public final class ImageConverter extends MinecraftApplet {
	public static void main(String[] args) throws MinecraftFormatException, IOException, InterruptedException, NoSuchAlgorithmException {
		long time = System.currentTimeMillis();
		Minecraft minecraft = Minecraft.load();
		World world = minecraft.getWorlds()[0];
		world.getLevel().setTime(0);
		long time1 = System.currentTimeMillis();
		org.qatide.image.Image image = org.qatide.image.Image.load(new File("micah.jpg"));
		Terrain terrain = minecraft.getDefaultTexturePack().getTerrain();
		DitherAlgorithm algorithm = DitherAlgorithm.Implementations.FLOYD_STEINBERG;
		long algorithmStart = System.currentTimeMillis();
		PixelMatcher matcher = new PixelMatcher(PixelComparer.Implementations.RATIO, BlockDefinitionWrapper.getEnabledImageColors(terrain, true));
		org.qatide.image.Image ditheredImage = algorithm.dither(image, matcher);
		int width = ditheredImage.getWidth();
		int height = ditheredImage.getHeight();
		TerrainImage[] terrainImages = new TerrainImage[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int offset = x + (y * width);
				terrainImages[offset] = BlockDefinitionWrapper.getTerrainImage(terrain, ditheredImage.getPixel(offset), true);
			}
		}
		BlockDefinitionImage definitionImage = new BlockDefinitionImage(terrainImages, width, height);
		//AppletLayout appletLayout = new AppletLayout(definitionImage);
		//appletLayout.setVisible(true);
		System.out.println("Took " + (System.currentTimeMillis() - algorithmStart) + " ms. to complete.");
		ImageIO.write(definitionImage.createBufferedImage(), "png", new File("FLOYD_STEINBERG.png"));
		long time2 = System.currentTimeMillis();
		long time3 = System.currentTimeMillis();
		System.out.println((time1 - time) + " " + (time2 - time1) + " " + (time3 - time2));
	}

	@Override
	public void init() {
		super.init();
		this.setLayout(new BorderLayout());
		this.add(new ChooseImagePanel(this), BorderLayout.NORTH);
		JTabbedPane tabbedPane = new JTabbedPane();
		{
			tabbedPane.add("Image", new ImageTabbedPane());
			tabbedPane.addTab("Settings", new JPanel());
			tabbedPane.addChangeListener(new ImagePanelChangeListener(tabbedPane));
		}
		this.add(tabbedPane, BorderLayout.CENTER);
	}

	@Override
	public void start() {
		super.start();    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void stop() {
		super.stop();    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void destroy() {
		super.destroy();    //To change body of overridden methods use File | Settings | File Templates.
	}
}
