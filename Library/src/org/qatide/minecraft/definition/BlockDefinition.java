package org.qatide.minecraft.definition;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author Omicron
 */
public final class BlockDefinition {
	private static BlockDefinition[][] definitions;

	public static void grow(int id) {
		int oldLength = definitions[id].length;
		if (oldLength > 15) {
			return;
		}
		definitions[id] = Arrays.copyOf(definitions[id], oldLength + 1);
		definitions[id][oldLength] = new BlockDefinition(id, oldLength);
	}

	public static BlockDefinition[] getDefinitions(int id) {
		if (definitions == null) {
			load();
		}
		return definitions[id];
	}

	public static BlockDefinition getDefinition(int id) {
		return getDefinition(id, 0);
	}

	public static BlockDefinition getDefinition(int id, int data) {
		if (definitions == null) {
			load();
		}
		return definitions[id][data];
	}

	private static void load() {
		DataInputStream inputStream = null;
		try {
			inputStream = new DataInputStream(new GZIPInputStream(new URL("http://qatide.com/minecraft/resources/blockdefinition.dat").openStream()));
			definitions = new BlockDefinition[256][];
			for (int i = 0; i < 256; i++) {
				int amount = inputStream.readByte();
				definitions[i] = new BlockDefinition[amount];
				for (int j = 0; j < amount; j++) {
					BlockDefinition definition = definitions[i][j] = new BlockDefinition(i, j);
					while (true) {
						int opcode = inputStream.readByte();
						if (opcode == 0) {
							break;
						}
						if (opcode == 1) {
							definition.sideImage = inputStream.readUnsignedByte();
							definition.topImage = definition.sideImage;
						} else if (opcode == 2) {
							definition.sideImage = inputStream.readUnsignedByte();
							definition.topImage = inputStream.readUnsignedByte();
						}
					}

				}
			}
		} catch (IOException ignored) {
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	public static void save() {
		DataOutputStream outputStream = null;
		try {
			outputStream = new DataOutputStream(new GZIPOutputStream(new FileOutputStream("blockdefinition.dat")));
			for (int i = 0; i < 256; i++) {
				int amount = definitions[i].length;
				outputStream.writeByte(amount);
				for (int j = 0; j < amount; j++) {
					BlockDefinition definition = definitions[i][j];
					if (definition.getSideImage() == definition.getTopImage()) {
						outputStream.writeByte(1);
						outputStream.writeByte(definition.getSideImage());
					} else {
						outputStream.writeByte(2);
						outputStream.writeByte(definition.getSideImage());
						outputStream.writeByte(definition.getTopImage());
					}
					outputStream.writeByte(0);
				}
			}
		} catch (IOException ignored) {
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	private final int id;
	private final int data;
	private int sideImage;
	private int topImage;

	public BlockDefinition(int id, int data) {
		this.id = id;
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public int getData() {
		return data;
	}

	public int getSideImage() {
		return sideImage;
	}

	public int getTopImage() {
		return topImage;
	}

	public void setSideImage(int sideImage) {
		this.sideImage = sideImage;
	}

	public void setTopImage(int topImage) {
		this.topImage = topImage;
	}
}
