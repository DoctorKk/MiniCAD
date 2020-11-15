package src;

import java.util.ArrayList;
import java.util.List;
import src.Model;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.*;
import java.awt.geom.*;
import java.awt.*;

public class Control {
    Control() {
    }

    public static void addControl(JComponent p) {
        p.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Model.startP = new Point(e.getX(), e.getY());
                if (Model.CurrentState == 0) {
                    int i = 0;
                    System.out.println(Model.itemsM.size());
                    for (itemsMesg t : Model.itemsM) {
                        if (t.ifContain()) {
                            t.chosen = true;
                            Model.choosingIndex = i;
                        }
                        i++;
                    }
                }
                p.repaint();
            }

            public void mouseReleased(MouseEvent e) {
                if (Model.CurrentState != 0) {
                    Shape temp = null;

                    temp = createShape(Model.startP, new Point(e.getX(), e.getY()), Model.CurrentState);
                    Model.itemsM.add(new itemsMesg(Model.CurrentState, temp));
                    Model.startP = null;
                    Model.endP = null;
                } else {
                    if (Model.choosingIndex >= 0) {
                        itemsMesg t = Model.itemsM.get(Model.choosingIndex);
                        t.p1 = new Point((int) (t.getStartP().getX() + (e.getX() - Model.startP.getX())),
                                (int) (t.getStartP().getY() + (e.getY() - Model.startP.getY())));
                        t.p2 = new Point((int) (t.getEndP().getX() + (e.getX() - Model.startP.getX())),
                                (int) (t.getEndP().getY() + (e.getY() - Model.startP.getY())));
                        Model.choosingIndex = -1;
                    }
                }
                p.repaint();
            }
        });

        p.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Model.endP = new Point(e.getX(), e.getY());
                if (Model.CurrentState == 0) {
                    System.out.println(Model.choosingIndex);
                    if (Model.choosingIndex >= 0) {
                        Shape temp = null;
                        itemsMesg t = Model.itemsM.get(Model.choosingIndex);
                        temp = createShape(
                                new Point((int) (t.getStartP().getX() + (e.getX() - Model.startP.getX())),
                                        (int) (t.getStartP().getY() + (e.getY() - Model.startP.getY()))),
                                new Point((int) (t.getEndP().getX() + (e.getX() - Model.startP.getX())),
                                        (int) (t.getEndP().getY() + (e.getY() - Model.startP.getY()))),
                                t.getState());
                        t.setItem(temp);
                    }
                }
                p.repaint();
            }
        });
    }

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
