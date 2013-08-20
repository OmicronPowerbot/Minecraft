package org.qatide.minecraft.ui;

import org.qatide.minecraft.Minecraft;
import org.qatide.minecraft.world.World;

import javax.swing.*;
import java.awt.*;

/**
 * @author Omicron
 */
public final class WorldMenu extends JMenu {
	public WorldMenu(Minecraft minecraft) {
		super("Worlds");
		for (World world : minecraft.getWorlds()) {
			this.add(new WorldMenuItem(world));
		}
	}

	private final class WorldMenuItem extends JMenuItem {
		private final World world;

		private WorldMenuItem(World world) throws HeadlessException {
			super(world.getTitle());
			this.world = world;
		}
	}
}
