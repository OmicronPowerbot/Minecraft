package org.qatide.minecraft.world.region;

import org.qatide.minecraft.nbt.*;

/**
 * @author Omicron
 */
public final class Section implements NBTSerializable<CompoundTag, Tag[]> {
	private final int y;
	private byte[] blocks;
	private byte[] data;
	private byte[] skyLight;
	private byte[] blockLight;
	private boolean initialized;

	public Section(int y) {
		this.y = y;
	}

	private void init(boolean create) {
		if (initialized) {
			return;
		}
		if (create) {
			blocks = new byte[Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE];
			data = new byte[(Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE) / 2];
			data = new byte[(Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE) / 2];
			skyLight = new byte[(Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE) / 2];
			blockLight = new byte[(Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE) / 2];
			for (int i = 0; i < (Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE) / 2; i++) {
				skyLight[i] = 15;
			}
		}
		initialized = true;
	}

	int getBlock(int index) {
		return blocks[index];
	}

	int getData(int index) {
		if ((index % 2) == 0) {
			return data[index / 2] & 0xFF;
		}
		return data[index / 2] >> 4;
	}

	int getSkyLight(int index) {
		if ((index % 2) == 0) {
			return skyLight[index / 2] & 0xFF;
		}
		return skyLight[index / 2] >> 4;
	}

	int getBlockLight(int index) {
		if ((index % 2) == 0) {
			return blockLight[index / 2] & 0xFF;
		}
		return blockLight[index / 2] >> 4;
	}

	void setBlock(int block, int index) {
		this.blocks[index] = (byte) block;
	}

	void setData(int data, int index) {
		if ((index % 2) != 0) {
			data <<= 4;
		}
		this.data[index / 2] = (byte) data;
	}

	void setSkyLight(int skyLight, int index) {
		if ((index % 2) != 0) {
			skyLight <<= 4;
		}
		this.skyLight[index / 2] = (byte) skyLight;
	}

	void setBlockLight(int blockLight, int index) {
		if ((index % 2) != 0) {
			blockLight <<= 4;
		}
		this.blockLight[index / 2] = (byte) blockLight;
	}

	public Block getBlock(int x, int z, int y) {
		init(true);
		return new Block(this, (((y * Chunk.CHUNK_SIZE) + z) * Chunk.CHUNK_SIZE) + x);
	}

	public boolean isEmpty() {
		if (!initialized) {
			return true;
		}
		for (byte block : blocks) {
			if (block != 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void deserialize(CompoundTag tag) {
		init(false);
		blocks = tag.getSubTag("Blocks", ByteArrayTag.class).getValues();
		data = tag.getSubTag("Data", ByteArrayTag.class).getValues();
		skyLight = tag.getSubTag("SkyLight", ByteArrayTag.class).getValues();
		blockLight = tag.getSubTag("BlockLight", ByteArrayTag.class).getValues();
	}

	@Override
	public Tag[] serialize() {
		Tag[] tags = new Tag[5];
		tags[0] = new ByteTag("Y", y);
		tags[1] = new ByteArrayTag("Blocks", blocks);
		tags[2] = new ByteArrayTag("Data", data);
		tags[3] = new ByteArrayTag("SkyLight", skyLight);
		tags[4] = new ByteArrayTag("BlockLight", blockLight);
		return tags;
	}
}
