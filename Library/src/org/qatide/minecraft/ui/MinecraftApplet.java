package org.qatide.minecraft.ui;

import org.qatide.minecraft.Minecraft;
import org.qatide.minecraft.MinecraftFormatException;

import java.applet.Applet;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Omicron
 */
public abstract class MinecraftApplet extends Applet {

	protected Minecraft minecraft;

	public final void launchUrl(String url) {
		try {
			super.getAppletContext().showDocument(new URL(url));
		} catch (MalformedURLException ignored) {
		}
	}

	@Override
	public void init() {
		try {
			minecraft = Minecraft.load();
		} catch (IOException e) {
			this.launchUrl("http://qatide.com/minecraft/load_error.html");
			return;
		} catch (MinecraftFormatException e) {
			this.launchUrl("http://qatide.com/minecraft/load_error.html");
			return;
		}
	}
}
