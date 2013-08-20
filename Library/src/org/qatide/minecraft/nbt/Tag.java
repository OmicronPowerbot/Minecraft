package org.qatide.minecraft.nbt;

import org.qatide.minecraft.MinecraftFormatException;

import java.io.*;

/**
 * @author Omicron
 */
public abstract class Tag {
	public static final int TAG_BYTE = 1;
	public static final int TAG_SHORT = 2;
	public static final int TAG_INT = 3;
	public static final int TAG_LONG = 4;
	public static final int TAG_FLOAT = 5;
	public static final int TAG_DOUBLE = 6;
	public static final int TAG_BYTE_ARRAY = 7;
	public static final int TAG_STRING = 8;
	public static final int TAG_LIST = 9;
	public static final int TAG_COMPOUND = 10;
	public static final int TAG_INT_ARRAY = 11;

	static String createTabs(int tabs) {
		StringBuilder builder = new StringBuilder(tabs);
		for (int i = 0; i < tabs; i++) {
			builder.append('\t');
		}
		return builder.toString();
	}

	public static byte[] createNbtFile(CompoundTag tag) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			dataOutputStream.writeByte(tag.getId());
			dataOutputStream.writeUTF(tag.getName());
			tag.write(dataOutputStream);
			return outputStream.toByteArray();
		} catch (IOException e) {
			throw new AssertionError(e);
		}
	}

	public static CompoundTag parseNBTFile(InputStream inputStream) throws IOException, MinecraftFormatException {
		try {
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			byte id = dataInputStream.readByte();
			if (id == 0) {
				return null;
			}
			if (id != TAG_COMPOUND) {
				return null;
			}
			Tag tag = createTag(id, dataInputStream.readUTF());
			tag.read(dataInputStream);
			return (CompoundTag) tag;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected static Tag createTag(int id, String name) throws IOException, MinecraftFormatException {
		switch (id) {
			case 1: {
				return new ByteTag(name);
			}
			case 2: {
				return new ShortTag(name);
			}
			case 3: {
				return new IntTag(name);
			}
			case 4: {
				return new LongTag(name);
			}
			case 5: {
				return new FloatTag(name);
			}
			case 6: {
				return new DoubleTag(name);
			}
			case 7: {
				return new ByteArrayTag(name);
			}
			case 8: {
				return new StringTag(name);
			}
			case 9: {
				return new ListTag(name);
			}
			case 10: {
				return new CompoundTag(name);
			}
			case 11: {
				return new IntArrayTag(name);
			}
			default: {
				throw new MinecraftFormatException("Unknown tag: " + id);
			}
		}
	}

	protected final String name;

	protected Tag(String name) {
		this.name = name;
	}

	public abstract byte getId();

	protected abstract void read(DataInput input) throws IOException, MinecraftFormatException;

	protected abstract void write(DataOutput output) throws IOException;

	public String getName() {
		return name;
	}

	public abstract Tag copy();

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Tag)) {
			return false;
		}
		Tag o = (Tag) obj;
		if (getId() != o.getId()) {
			return false;
		}
		if (name == null && o.name != null || name != null && o.name == null) {
			return false;
		}
		if (name != null && !name.equals(o.name)) {
			return false;
		}
		return true;
	}

	@Override
	public final String toString() {
		return toString(0);
	}

	protected abstract String toString(int indent);
}
