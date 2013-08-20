package org.qatide.minecraft.ui;

import javax.swing.*;

/**
 * @author Omicron
 */
public class ImageTabbedPane extends JTabbedPane {

	private ImageViewPanel imageViewPanel;
	private ImageViewPanel previewViewPanel;

	public ImageViewPanel getImageViewPanel() {
		return imageViewPanel;
	}

	public ImageViewPanel getPreviewViewPanel() {
		return previewViewPanel;
	}
}
