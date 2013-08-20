package org.qatide.minecraft.world.level.entity.mobile;

import org.qatide.minecraft.nbt.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omicron
 */
public abstract class Mobile implements NBTSerializable<CompoundTag, List<Tag>> {
	protected double x, y, z;
	protected double motionX, motionY, motionZ;
	protected float yaw, pitch;
	protected boolean onGround;
	protected int air;
	protected int fire;
	protected float fallDistance;

	protected Mobile() {
	}

	@Override
	public void deserialize(CompoundTag tree) {
		ListTag positions = tree.getSubTag("Pos", ListTag.class);
		x = positions.getValue(0, DoubleTag.class).getValue();
		y = positions.getValue(1, DoubleTag.class).getValue();
		z = positions.getValue(2, DoubleTag.class).getValue();
		ListTag motions = tree.getSubTag("Motion", ListTag.class);
		motionX = motions.getValue(0, DoubleTag.class).getValue();
		motionY = motions.getValue(1, DoubleTag.class).getValue();
		motionZ = motions.getValue(2, DoubleTag.class).getValue();
		ListTag rotations = tree.getSubTag("Rotation", ListTag.class);
		yaw = rotations.getValue(0, FloatTag.class).getValue();
		pitch = rotations.getValue(1, FloatTag.class).getValue();
		onGround = tree.getSubTag("OnGround", ByteTag.class).getValue() == 1;
		air = tree.getSubTag("Air", ShortTag.class).getValue();
		fire = tree.getSubTag("Fire", ShortTag.class).getValue();
		fallDistance = tree.getSubTag("FallDistance", FloatTag.class).getValue();
	}

	@Override
	public List<Tag> serialize() {
		List<Tag> tags = new ArrayList<Tag>();
		{
			List<Tag> posTags = new ArrayList<Tag>();
			posTags.add(new DoubleTag(null, x));
			posTags.add(new DoubleTag(null, y));
			posTags.add(new DoubleTag(null, z));
			tags.add(new ListTag("Pos", Tag.TAG_DOUBLE, posTags.toArray(new Tag[posTags.size()])));
		}
		{
			List<Tag> motionTags = new ArrayList<Tag>();
			motionTags.add(new DoubleTag(null, motionX));
			motionTags.add(new DoubleTag(null, motionY));
			motionTags.add(new DoubleTag(null, motionZ));
			tags.add(new ListTag("Motion", Tag.TAG_DOUBLE, motionTags.toArray(new Tag[motionTags.size()])));
		}
		{
			List<Tag> rotationsTags = new ArrayList<Tag>();
			rotationsTags.add(new FloatTag(null, yaw));
			rotationsTags.add(new FloatTag(null, pitch));
			tags.add(new ListTag("Rotation", Tag.TAG_FLOAT, rotationsTags.toArray(new Tag[rotationsTags.size()])));
		}
		tags.add(new ByteTag("OnGround", (byte) (onGround ? 1 : 0)));
		tags.add(new ShortTag("Air", air));
		tags.add(new ShortTag("Fire", fire));
		tags.add(new FloatTag("FallDistance", fallDistance));
		return tags;
	}
}