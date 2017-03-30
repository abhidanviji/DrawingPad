package drawpadtest;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import drawpad.ScribbleCanvas;

public class PaintProgram extends JPanel implements MouseListener, ActionListener,

		MouseMotionListener {

	private static final long serialVersionUID = 1L;
	public static int stroke, eraser = 0;
	private int xX1, yY1, xX2, yY2, choice;
	private static final Color BACKGROUND_COLOR = Color.WHITE;
	private int eraserWidth = 40;
	private int eraserHeight = 40;
	JFrame frame;
	BufferedImage grid;
Graphics2D gc;
	public static void main(String[] args) {
		new PaintProgram();
	}

	PaintProgram() {

		frame = new JFrame("Paint Program");
		canvas = new ScribbleCanvas();
		frame.setSize(1200, 800);

		frame.setBackground(BACKGROUND_COLOR);
		frame.getContentPane().add(this);
		frame.getContentPane().setLayout(new BorderLayout());

		JMenuBar menuBar = new JMenuBar();
		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		JMenu menu;
		JMenuItem mi;
		frame.setJMenuBar(menuBar);
		menu = new JMenu("File");
		menuBar.add(menu);

		mi = new JMenuItem("New");
		menu.add(mi);
		mi.addActionListener(new NewFileListener());

		mi = new JMenuItem("Open");
		menu.add(mi);
		mi.addActionListener(new OpenFileListener());

		mi = new JMenuItem("Save");
		menu.add(mi);
		mi.addActionListener(new SaveFileListener());

		mi = new JMenuItem("Save As");
		menu.add(mi);
		mi.addActionListener(new SaveAsFileListener());

		exitAction = new ExitListener();
		mi = new JMenuItem("Exit");
		menu.add(mi);
		mi.addActionListener(exitAction);

		// option menu
		menu = new JMenu("Option");
		menuBar.add(menu);

		mi = new JMenuItem("Color");
		menu.add(mi);
		// mi.addActionListener(new ColorListener());

		// Help menu
		menu = new JMenu("Help");
		menuBar.add(menu);

		mi = new JMenuItem("About");
		menu.add(mi);
		// mi.addActionListener(new AboutListener());
		/*
		 * JMenu help = new JMenu("Help"); JMenu file = new JMenu("File");
		 * menuBar.add(file); menuBar.add(help); JMenuItem about = new
		 * JMenuItem("About"); JMenuItem newfile = new JMenuItem("New");
		 * help.add(about); file.add(newfile); about.addActionListener(this);
		 * newfile.addActionListener(this);
		 */

		JButton button1 = new JButton("Clear");
		button1.addActionListener(new clearListener());
		JButton color = new JButton("Color");
		color.addActionListener(this);
		JButton erase = new JButton("Erase?");
		erase.addActionListener(this);
		JButton button2 = new JButton("Empty Rect");
		button2.addActionListener(this);
		JButton button3 = new JButton("Filled oval");
		button3.addActionListener(this);
		JButton button4 = new JButton("Filled Rect");
		button4.addActionListener(this);
		JButton button5 = new JButton("Empty oval");
		button5.addActionListener(this);
		JButton button6 = new JButton("Line");
		button6.addActionListener(this);
		JRadioButton thin = new JRadioButton("Thin Line");
		thin.addActionListener(this);
		JRadioButton medium = new JRadioButton("Medium Line");
		medium.addActionListener(this);
		JRadioButton thick = new JRadioButton("Thick Line");
		thick.addActionListener(this);

		ButtonGroup lineOption = new ButtonGroup();
		lineOption.add(thin);
		lineOption.add(medium);
		lineOption.add(thick);

		toolBar.add(button1);
		toolBar.add(color);
		toolBar.add(erase);
		toolBar.add(button2);
		toolBar.add(button3);
		toolBar.add(button4);
		toolBar.add(button5);
		toolBar.add(button6);
		toolBar.add(thin);
		toolBar.add(medium);
		toolBar.add(thick);
		addMouseListener(this);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (grid == null) {
			int w = this.getWidth();
			int h = this.getHeight();
			grid = (BufferedImage) (this.createImage(w, h));
			gc = grid.createGraphics();
			gc.setColor(Color.BLUE);
		}

		g2.drawImage(grid, null, 0, 0);
		check();
	}

	public void draw() {
		Graphics2D g = (Graphics2D) getGraphics();
		int w = xX2 - xX1;
		if (w < 0)
			w = w * (-1);

		int h = yY2 - yY1;
		if (h < 0)
			h = h * (-1);

		switch (choice) {
		case 1:
			check();
			gc.drawRect(xX1, yY1, w, h);
			repaint();
			break;

		case 2:
			check();
			gc.drawOval(xX1, yY1, w, h);
			repaint();
			break;

		case 3:
			check();
			gc.drawRect(xX1, yY1, w, h);
			gc.fillRect(xX1, yY1, w, h);
			repaint();
			break;

		case 4:
			check();
			gc.drawOval(xX1, yY1, w, h);
			gc.fillOval(xX1, yY1, w, h);
			repaint();
			break;

		case 5:

			if (stroke == 0)
				gc.setStroke(new BasicStroke(1));
			if (stroke == 1)
				gc.setStroke(new BasicStroke(3));
			if (stroke == 2)
				gc.setStroke(new BasicStroke(6));
			gc.drawLine(xX1, yY1, xX2, yY2);
			repaint();
			break;

		case 6:
			repaint();
			Color temp = gc.getColor();

			gc.setColor(BACKGROUND_COLOR);
			gc.fillRect(0, 0, getWidth(), getHeight());
			gc.setColor(temp);
			repaint();
			break;

		case 7:

			if (eraser == 1) {
				gc.clearRect(xX1, yY1, w, h);
			} else {

			}
			break;
		}
	}

	public void check() {
		if (xX1 > xX2) {
			int z = 0;
			z = xX1;
			xX1 = xX2;
			xX2 = z;
		}
		if (yY1 > yY2) {
			int z = 0;
			z = yY1;
			yY1 = yY2;
			yY2 = z;
		}
	}

	public void actionPerformed(ActionEvent e) {
		/**
		 * CHANGE BY S AQEEL : Remove mousemotionlistener(which is added when
		 * eraser is selected) So that if another control is pressed, the user
		 * does not accidentally erases
		 */
		super.removeMouseMotionListener(this);

		if (e.getActionCommand().equals("Color")) {
			Color bgColor = JColorChooser.showDialog(this, "Choose Background Color", getBackground());
			if (bgColor != null)
				gc.setColor(bgColor);
		}

		if (e.getActionCommand().equals("About")) {
			System.out.println("About Has Been Pressed");
			JFrame about = new JFrame("About");
			about.setSize(300, 300);
			JTextField abt = new JTextField();
			about.add(abt);
			abt.setText("This is the first version of the Drawing Pad Program");
			about.setVisible(true);
		}

		if (e.getActionCommand().equals("New")) {
			System.out.println("New File Has Been Pressed");
			JFrame newfile = new JFrame("New File");
			newfile.setSize(300, 300);
			JTextField file = new JTextField();
			newfile.add(file);
			file.setText("Choose");
			newfile.setVisible(true);
		}

		if (e.getActionCommand().equals("Empty Rect")) {
			System.out.println("Empty Rectangle Has Been Selected~");
			choice = 1;

		}

		if (e.getActionCommand().equals("Empty oval")) {
			System.out.println("Empty Oval Has Been Selected!");
			choice = 2;
		}

		if (e.getActionCommand().equals("Filled Rect")) {
			System.out.println("Filled Rectangle Has Been Selected");
			choice = 3;
		}

		if (e.getActionCommand().equals("Filled oval")) {
			System.out.println("Filled Oval Has Been Selected");
			choice = 4;
		}

		if (e.getActionCommand().equals("Line")) {
			System.out.println("Draw Line Has Been Selected");
			choice = 5;
		}

		if (e.getActionCommand().equals("Thin Line")) {
			stroke = 0;
		}

		if (e.getActionCommand().equals("Medium Line")) {
			stroke = 1;
		}

		if (e.getActionCommand().equals("Thick Line")) {
			stroke = 2;
		}

		if (e.getActionCommand().equals("Erase?")) {
			eraser = 1;
			choice = 7;

			super.addMouseMotionListener(this);
		}

		if (e.getActionCommand().equals("Clear")) {
			System.out.println("Clear All The Things!!!");
			choice = 6;
			draw();
		}

	}

	public void mouseExited(MouseEvent evt) {
	}

	public void mouseEntered(MouseEvent evt) {
	}

	public void mouseClicked(MouseEvent evt) {
	}

	public void mousePressed(MouseEvent evt) {

		xX1 = evt.getX();
		yY1 = evt.getY();

	}

	public void mouseReleased(MouseEvent evt) {
		xX2 = evt.getX();
		yY2 = evt.getY();
		draw();
		eraser = 0;
	}

	/**
	 * CHANGE BY S AQEEL : MouseMotionListener functions implemented. Note this
	 * listener is only registered when eraser is selected
	 */
	public void mouseDragged(MouseEvent me) {
		Color c = gc.getColor();
		gc.setColor(BACKGROUND_COLOR);
		gc.drawRect(me.getX(), me.getY(), eraserWidth, eraserHeight);
		gc.fillRect(me.getX(), me.getY(), eraserWidth, eraserHeight);
		gc.setColor(c);
		repaint();
	}

	public void mouseMoved(MouseEvent arg0) {
	}

	protected void newFile() {
		currentFilename = null;
		canvas.newFile();

	}

	protected void saveFile() {
		if (currentFilename == null) {
			currentFilename = "Abhi";
		}
		/*
		 * try { BufferedImage image = new BufferedImage(getWidth(),
		 * getHeight(), BufferedImage.TYPE_INT_RGB); Graphics2D graphics2D =
		 * image.createGraphics(); frame.paint(graphics2D);
		 * ImageIO.write(image,"jpeg", new File(
		 * "C:\\Users\\Abhi\\workspace\\DrawingPad\\src\\drawpad\\AbhiSave.jpeg"
		 * )); } catch(Exception exception) { //code }
		 */
		canvas.saveFile(currentFilename);
	}

	protected void saveFileAs(String filename) {
		currentFilename = filename;
		canvas.saveFile(filename);

	}

	protected void openFile(String filename) {
		currentFilename = filename;
		canvas.openFile(filename);

	}

	class NewFileListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			newFile();
		}

	}

	class OpenFileListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int retval = chooser.showDialog(null, "Open");
			if (retval == JFileChooser.APPROVE_OPTION) {
				File theFile = chooser.getSelectedFile();
				if (theFile != null) {
					if (theFile.isFile()) {
						String filename = chooser.getSelectedFile().getAbsolutePath();
						openFile(filename);
					}
				}
			}
		}

	}

	class SaveFileListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			saveFile();
		}

	}

	class SaveAsFileListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int retval = chooser.showDialog(null, "Save As");
			if (retval == JFileChooser.APPROVE_OPTION) {
				File theFile = chooser.getSelectedFile();
				if (theFile != null) {
					if (!theFile.isDirectory()) {
						String filename = chooser.getSelectedFile().getAbsolutePath();
						saveFileAs(filename);
					}
				}
			}
		}

	}

	class ExitListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(null, "Do you want to Save your work before Exit?",
					"Save Dialog Box", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				saveFile();
				System.exit(0);
			}
		}

	}

	class clearListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			gc.setColor(Color.WHITE);
			gc.fillRect(0, 0, getWidth(), getHeight());
		}

	}

	protected String currentFilename = null;
	protected ScribbleCanvas canvas;
	protected ActionListener exitAction;
	protected JFileChooser chooser = new JFileChooser(".");

}