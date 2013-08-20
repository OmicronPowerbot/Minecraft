package org.qatide.minecraft.ui.event;

import org.qatide.minecraft.ui.ImageViewPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * @author Omicron
 */
public final class ImagePanelChangeListener implements ChangeListener {
	private final JTabbedPane tabbedPane;

	public ImagePanelChangeListener(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Component component = tabbedPane.getSelectedComponent();
		if (component.getClass() == ImageViewPanel.class) {
			((ImageViewPanel) component).setState(ImageViewPanel.STATE_ENTERED);
		}
		for (Component tabComponent : tabbedPane.getComponents()) {
			if (tabComponent == component) {
				continue;
			}
			if (tabComponent.getClass() == ImageViewPanel.class) {
				((ImageViewPanel) component).setState(ImageViewPanel.STATE_EXITED);
			}
		}
	}
}
