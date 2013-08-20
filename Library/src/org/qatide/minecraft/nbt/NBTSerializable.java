package org.qatide.minecraft.nbt;

import org.qatide.minecraft.MinecraftFormatException;

/**
 * @author Omicron
 */
public interface NBTSerializable<E extends Tag, V> {
	public void deserialize(E tag) throws MinecraftFormatException;

	public V serialize();
}
