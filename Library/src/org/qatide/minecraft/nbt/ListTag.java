package org.qatide.minecraft.nbt;

import org.qatide.minecraft.MinecraftFormatException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Omicron
 */
public final class ListTag extends Tag {
	private int listId;
	private Tag[] values;

	ListTag(String name) {
		super(name);
	}

	public ListTag(String name, int listId, Tag[] values) {
		super(name);
		this.listId = listId;
		this.values = values;
	}

	public int getListId() {
		return listId;
	}

	public Tag[] getValues() {
		return values;
	}

	@SuppressWarnings({ "unchecked" })
	public <T extends Tag> T getValue(int index, Class<T> clazz) {
		Tag value = values[index];
		if (clazz != value.getClass()) {
			throw new ClassCastException();
		}
		return (T) value;
	}

	@Override
	public byte getId() {
		return TAG_LIST;
	}

	@Override
	protected void read(DataInput input) throws IOException, MinecraftFormatException {
		listId = input.readUnsignedByte();
		int length = input.readInt();
		if (length < 0) {
			throw new MinecraftFormatException("Length not of valid size: " + length);
		}
		values = new Tag[length];
		for (int i = 0; i < length; i++) {
			values[i] = Tag.createTag(listId, null);
			values[i].read(input);
		}
	}

	@Override
	protected void write(DataOutput output) throws IOException {
		output.writeByte(listId);
		output.writeInt(values.length);
		for (Tag tag : values) {
			tag.write(output);
		}
	}

	@Override
	public Tag copy() {
		return new ListTag(name, listId, values);
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		ListTag listTag = (ListTag) obj;
		if (listId != listTag.getListId()) {
			return false;
		}
		if (!Arrays.equals(values, listTag.getValues())) {
			return false;
		}
		return true;
	}

	@Override
	protected String toString(int indent) {
		String typeName = getTypeName();
		int count = indent;
		if (name != null) {
			count += typeName.length() + 3 + name.length() + 3;
		}
		count += 4 + typeName.length() + 4;
		count += (1 + 4 + 2) * values.length;
		count += 1 + indent + 1;
		StringBuilder builder = new StringBuilder(count);
		builder.append(createTabs(indent));
		if (name != null) {
			builder.append(typeName).append("[] ").append(name.isEmpty() ? "NULL" : name).append(" = ");
		}
		builder.append("new ").append(typeName).append("[] {");
		for (Tag value : values) {
			builder.append('\n');
			builder.append(value.toString(indent + 1)).append(", ");
		}
		builder.append('\n').append(createTabs(indent)).append('}');
		return builder.toString();
	}

	private String getTypeName() {
		switch (listId) {
			case TAG_BYTE: {
				return "byte";
			}
			case TAG_SHORT: {
				return "short";
			}
			case TAG_INT: {
				return "int";
			}
			case TAG_LONG: {
				return "long";
			}
			case TAG_FLOAT: {
				return "float";
			}
			case TAG_DOUBLE: {
				return "double";
			}
			case TAG_BYTE_ARRAY: {
				return "byte[]";
			}
			case TAG_STRING: {
				return "String";
			}
			case TAG_LIST: {
				return "List";
			}
			case TAG_COMPOUND: {
				return "Object[]";
			}
			case TAG_INT_ARRAY: {
				return "int[]";
			}
		}
		throw new AssertionError();
	}
}
