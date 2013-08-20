package org.qatide.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Omicron
 */
public final class IntTag extends Tag {
	private int value;

	IntTag(String name) {
		super(name);
	}

	public IntTag(String name, int value) {
		super(name);
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public byte getId() {
		return TAG_INT;
	}

	@Override
	protected void read(DataInput input) throws IOException {
		value = input.readInt();
	}

	@Override
	protected void write(DataOutput output) throws IOException {
		output.writeInt(value);
	}

	@Override
	public Tag copy() {
		return new IntTag(name, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (value != ((IntTag) obj).getValue()) {
			return false;
		}
		return true;
	}

	@Override
	protected String toString(int indent) {
		int count = indent;
		if (name != null) {
			count += 4 + name.length() + 3;
		}
		count += 11;
		StringBuilder builder = new StringBuilder(count);
		builder.append(createTabs(indent));
		if (name != null) {
			builder.append("int ").append(name).append(" = ");
		}
		builder.append(value);
		return builder.toString();
	}
}
