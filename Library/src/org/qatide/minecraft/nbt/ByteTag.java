package org.qatide.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Omicron
 */
public final class ByteTag extends Tag {
	private int value;

	ByteTag(String name) {
		super(name);
	}

	public ByteTag(String name, int value) {
		super(name);
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public byte getId() {
		return TAG_BYTE;
	}

	@Override
	protected void read(DataInput input) throws IOException {
		value = input.readUnsignedByte();
	}

	@Override
	protected void write(DataOutput output) throws IOException {
		output.writeByte(value);
	}

	@Override
	public Tag copy() {
		return new ByteTag(name, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (value != ((ByteTag) obj).getValue()) {
			return false;
		}
		return true;
	}

	@Override
	protected String toString(int indent) {
		int count = indent;
		if (name != null) {
			count += 5 + name.length() + 3;
		}
		count += 4;
		StringBuilder builder = new StringBuilder(count);
		builder.append(createTabs(indent));
		if (name != null) {
			builder.append("byte ").append(name).append(" = ");
		}
		builder.append(value);
		return builder.toString();
	}
}
