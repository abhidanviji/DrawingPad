package drawpad;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

class Background extends TwoEndsShape {

	@Override
	public void drawOutline(Graphics g, int x1, int y1, int x2, int y2) {

	}

	@Override
	public void draw(Graphics g) {

		try {

			Image img = ImageIO.read(new File("C:\\Users\\Abhi\\workspace\\DrawingPad\\scen.jpg"));
			g.drawImage(img, 0, 0, null);

		} catch (Exception e) {

		}

	}

}
