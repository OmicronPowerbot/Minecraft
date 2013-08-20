package org.qatide.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Omicron
 */
public final class ShortTag extends Tag {
	private int value;

	ShortTag(String name) {
		super(name);
	}

	public ShortTag(String name, int value) {
		super(name);
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public byte getId() {
		return TAG_SHORT;
	}

	@Override
	protected void read(DataInput input) throws IOException {
		value = input.readUnsignedShort();
	}

	@Override
	protected void write(DataOutput output) throws IOException {
		output.writeShort(value);
	}

	@Override
	public Tag copy() {
		return new ShortTag(name, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (value != ((ShortTag) obj).getValue()) {
			return false;
		}
		return true;
	}

	@Override
	protected String toString(int indent) {
		int count = indent;
		if (name != null) {
			count += 6 + name.length() + 3;
		}
		count += 6;
		StringBuilder builder = new StringBuilder(count);
		builder.append(createTabs(indent));
		if (name != null) {
			builder.append("short ").append(name).append(" = ");
		}
		builder.append(value);
		return builder.toString();
	}
}
