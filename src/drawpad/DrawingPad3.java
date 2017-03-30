package drawpad;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class DrawingPad3 extends DrawingPad2 {

	public DrawingPad3(String title) {
		super(title);
		/*initTools1();
		ActionListener toolListener = new ActionListener() { 
			public void actionPerformed(ActionEvent event) { 
			  Object source = event.getSource();
			  if (source instanceof AbstractButton) {
			    AbstractButton button = (AbstractButton) source; 
			    Tool tool = toolkit1.setSelectedTool(button.getText());
			    drawingCanvas.setTool(tool);
			  }
			}
		      };
		    JComponent toolbar = createToolBar(toolListener); 
		    getContentPane().add(toolbar, BorderLayout.EAST);
		    //JMenu menu = createToolMenu(toolListener); 
		    menuBar.add(addToolMenu(menuBar.getMenu(1),toolListener), 1);*/
		    
	}
	
	

	protected void initTools1() {
		toolkit1 = new ToolKit();
		toolkit1.addTool(new TwoEndsShapeTool(canvas, "RoundRect", new RoundedRectangleShape()));
		toolkit1.addTool(new TwoEndsShapeTool(canvas, "FilledRoundRect", new FilledRoundedRectangleShape()));
		drawingCanvas.setTool(toolkit1.getTool(1));
	}
	
	protected JMenu addToolMenu(JMenu menu,ActionListener toolListener) { 
	    //JMenu menu = new JMenu("More Tools"); 
	    int n = toolkit1.getToolCount(); 
	    for (int i = 0; i < n; i++) {
	      Tool tool = toolkit1.getTool(i); 
	      if (tool != null) { 
		JMenuItem menuitem = new JMenuItem(tool.getName());
		menuitem.addActionListener(toolListener); 
		menu.add(menuitem);
	      }
	    }
	    return menu; 
	  }
	protected ToolKit toolkit1;

	public static void main(String[] args) {
		JFrame frame = new DrawingPad3("Abhi's Drawing Pad");
		frame.setSize(width, height);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2);
		frame.show();
	}

}
