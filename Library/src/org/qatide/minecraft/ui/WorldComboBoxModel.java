/*
 * Created by JFormDesigner on Sun Apr 08 14:16:24 EDT 2012
 */

package org.qatide.minecraft.ui;

import org.qatide.minecraft.Minecraft;
import org.qatide.minecraft.world.World;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * @author Pim de Witte
 */
public final class WorldComboBoxModel extends AbstractListModel implements ComboBoxModel {
	private final World[] items;
	private final int size;
	private World selectedItem;

	public WorldComboBoxModel(Minecraft minecraft) {
		items = minecraft.getWorlds();
		this.size = items.length;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		if (anItem == null) {
			return;
		}
		if (selectedItem != null) {
			if (!selectedItem.equals( anItem )) {
				return;
			}
		}
		selectedItem = (World) anItem;
		fireContentsChanged(this, -1, -1);
	}

	@Override
	public World getSelectedItem() {
		return selectedItem;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public World getElementAt(int index) {
		return items[index];
	}
}
