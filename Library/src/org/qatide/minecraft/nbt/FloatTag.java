package org.qatide.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Omicron
 */
public final class FloatTag extends Tag {
	private float value;

	FloatTag(String name) {
		super(name);
	}

	public FloatTag(String name, float value) {
		super(name);
		this.value = value;
	}

	public float getValue() {
		return value;
	}

	@Override
	public byte getId() {
		return TAG_FLOAT;
	}

	@Override
	protected void read(DataInput input) throws IOException {
		value = input.readFloat();
	}

	@Override
	protected void write(DataOutput output) throws IOException {
		output.writeFloat(value);
	}

	@Override
	public Tag copy() {
		return new FloatTag(name, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (value != ((FloatTag) obj).getValue()) {
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
		StringBuilder builder = new StringBuilder(count);
		builder.append(createTabs(indent));
		if (name != null) {
			builder.append("float ").append(name).append(" = ");
		}
		builder.append(value);
		return builder.toString();
	}
}
