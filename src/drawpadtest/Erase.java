package drawpadtest;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
 
public class Erase extends JPanel {
    List<Shape> list = new ArrayList<Shape>();
    int selectedIndex = -1;
 
    public Erase() {
        list.add(new Rectangle(25, 25, 75, 40));
        list.add(new Rectangle(200, 50, 75, 100));
        list.add(new Rectangle(90, 175, 40, 75));
        list.add(new Rectangle(300, 240, 40, 90));
        addMouseListener(selector);
    }
 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        for(int i = 0; i < list.size(); i++) {
            Color color = (i == selectedIndex) ? Color.red : Color.blue;
            g2.setPaint(color);
            g2.draw(list.get(i));
        }
    }
 
    public Dimension getPreferredSize() {
        return new Dimension(400,400);
    }
 
    private JPanel getErasePanel() {
        JButton button = new JButton("erase selected shape");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(selectedIndex != -1) {
                    list.remove(selectedIndex);
                    selectedIndex = -1;
                    repaint();
                }
            }
        });
        JPanel panel = new JPanel();
        panel.add(button);
        return panel;
    }
 
    public static void main(String[] args) {
        Erase test = new Erase();
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(test);
        f.add(test.getErasePanel(), "Last");
        f.pack();
        f.setLocation(200,200);
        f.setVisible(true);
    }
 
    private MouseListener selector = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            Point p = e.getPoint();
            for(int i = 0; i < list.size(); i++) {
                if(list.get(i).contains(p)) {
                    if(i == selectedIndex) {
                        selectedIndex = -1;
                    } else {
                        selectedIndex = i;
                    }
                    repaint();
                    break;
                }
            }
        }
    };
}
