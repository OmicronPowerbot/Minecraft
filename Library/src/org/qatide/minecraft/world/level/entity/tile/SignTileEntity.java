package org.qatide.minecraft.world.level.entity.tile;

import org.qatide.minecraft.nbt.CompoundTag;
import org.qatide.minecraft.nbt.StringTag;
import org.qatide.minecraft.nbt.Tag;

import java.util.List;

/**
 * @author Omicron
 */
public final class SignTileEntity extends TileEntity {
	private String text1;
	private String text2;
	private String text3;
	private String text4;

	public SignTileEntity() {
		super("Sign");
	}

	@Override
	public void deserialize(CompoundTag tag) {
		text1 = tag.getSubTag("Text1", StringTag.class).getValue();
		text2 = tag.getSubTag("Text2", StringTag.class).getValue();
		text3 = tag.getSubTag("Text3", StringTag.class).getValue();
		text4 = tag.getSubTag("Text4", StringTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = super.serialize();
		tags.add(new StringTag("Text1", text1));
		tags.add(new StringTag("Text2", text2));
		tags.add(new StringTag("Text3", text3));
		tags.add(new StringTag("Text4", text4));
		return tags;
	}
}
