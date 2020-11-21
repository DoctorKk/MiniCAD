package src;

import java.awt.geom.*;
import java.util.ArrayList;
import java.io.*;
import javax.swing.*;

import java.awt.*;
//import javax.swing.JPanel;

public class Model extends JPanel {
    public static int CurrentState = 0; // 0选中，1画线，2画圆,3矩形，4文本
    public static int choosingIndex = -1;
    public static Point startP, endP;
    public static String curText;
    public static Color curColor;
    // public static ArrayList<Shape> items = new ArrayList<Shape>();
    public static ArrayList<itemsMesg> itemsM = new ArrayList<itemsMesg>();

    Model() {

    }

    public static void serializeItem(File f) throws FileNotFoundException, IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(itemsM);
        oos.close();
    }

    public static void deserializeItem(File file) throws FileNotFoundException, IOException {
        // file = new File("D:\\test");
        // System.out.println(file.getAbsolutePath());
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        try {
            itemsM = (ArrayList<itemsMesg>) ois.readObject();
        } catch (Exception e) {
            System.out.println("failed");
        }
        ois.close();
    }

}

class itemsMesg implements Serializable {
    protected Color color;
    protected int size;
    protected boolean chosen;
    protected int state;
    protected Point p1, p2;
    protected Shape item;
    protected String text;

    itemsMesg(int state, Shape r) {
        color = Color.BLACK;
        size = 10;
        chosen = false;
        this.state = state;
        item = r;
        p1 = Model.startP;
        p2 = Model.endP;
    }

    itemsMesg(int state, String t) {
        this.state = state;
        this.text = t;
        color = Color.BLACK;
        size = 25;
        chosen = false;
        item = null;
        p1 = Model.endP;
        p2 = null;
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

    void setColor(Color c) {
        color = c;
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
        // System.out.println("Not" + p1.getX());
        if (this.state == 4) {
            if (Model.startP.x >= p1.getX() && Model.startP.x <= (p1.getX() + text.length() * size)
                    && Model.startP.y >= p1.getY() - size && Model.startP.y <= p1.getY()) {
                System.out.println("yes");
                return true;
            }
            System.out.println("no");
            return false;
        } else if (this.state == 1)
            return kCal();
        return item.contains(Model.startP.x, Model.startP.y);
    }

    boolean kCal() {
        double dis;
        if (Math.abs(p1.x - p2.x) < 1) {
            System.out.println("chuizhi  " + Math.abs(Model.startP.x - p1.x));
            dis = Math.abs(Model.startP.x - p1.x);
            return dis < 1;
        }
        double k = (double) (p1.y - p2.y) / (double) (p1.x - p2.x);
        double b = (double) p1.y - k * (double) p1.x;
        dis = Math.abs(k * Model.startP.x + b - Model.startP.y) / Math.sqrt(k * k + 1);
        System.out.println(dis);
        return dis < size;
    }
}
