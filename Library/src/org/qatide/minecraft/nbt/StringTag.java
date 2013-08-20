package org.qatide.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Omicron
 */
public final class StringTag extends Tag {
	private String value;

	StringTag(String name) {
		super(name);
	}

	public StringTag(String name, String value) {
		super(name);
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public byte getId() {
		return TAG_STRING;
	}

	@Override
	protected void read(DataInput input) throws IOException {
		value = input.readUTF();
	}

	@Override
	protected void write(DataOutput output) throws IOException {
		output.writeUTF(value);
	}

	@Override
	public Tag copy() {
		return new StringTag(name, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (!value.equals(((StringTag) obj).getValue())) {
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
		count += value.length();
		StringBuilder builder = new StringBuilder(count);
		builder.append(createTabs(indent));
		if (name != null) {
			builder.append("String ").append(name).append(" = ");
		}
		builder.append(value);
		return builder.toString();
	}
}
