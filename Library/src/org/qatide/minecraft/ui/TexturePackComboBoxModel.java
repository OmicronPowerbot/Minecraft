package org.qatide.minecraft.ui;

import org.qatide.minecraft.Minecraft;
import org.qatide.minecraft.graphics.TexturePack;

import javax.swing.*;

/**
 * @author Omicron
 */
public final class TexturePackComboBoxModel extends AbstractListModel implements ComboBoxModel {
	private final TexturePack[] items;
	private final int size;
	private TexturePack selectedItem;

	public TexturePackComboBoxModel(Minecraft minecraft) {
		TexturePack[] items = minecraft.getTexturePacks();
		this.size = items.length + 1;
		this.items = new TexturePack[size];
		this.items[0] = minecraft.getDefaultTexturePack();
		System.arraycopy(items, 0, this.items, 1, items.length);
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
		selectedItem = (TexturePack) anItem;
		fireContentsChanged(this, -1, -1);
	}

	@Override
	public TexturePack getSelectedItem() {
		return selectedItem;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public TexturePack getElementAt(int index) {
		return items[index];
	}
}
