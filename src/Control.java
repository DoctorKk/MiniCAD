package src;

import javax.swing.JComponent;
import javax.swing.JFrame;
//import javax.swing.JOptionPane;

import java.awt.event.*;
import java.awt.geom.*;
import java.awt.*;

public class Control {
    Control() {

    }

    public static void addControl(JComponent p) {
        p.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getY());
                Model.startP = new Point(e.getX(), e.getY());
                if (Model.CurrentState == 0) {
                    int i = 0;
                    // System.out.println(Model.itemsM.size());
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

                        // Model.choosingIndex = -1;
                    }
                }
                Model.startP = null;
                Model.endP = null;
                p.repaint();
            }
        });

        p.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Model.endP = new Point(e.getX(), e.getY());
                if (Model.CurrentState == 0) {
                    // System.out.println(Model.choosingIndex);
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

        p.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                System.out.println("DOWN");
                // 获取键值，和 KeyEvent.VK_XXXX 常量比较确定所按下的按键
                int keyCode = e.getKeyCode();
                if (Model.choosingIndex >= 0) {
                    itemsMesg t = Model.itemsM.get(Model.choosingIndex);
                    switch (keyCode) {
                        case KeyEvent.VK_Q:
                            if (t.size >= 2)
                                t.size--;
                            break;
                        case KeyEvent.VK_E:
                            t.size++;
                            break;
                        default:
                            break;
                    }
                }
                p.repaint();
                System.out.println("按下: " + e.getKeyCode());
            }
        });

    }

    public static void addKeyControl(JFrame p) {
        // Control x = new Control();
        p.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                System.out.println("DOWN");
                // 获取键值，和 KeyEvent.VK_XXXX 常量比较确定所按下的按键
                int keyCode = e.getKeyCode();
                if (Model.choosingIndex >= 0) {
                    itemsMesg t = Model.itemsM.get(Model.choosingIndex);
                    switch (keyCode) {
                        case KeyEvent.VK_Q:
                            if (t.size >= 2)
                                t.size--;
                            break;
                        case KeyEvent.VK_E:
                            t.size++;
                            break;
                    }
                }
                // p.repaint();
                System.out.println("按下: " + e.getKeyCode());
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
