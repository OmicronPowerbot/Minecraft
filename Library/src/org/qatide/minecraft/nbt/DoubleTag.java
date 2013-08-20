package org.qatide.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Omicron
 */
public final class DoubleTag extends Tag {
	private double value;

	DoubleTag(String name) {
		super(name);
	}

	public DoubleTag(String name, double value) {
		super(name);
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public byte getId() {
		return TAG_DOUBLE;
	}

	@Override
	protected void read(DataInput input) throws IOException {
		value = input.readDouble();
	}

	@Override
	protected void write(DataOutput output) throws IOException {
		output.writeDouble(value);
	}

	@Override
	public Tag copy() {
		return new DoubleTag(name, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (value != ((DoubleTag) obj).getValue()) {
			return false;
		}
		return true;
	}

	@Override
	protected String toString(int indent) {
		int count = indent;
		if (name != null) {
			count += 7 + name.length() + 3;
		}
		StringBuilder builder = new StringBuilder(count);
		builder.append(createTabs(indent));
		if (name != null) {
			builder.append("double ").append(name).append(" = ");
		}
		builder.append(value);
		return builder.toString();
	}
}
