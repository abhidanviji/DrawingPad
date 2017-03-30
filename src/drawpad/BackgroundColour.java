package drawpad;

import java.awt.Graphics;

public class BackgroundColour extends TwoEndsShape {

	ScribbleCanvas canvas;

	public BackgroundColour(ScribbleCanvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void drawOutline(Graphics g, int x1, int y1, int x2, int y2) {
		

	}

	@Override
	public void draw(Graphics g) {
		if (color != null) {
			g.setColor(color);
		}
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

}
