package org.qatide.minecraft.ui;

import org.qatide.minecraft.ImageConverter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Omicron
 */
public final class ChooseImagePanel extends JPanel implements ActionListener {
	private static final ImageFilter IMAGE_FILTER = new ImageFilter();
	private final ImageConverter applet;
	private File lastSelectedFile;

	public ChooseImagePanel(ImageConverter applet) {
		super(null);
		this.applet = applet;
		JButton chooseImageButton = new JButton("Choose Image");
		{
			chooseImageButton.setLocation(5, 5);
			chooseImageButton.setSize(chooseImageButton.getPreferredSize());
			chooseImageButton.addActionListener(this);
		}
		this.add(chooseImageButton);
		Dimension preferredSize = new Dimension();
		for (int i = 0; i < this.getComponentCount(); i++) {
			Rectangle bounds = this.getComponent(i).getBounds();
			preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
			preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
		}
		Insets insets = this.getInsets();
		preferredSize.width += insets.right;
		preferredSize.height += insets.bottom;
		this.setMinimumSize(preferredSize);
		this.setPreferredSize(preferredSize);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.addChoosableFileFilter(IMAGE_FILTER);
		chooser.setFileFilter(IMAGE_FILTER);
		if (lastSelectedFile != null) {
			chooser.setCurrentDirectory(lastSelectedFile.getParentFile());
		}
		chooser.setMultiSelectionEnabled(false);
		if (chooser.showOpenDialog(applet) == 0) {
			lastSelectedFile = chooser.getSelectedFile();
			try {
				BufferedImage image = ImageIO.read(lastSelectedFile);
				//applet.getImageViewPanel().update(image, image.getWidth(), image.getHeight());
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(applet, "Error while reading file: " + ex.getMessage(), "Error!", 0);
			}
		}
	}

	private static final class ImageFilter extends FileFilter {
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			}
			String name = f.getName();
			int index = name.lastIndexOf('.');
			if (index == -1) {
				return false;
			}
			String extension = name.substring(index + 1);
			if (extension.equals("bmp") || extension.equals("dib")) {
				return true;
			}
			if (extension.equals("gif")) {
				return true;
			}
			if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("jpe") || extension.equals("jfif")) {
				return true;
			}
			if (extension.equals("png")) {
				return true;
			}
			if (extension.equals("ico")) {
				return true;
			}
			return false;
		}

		public String getDescription() {
			return "All Picture Files";
		}
	}
}
