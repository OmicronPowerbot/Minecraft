package org.qatide.minecraft;

import org.qatide.minecraft.definition.BlockDefinition;
import org.qatide.minecraft.graphics.Terrain;
import org.qatide.minecraft.graphics.TerrainImage;
import org.qatide.util.ColorUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Omicron
 */
public final class BlockDefinitionWrapper {

	private static final Object[] enabledData = new Object[]{ new int[]{ 1, 3, 4, 5, 17, 24, 35, 41, 42, 43, 80, 98 }, new int[][]{ null, null, null, null, new int[]{ 0 }, null, null, null, null, null, null, null } };
	private static BlockDefinitionWrapper[] wrappers;

	public static int[] getEnabledImageColors(Terrain terrain, boolean sideView) {
		if (wrappers == null) {
			populateWrappers();
		}
		int[] colors = new int[wrappers.length];
		int amount = 0;
		for (int i = 0; i < wrappers.length; i++) {
			if (!wrappers[i].isEnabled()) {
				continue;
			}
			colors[amount++] = terrain.getImage(sideView ? wrappers[i].getSideImage() : wrappers[i].getTopImage()).getAverageColor();
		}
		return Arrays.copyOf(colors, amount);
	}

	public static TerrainImage getTerrainImage(Terrain terrain, int color, boolean sideView) {
		for (int i = 0; i < wrappers.length; i++) {
			if (!wrappers[i].isEnabled()) {
				continue;
			}
			TerrainImage image = terrain.getImage(sideView ? wrappers[i].getSideImage() : wrappers[i].getTopImage());
			if (color == image.getAverageColor()) {
				return image;
			}
		}
		return null;
	}

	public static BlockDefinitionWrapper[] getWrappers() {
		if (wrappers == null) {
			populateWrappers();
		}
		return wrappers;
	}

	public static void populateWrappers() {
		List<BlockDefinitionWrapper> wrappers = new ArrayList<BlockDefinitionWrapper>();
		for (int i = 0; i < 256; i++) {
			BlockDefinition[] definitions = BlockDefinition.getDefinitions(i);
			int amount = definitions.length;
			for (int j = 0; j < amount; j++) {
				wrappers.add(new BlockDefinitionWrapper(definitions[j]));
			}
		}
		BlockDefinitionWrapper.wrappers = wrappers.toArray(new BlockDefinitionWrapper[wrappers.size()]);
	}

	private final BlockDefinition definition;
	private boolean enabled;

	public BlockDefinitionWrapper(BlockDefinition definition) {
		this.definition = definition;
		int[] enabledIds = (int[]) enabledData[0];
		int length = enabledIds.length;
		for (int index = 0; index < length; index++) {
			if (enabledIds[index] == definition.getId()) {
				int[] enabledDatas = ((int[][]) enabledData[1])[index];
				if (enabledDatas == null) {
					enabled = true;
					return;
				}
				for (int id : enabledDatas) {
					if (id == definition.getId()) {
						enabled = true;
						return;
					}
				}
				return;
			}
		}
	}

	public BlockDefinition getDefinition() {
		return definition;
	}

	public int getId() {
		return definition.getId();
	}

	public int getData() {
		return definition.getData();
	}

	public int getSideImage() {
		return definition.getSideImage();
	}

	public int getTopImage() {
		return definition.getTopImage();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
