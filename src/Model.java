package src;

import java.awt.geom.*;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.*;
//import javax.swing.JPanel;

public class Model extends JPanel {
    public static int CurrentState = 0; // 0选中，1画线，2画圆,3文本
    public static int choosingIndex = -1;
    public static Point startP, endP;
    // public static ArrayList<Shape> items = new ArrayList<Shape>();
    public static ArrayList<itemsMesg> itemsM = new ArrayList<itemsMesg>();

    Model() {

    }

}

class itemsMesg {
    protected Color color;
    protected int size;
    protected boolean chosen;
    protected int state;
    protected Point p1, p2;
    protected Shape item;

    itemsMesg(int state, Shape r) {
        color = Color.BLACK;
        size = 10;
        chosen = false;
        this.state = state;
        item = r;
        p1 = Model.startP;
        p2 = Model.endP;
    }

    void setPoint(Point s, Point e) {
        this.p1 = s;
        this.p2 = e;
    }

    Point getStartP() {
        return p1;
    }

    Point getEndP() {
        return p2;
    }

    void setItem(Shape r) {
        item = r;
    }

    int getState() {
        return this.state;
    }

    Color getColor() {
        return color;
    }

    int getSize() {
        return size;
    }

    Shape getItem() {
        return item;
    }

    boolean ifContain() {
        System.out.println("Not" + p1.getX());
        return item.contains(Model.startP.x, Model.startP.y);
    }

    int kCal() {

    }
}
