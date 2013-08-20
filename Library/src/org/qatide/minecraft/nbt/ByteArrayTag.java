package org.qatide.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Omicron
 */
public final class ByteArrayTag extends Tag {
	private byte[] values;

	ByteArrayTag(String name) {
		super(name);
	}

	public ByteArrayTag(String name, byte[] values) {
		super(name);
		this.values = values;
	}

	public byte[] getValues() {
		return values;
	}

	@Override
	public byte getId() {
		return TAG_BYTE_ARRAY;
	}

	@Override
	protected void read(DataInput input) throws IOException {
		int length = input.readInt();
		if (length <= 0) {
			throw new IOException("Length not of valid size.");
		}
		values = new byte[length];
		input.readFully(values);
	}

	@Override
	protected void write(DataOutput output) throws IOException {
		output.writeInt(values.length);
		output.write(values);
	}

	@Override
	public Tag copy() {
		return new ByteArrayTag(name, values);
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (!Arrays.equals(values, ((ByteArrayTag) obj).getValues())) {
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
		count += 13;
		count += (4 + 2) * values.length;
		count += 1;
		StringBuilder builder = new StringBuilder(count);
		builder.append(createTabs(indent));
		if (name != null) {
			builder.append("byte[] ").append(name).append(" = ");
		}
		builder.append("new byte[] { ");
		for (byte value : values) {
			builder.append(value).append(", ");
		}
		builder.append('}');
		return builder.toString();
	}
}
