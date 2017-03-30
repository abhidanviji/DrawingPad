package drawpad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;

public class NewDraw extends DrawingPad2 {
	protected Vector pointVector = new Vector();
	protected Paint paintcan = new Paint(pointVector);
	protected UndoManager undoManager = new UndoManager();
	Image img;
	protected JFileChooser chooser = new JFileChooser(".");

	public NewDraw(String title) {
		super(title);
		JButton undoButton = new JButton("Undo");
		JButton redoButton = new JButton("Redo");
		undoButton.setEnabled(false);
		redoButton.setEnabled(false);
		JToolBar tools = new JToolBar();
		tools.add(undoButton);
		tools.add(redoButton);

		paintcan.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Point point = new Point(e.getX(), e.getY());
				pointVector.addElement(point);

				undoManager.undoableEditHappened(
						new UndoableEditEvent(paintcan, new UndoablePaintSquare(point, pointVector)));

				undoButton.setText(undoManager.getUndoPresentationName());
				redoButton.setText(undoManager.getRedoPresentationName());
				undoButton.setEnabled(undoManager.canUndo());
				redoButton.setEnabled(undoManager.canRedo());
				paintcan.repaint();
			}
		});

		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					undoManager.undo();
				} catch (CannotRedoException cre) {
					cre.printStackTrace();
				}
				paintcan.repaint();
				undoButton.setEnabled(undoManager.canUndo());
				redoButton.setEnabled(undoManager.canRedo());
			}
		});

		redoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					undoManager.redo();
				} catch (CannotRedoException cre) {
					cre.printStackTrace();
				}
				paintcan.repaint();
				undoButton.setEnabled(undoManager.canUndo());
				redoButton.setEnabled(undoManager.canRedo());
			}
		});

		paintcan.setPreferredSize(new Dimension(200, 200));
		getContentPane().add(tools, BorderLayout.SOUTH);
		getContentPane().add(paintcan, BorderLayout.EAST);
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
		toolkit.addTool(new TwoEndsShapeTool(canvas, "Eraser Rect", new Eraser3()));
		toolkit.addTool(new TwoEndsShapeTool(canvas, "Background", new Background()));
		toolkit.addTool(new TwoEndsShapeTool(canvas, "BackgroundColour", new BackgroundColour(canvas)));
	}

	public static void main(String[] args) {
		JFrame frame = new NewDraw("Abhi's Drawing Pad");
		frame.setSize(width, height);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2);
		frame.show();
	}

	class UndoablePaintSquare extends AbstractUndoableEdit {
		protected Vector points;

		protected Point point;

		public UndoablePaintSquare(Point p, Vector v) {
			points = v;
			point = p;
		}

		public String getPresentationName() {
			return "Square Addition";
		}

		public void undo() {
			super.undo();
			points.remove(point);
		}

		public void redo() {
			super.redo();
			points.add(point);
		}

	}

	class Paint extends JPanel {
		private Vector points;

		protected int width = 50;

		protected int height = 50;

		public Paint(Vector v) {
			super();
			points = v;
			setOpaque(true);
			setBackground(Color.white);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.black);

			for (int i = 0; i < points.size(); i++) {
				Point point = (Point) points.get(i);
				g.drawRect(point.x, point.y, width, height);
			}
		}
	}

}
