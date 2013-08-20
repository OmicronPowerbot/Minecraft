package org.qatide.minecraft.nbt;

import org.qatide.minecraft.MinecraftFormatException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Omicron
 */
public final class IntArrayTag extends Tag {
	private int[] values;

	IntArrayTag(String name) {
		super(name);
	}

	public IntArrayTag(String name, int[] values) {
		super(name);
		this.values = values;
	}

	public int[] getValues() {
		return values;
	}

	@Override
	public byte getId() {
		return TAG_INT_ARRAY;
	}

	@Override
	protected void read(DataInput input) throws IOException {
		int length = input.readInt();
		values = new int[length];
		if (length <= 0) {
			throw new IOException("Length not of valid size.");
		}
		values = new int[length];
		for (int off = 0; off < length; off++) {
			values[off] = input.readInt();
		}
	}

	@Override
	protected void write(DataOutput output) throws IOException {
		int amount = values.length;
		output.writeInt(amount);
		for (int i = 0; i < amount; i++) {
			output.writeInt(values[i]);
		}
	}

	@Override
	public Tag copy() {
		return new IntArrayTag(name, values);
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (!Arrays.equals(values, ((IntArrayTag) obj).getValues())) {
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
		count += 12;
		count += (11 + 2) * values.length;
		count += 1;
		StringBuilder builder = new StringBuilder(count);
		builder.append(createTabs(indent));
		if (name != null) {
			builder.append("int[] ").append(name).append(" = ");
		}
		builder.append("new int[] { ");
		for (int value : values) {
			builder.append(value).append(", ");
		}
		builder.append('}');
		return builder.toString();
	}
}
