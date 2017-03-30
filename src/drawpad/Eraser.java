package drawpad;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class Eraser extends TwoEndsShape {

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(10));
		g2.drawLine(x1, y1, x2, y2);
	}

	public void drawOutline(Graphics g, int x1, int y1, int x2, int y2) {

	}

}