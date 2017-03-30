package drawpadtest;

import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;

public class PaintBrush extends Applet implements MouseListener, MouseMotionListener, ActionListener {
	// component declarations
	Button blackButton;
	Button redButton;
	Button greenButton;
	Button blueButton;
	Button penButton;
	Button lineButton;
	Button eraserButton;
	Button clearButton;
	Button squareButton;
	Button ovalButton;
	Button fillSquareButton;
	Button fillOvalButton;
	Label colourTitle;
	Label colourDisplay;
	Label toolTitle;
	Label toolDisplay;

	// required variables to store settings
	Color currentColour;
	int toolType;

	// variables to store interim coordinates
	int oldX = -1;
	int oldY = -1;

	public void init() {
		// create all the buttons
		blackButton = new Button("Black");
		redButton = new Button("Red");
		greenButton = new Button("Green");
		blueButton = new Button("Blue");
		penButton = new Button("Pen");
		lineButton = new Button("Line");
		eraserButton = new Button("Eraser");
		clearButton = new Button("Clear");
		squareButton = new Button("Square");
		ovalButton = new Button("Oval");
		fillSquareButton = new Button("F Square");
		fillOvalButton = new Button("F Oval");

		// add action listners for all the buttons
		blackButton.addActionListener(this);
		redButton.addActionListener(this);
		greenButton.addActionListener(this);
		blueButton.addActionListener(this);
		penButton.addActionListener(this);
		lineButton.addActionListener(this);
		eraserButton.addActionListener(this);
		clearButton.addActionListener(this);
		squareButton.addActionListener(this);
		ovalButton.addActionListener(this);
		fillSquareButton.addActionListener(this);
		fillOvalButton.addActionListener(this);

		// create the button bar panel
		Panel buttonPanel = new Panel();
		buttonPanel.setLayout(new GridLayout(6, 2));
		buttonPanel.add(blackButton);
		buttonPanel.add(redButton);
		buttonPanel.add(greenButton);
		buttonPanel.add(blueButton);
		buttonPanel.add(penButton);
		buttonPanel.add(lineButton);
		buttonPanel.add(eraserButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(squareButton);
		buttonPanel.add(ovalButton);
		buttonPanel.add(fillSquareButton);
		buttonPanel.add(fillOvalButton);

		// create the status bar panel
		colourTitle = new Label("Colour: ");
		currentColour = Color.black;
		colourDisplay = new Label("Black");
		toolTitle = new Label("   Selected Tool: ");
		toolType = 0;
		toolDisplay = new Label("Pen (Scribble)");
		Panel statusPanel = new Panel();
		statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		statusPanel.add(colourTitle);
		statusPanel.add(colourDisplay);
		statusPanel.add(toolTitle);
		statusPanel.add(toolDisplay);

		// arrange final panels in a border layout
		this.setLayout(new BorderLayout());
		this.add(buttonPanel, BorderLayout.WEST);
		this.add(statusPanel, BorderLayout.NORTH);

		// add mouse listners to applet
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	// event handling for when an action occurs (in this case, whenever
	// a button is pressed)
	public void actionPerformed(ActionEvent e) {
		// get the local graphics page to draw on
		Graphics page = this.getGraphics();

		// red button pressed: set the current colour to red
		// and update the status display
		if (e.getSource().equals(redButton)) {
			currentColour = Color.red;
			colourDisplay.setText("Red");
		}

		// blue button pressed: set the current colour to blue
		// and update the status display
		if (e.getSource().equals(blueButton)) {
			currentColour = Color.blue;
			colourDisplay.setText("Blue");
		}

		// green button pressed: set the current colour to green
		// and update the status display
		if (e.getSource().equals(greenButton)) {
			currentColour = Color.green;
			colourDisplay.setText("Green");
		}

		// black button pressed: set the current colour to black
		// and update the status display
		if (e.getSource().equals(blackButton)) {
			currentColour = Color.black;
			colourDisplay.setText("Black");
		}

		// pen button pressed: set the selected tool to '0'
		// and update the status display
		if (e.getSource().equals(penButton)) {
			toolType = 0;
			toolDisplay.setText("Pen (Scribble)");
		}

		// eraser button pressed: set the selected tool to '1'
		// and update the status display
		if (e.getSource().equals(eraserButton)) {
			toolType = 1;
			toolDisplay.setText("Eraser");
		}

		// square button pressed: set the selected tool to '2'
		// and update the status display
		if (e.getSource().equals(squareButton)) {
			toolType = 2;
			toolDisplay.setText("Square");
		}

		// oval button pressed: set the selected tool to '3'
		// and update the status display
		if (e.getSource().equals(ovalButton)) {
			toolType = 3;
			toolDisplay.setText("Oval");
		}

		// filled square button pressed: set the selected tool to '4'
		// and update the status display
		if (e.getSource().equals(fillSquareButton)) {
			toolType = 4;
			toolDisplay.setText("Filled Square");
		}

		// filled oval button pressed: set the selected tool to '5'
		// and update the status display
		if (e.getSource().equals(fillOvalButton)) {
			toolType = 5;
			toolDisplay.setText("Filled Oval");
		}

		// clear button pressed: get the size of the applet and draw
		// a clearing rectangle over it, to clear the screen
		if (e.getSource().equals(clearButton)) {
			Dimension size = this.getSize();
			page.clearRect(0, 0, size.width, size.height);
		}

		// line button pressed: set the selected tool to '6'
		// and update the status display
		if (e.getSource().equals(lineButton)) {
			toolType = 6;
			toolDisplay.setText("Line");
		}
	}

	// event handling for when the mouse button is first pressed
	public void mousePressed(MouseEvent e) {
		// store the coordinates for future reference
		oldX = e.getX();
		oldY = e.getY();
	}

	// event handling for when the mouse is dragged with the button
	// held down
	public void mouseDragged(MouseEvent e) {
		Graphics page = this.getGraphics();

		// scribble tool: draw a line from the old coordinates to the
		// new ones
		if (toolType == 0) {
			page.setColor(currentColour);
			page.drawLine(oldX, oldY, e.getX(), e.getY());

			oldX = e.getX();
			oldY = e.getY();
		}

		// eraser tool: clear a small rectangles' worth at the specified
		// coordinate
		if (toolType == 1) {
			page.clearRect(e.getX(), e.getY(), 10, 10);
			page.setColor(currentColour);
		}
	}

	// resolve method: used to swap two numbers around so the
	// smaller is always in element 0, and the larger in element
	// 1, of the returned array
	public int[] resolve(int newC, int oldC) {
		int start, end;

		if (newC < oldC) {
			start = newC;
			end = oldC;
		} else {
			start = oldC;
			end = newC;
		}

		int[] blah = { start, end };
		return blah;
	}

	// event handling for when the mouse button is released
	public void mouseReleased(MouseEvent e) {
		// get the applet's Graphics page
		Graphics page = this.getGraphics();

		// resolve the coordinates so that the smaller of the x and y
		// coordinates can be picked out easily
		int newX = e.getX();
		int newY = e.getY();
		int[] x = resolve(newX, oldX);
		int[] y = resolve(newY, oldY);

		// tool type 2: draw a rectangle
		if (toolType == 2) {
			page.setColor(currentColour);
			page.drawRect(x[0], y[0], (x[1] - x[0]), (y[1] - y[0]));
		}

		// tool type 3: draw an oval
		if (toolType == 3) {
			page.setColor(currentColour);
			page.drawOval(x[0], y[0], (x[1] - x[0]), (y[1] - y[0]));
		}

		// tool type 4: draw a filled rectangle
		if (toolType == 4) {
			page.setColor(currentColour);
			page.fillRect(x[0], y[0], (x[1] - x[0]), (y[1] - y[0]));
		}

		// tool type 5: draw a filled oval
		if (toolType == 5) {
			page.setColor(currentColour);
			page.fillOval(x[0], y[0], (x[1] - x[0]), (y[1] - y[0]));
		}

		// tool type 6: draw the line
		if (toolType == 6) {
			page.setColor(currentColour);
			page.drawLine(oldX, oldY, newX, newY);
		}
	}

	// unused methods from listener interfaces
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}
}
