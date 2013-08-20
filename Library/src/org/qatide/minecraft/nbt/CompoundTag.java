package org.qatide.minecraft.nbt;

import org.qatide.minecraft.MinecraftFormatException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Omicron
 */
public final class CompoundTag extends Tag {
	private Tag[] values;

	CompoundTag(String name) {
		super(name);
	}

	public CompoundTag(String name, Tag[] values) {
		super(name);
		this.values = values;
	}

	public Tag[] getValues() {
		return values;
	}

	@SuppressWarnings({ "unchecked" })
	public <T extends Tag> T getSubTag(String name) {
		for (Tag tag : values) {
			if (name.equals(tag.getName())) {
				return (T) tag;
			}
		}
		return null;
	}

	@SuppressWarnings({ "unchecked" })
	public <T extends Tag> T getSubTag(String name, Class<T> clazz) {
		for (Tag tag : values) {
			if (name.equals(tag.getName())) {
				if (clazz != tag.getClass()) {
					throw new ClassCastException(clazz.getName() + ' ' + tag.getClass().getName());
				}
				return (T) tag;
			}
		}
		return null;
	}

	@Override
	public byte getId() {
		return TAG_COMPOUND;
	}

	@Override
	protected void read(DataInput input) throws IOException, MinecraftFormatException {
		ArrayList<Tag> tags = new ArrayList<Tag>();
		while (true) {
			int id = input.readByte();
			if (id == 0) {
				values = tags.toArray(new Tag[tags.size()]);
				return;
			}
			Tag tag = Tag.createTag(id, input.readUTF());
			tag.read(input);
			tags.add(tag);
		}
	}

	@Override
	protected void write(DataOutput output) throws IOException {
		for (Tag tag : values) {
			output.writeByte(tag.getId());
			output.writeUTF(tag.getName());
			tag.write(output);
		}
		output.writeByte(0);
	}

	@Override
	public Tag copy() {
		return new CompoundTag(name, values);
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (!Arrays.equals(values, ((CompoundTag) obj).getValues())) {
			return false;
		}
		return true;
	}

	@Override
	protected String toString(int indent) {
		int count = indent;
		if (name != null) {
			count += 9 + name.length() + 3;
		}
		count += 14;
		count += (1 + 4 + 2) * values.length;
		count += 1 + indent + 1;
		StringBuilder builder = new StringBuilder(count);
		builder.append(createTabs(indent));
		if (name != null) {
			builder.append("Object[] ").append(name.isEmpty() ? "NULL" : name).append(" = ");
		}
		builder.append("new Object[] {");
		for (Tag value : values) {
			if (value.getId() == TAG_LIST) {
				if (((ListTag) value).getValues().length == 0) {
					continue;
				}
			}
			builder.append('\n');
			builder.append(value.toString(indent + 1)).append(", ");
		}
		builder.append('\n').append(createTabs(indent)).append('}');
		return builder.toString();
	}
}
