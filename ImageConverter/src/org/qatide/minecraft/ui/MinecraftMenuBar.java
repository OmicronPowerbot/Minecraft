package org.qatide.minecraft.ui;

import org.qatide.minecraft.Minecraft;

import javax.swing.*;

/**
 * @author Omicron
 */
public final class MinecraftMenuBar extends JMenuBar {
	private final WorldMenu worldMenu;

	public MinecraftMenuBar(Minecraft minecraft) {
		this.worldMenu = new WorldMenu(minecraft);
		this.add(worldMenu);
	}
}
