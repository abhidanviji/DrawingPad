package drawpad;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class NewDrawPad extends DrawingPad2 {
	Image img;

	public NewDrawPad(String title) {
		super(title);
	}

	protected void initTools() {
		super.initTools();
		toolkit.addTool(new TwoEndsShapeTool(canvas, "RoundRect", new RoundedRectangleShape()));
		toolkit.addTool(new TwoEndsShapeTool(canvas, "FilledRoundRect", new FilledRoundedRectangleShape()));
		toolkit.addTool(new TwoEndsShapeTool(canvas, "3DRect", new ThreeDRectangleShape()));
		toolkit.addTool(new TwoEndsShapeTool(canvas, "Filled3DRect", new FilledThreeDRectangleShape()));
		toolkit.addTool(new TwoEndsShapeTool(canvas, "BoundedCircle", new BoundedOvalShape()));
		toolkit.addTool(new TwoEndsShapeTool(canvas, "FilledBoundedOval", new FilledBoundedOvalShape()));
		toolkit.addTool(new TwoEndsShapeTool(canvas, "Eraser Small", new Eraser()));
		toolkit.addTool(new TwoEndsShapeTool(canvas, "Eraser Medium", new Eraser1()));
		toolkit.addTool(new TwoEndsShapeTool(canvas, "Eraser Large", new Eraser2()));
		toolkit.addTool(new TwoEndsShapeTool(canvas, "Background", new Background()));
		toolkit.addTool(new TwoEndsShapeTool(canvas, "BackgroundColour", new BackgroundColour(canvas)));
	}

	public static void main(String[] args) {
		JFrame frame = new NewDrawPad("Abhi's Drawing Pad");
		frame.setSize(width, height);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2);
		frame.show();
	}

}
