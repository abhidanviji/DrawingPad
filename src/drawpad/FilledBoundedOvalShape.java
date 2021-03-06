package drawpad;

import java.awt.*; 

public class FilledBoundedOvalShape extends BoundedOvalShape {

  public void draw(Graphics g) {
    int x = Math.min(x1, x2); 
    int y = Math.min(y1, y2); 
    int w = Math.abs(x1 - x2) + 1; 
    int h = Math.abs(y1 - y2) + 1;     
    if (color != null) {
      g.setColor(color);
    }
    g.fillOval(x, y, w, h);
    g.drawRect(x, y, w, h);
  }

}
