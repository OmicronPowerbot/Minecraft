package org.qatide.minecraft.ui.event;

import java.util.EventObject;

/**
 * @author Omicron
 */
public final class ProgressEvent extends EventObject {
	private final String text;
	private final int progress;

	public ProgressEvent(Object source, String text, int progress) {
		super(source);
		this.text = text;
		this.progress = progress;
	}

	public String getText() {
		return text;
	}

	public int getProgress() {
		return progress;
	}
}
