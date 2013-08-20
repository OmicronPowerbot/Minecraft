package org.qatide.minecraft.ui;

import org.qatide.minecraft.ui.event.ProgressEvent;
import org.qatide.minecraft.ui.event.ProgressListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * @author Omicron
 */
public final class ImageViewPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, ProgressListener {
	public static final float MIN_SCALE = 1.0F;
	public static final float MAX_SCALE = 64.0F;

	public static final int STATE_EXITED = 0;
	public static final int STATE_ENTERED = 1;

	private boolean disposeOnExit;
	private final ArrayList<ChangeListener> changeListeners = new ArrayList<ChangeListener>();
	private Image image;
	private float scale = MIN_SCALE;
	private boolean fitToSize = true;
	private int imageX;
	private int imageY;
	private int imageWidth;
	private int imageHeight;
	private Point initialDragPoint;
	private int initialImageX;
	private int initialImageY;

	public ImageViewPanel(boolean disposeOnExit) {
		this.disposeOnExit = disposeOnExit;
		addMouseListener(this);
		addMouseWheelListener(this);
		addMouseMotionListener(this);
	}

	public void update(Image image, int width, int height) {
		this.image = image;
		this.imageWidth = width;
		this.imageHeight = height;
		//Utils.setCursorFromImage(ImageManager.getHandOpen().getImage(), this);
	}

	public void checkBounds() {
		int panelWidth = this.getWidth();
		int panelHeight = this.getHeight();
		int imageWidth = (int) (this.imageWidth * scale);
		if (imageWidth < panelWidth) {
			if (imageX < 0) {
				imageX = 0;
			} else if (imageX > panelWidth - imageWidth) {
				imageX = panelWidth - imageWidth;
			}
		} else if (imageWidth > panelWidth) {
			if (imageX < panelWidth - imageWidth) {
				imageX = panelWidth - imageWidth;
			} else if (imageX > 0) {
				imageX = 0;
			}
		} else {
			imageX = 0;
		}
		int imageHeight = (int) (this.imageHeight * scale);
		if (imageHeight < panelHeight) {
			if (imageY < 0) {
				imageY = 0;
			} else if (imageY > panelHeight - imageHeight) {
				imageY = panelHeight - imageHeight;
			}
		} else if (imageHeight > panelHeight) {
			if (imageY < panelHeight - imageHeight) {
				imageY = panelHeight - imageHeight;
			} else if (imageY > 0) {
				imageY = 0;
			}
		} else {
			imageY = 0;
		}
	}

	private void restrictZoom() {
		if (scale < MIN_SCALE) {
			scale = MIN_SCALE;
		}
		if (scale > MAX_SCALE) {
			scale = MAX_SCALE;
		}
	}

	public void setState(int state) {
		if (state == STATE_ENTERED) {
			this.getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());
			ChangeEvent event = new ChangeEvent(this);
			for (ChangeListener changeListener : changeListeners) {
				changeListener.stateChanged(event);
			}
		} else if (state == STATE_EXITED) {
			if (disposeOnExit) {
				image = null;
			}
		}
	}

	public void addChangeListener(ChangeListener l) {
		changeListeners.add(l);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		initialDragPoint = e.getPoint();
		initialImageX = imageX;
		initialImageY = imageY;
		//Utils.setCursorFromImage(ImageManager.getHandClosed().getImage(), ImageViewPanel.this);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//Utils.setCursorFromImage(ImageManager.getHandOpen().getImage(), ImageViewPanel.this);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		fitToSize = false;
		Point point = e.getPoint();
		imageX = (int) (initialImageX - (initialDragPoint.getX() - point.getX()));
		imageY = (int) (initialImageY - (initialDragPoint.getY() - point.getY()));
		checkBounds();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		fitToSize = false;
		Point panelPoint = e.getPoint();
		Point imagePoint = new Point((int) Math.round((double) (panelPoint.x - imageX) / scale), (int) Math.round((double) (panelPoint.y - imageY) / scale));
		if (notches < 0) {
			scale *= 1.0F - (notches * 0.2F);
		} else {
			scale /= 1.0F + (notches * 0.2F);
		}
		restrictZoom();
		imageX = (int) Math.round(panelPoint.getX() - imagePoint.getX() * scale);
		imageY = (int) Math.round(panelPoint.getY() - imagePoint.getY() * scale);
		checkBounds();
		repaint();
	}

	@Override
	public void progressUpdate(ProgressEvent progressEvent) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image == null) {
			return;
		}
		float imageWidth = this.imageWidth;
		float imageHeight = this.imageHeight;
		if (fitToSize) {
			float wRatio = (float) this.getWidth() / imageWidth;
			float hRatio = (float) this.getHeight() / imageHeight;
			scale = hRatio < wRatio ? hRatio : wRatio;
			restrictZoom();
			imageX = (int) ((getWidth() - (imageWidth * scale)) / 2);
			imageY = (int) ((getHeight() - (imageHeight * scale)) / 2);
		}
		g.drawImage(image, imageX, imageY, (int) (imageWidth * scale), (int) (imageHeight * scale), null);
	}
}
