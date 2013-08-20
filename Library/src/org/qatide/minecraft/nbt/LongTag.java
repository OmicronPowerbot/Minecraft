package org.qatide.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Omicron
 */
public final class LongTag extends Tag {
	private long value;

	LongTag(String name) {
		super(name);
	}

	public LongTag(String name, long value) {
		super(name);
		this.value = value;
	}

	public long getValue() {
		return value;
	}

	@Override
	public byte getId() {
		return TAG_LONG;
	}

	@Override
	protected void read(DataInput input) throws IOException {
		value = input.readLong();
	}

	@Override
	protected void write(DataOutput output) throws IOException {
		output.writeLong(value);
	}

	@Override
	public Tag copy() {
		return new LongTag(name, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (value != ((LongTag) obj).getValue()) {
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
		count += 11;
		StringBuilder builder = new StringBuilder(count);
		builder.append(createTabs(indent));
		if (name != null) {
			builder.append("long ").append(name).append(" = ");
		}
		builder.append(value);
		return builder.toString();
	}
}
