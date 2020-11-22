package src;

import javax.swing.JComponent;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.*;

public class Control {
    Control() {
    }

    // Mouse event
    public static void addControl(JComponent p) {
        p.addMouseListener(new MouseAdapter() {
            // mouse-pressing event
            public void mousePressed(MouseEvent e) {
                Model.startP = new Point(e.getX(), e.getY());
                if (Model.CurrentState == 0) {
                    int i = 0;
                    for (itemsMesg t : Model.itemsM) {
                        if (t.ifContain()) {
                            t.chosen = true;
                            Model.choosingIndex = i;
                            break;
                        }
                        i++;
                    }
                }
                p.repaint();
            }

            // mouse-releasing event
            public void mouseReleased(MouseEvent e) {
                if (Model.CurrentState == 4) {
                    Model.itemsM.add(new itemsMesg(Model.CurrentState, Model.curText));

                } else if (Model.CurrentState != 0) {
                    Shape temp = null;

                    temp = createShape(Model.startP, new Point(e.getX(), e.getY()), Model.CurrentState);
                    Model.itemsM.add(new itemsMesg(Model.CurrentState, temp));

                } else {
                    if (Model.choosingIndex >= 0) {
                        itemsMesg t = Model.itemsM.get(Model.choosingIndex);
                        if (t.getState() == 4) {
                            t.setPoint(e.getPoint(), null);
                        } else {
                            t.p1 = new Point((int) (t.getStartP().getX() + (e.getX() - Model.startP.getX())),
                                    (int) (t.getStartP().getY() + (e.getY() - Model.startP.getY())));
                            t.p2 = new Point((int) (t.getEndP().getX() + (e.getX() - Model.startP.getX())),
                                    (int) (t.getEndP().getY() + (e.getY() - Model.startP.getY())));
                        }
                    }
                }
                Model.startP = null;
                Model.endP = null;
                p.repaint();
            }
        });

        // mouse-dragging event
        p.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Model.endP = new Point(e.getX(), e.getY());
                if (Model.CurrentState == 0) {
                    if (Model.choosingIndex >= 0) {
                        itemsMesg t = Model.itemsM.get(Model.choosingIndex);
                        if (t.getState() == 4) {
                            t.setPoint(Model.endP, null);
                        } else {
                            Shape temp = null;

                            temp = createShape(
                                    new Point((int) (t.getStartP().getX() + (e.getX() - Model.startP.getX())),
                                            (int) (t.getStartP().getY() + (e.getY() - Model.startP.getY()))),
                                    new Point((int) (t.getEndP().getX() + (e.getX() - Model.startP.getX())),
                                            (int) (t.getEndP().getY() + (e.getY() - Model.startP.getY()))),
                                    t.getState());
                            t.setItem(temp);
                        }

                    }
                }
                p.repaint();
            }
        });

    }

    // return the shape you want to create
    static Shape createShape(Point x1, Point x2, int state) {
        Shape temp = null;

        switch (state) {
            case 1:
                temp = new Line2D.Float(x1, x2);
                break;
            case 2:
                temp = new Ellipse2D.Float(x1.x, x1.y, Math.abs(x2.x - x1.x), Math.abs(x2.y - x1.y));
                break;
            case 3:
                temp = new Rectangle2D.Float(Math.min(x1.x, x2.x), Math.min(x1.y, x2.y), Math.abs(x2.x - x1.x),
                        Math.abs(x2.y - x1.y));
                break;
        }

        return temp;
    }

}
