package src;

import java.util.ArrayList;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class Model extends JPanel {
    // State，0 means you can select, 1 draw line, 2 draw ellipse, 3 draw rectangle,
    // 4 insert text
    public static int CurrentState = 0;

    // The index of the shape you choose, if there's no shape chosen, it will be -1
    public static int choosingIndex = -1;

    // startP:the point you pressed Mouse, endP:the point you release Mouse
    public static Point startP, endP;

    // the text you just input
    public static String curText;

    // the color you just select
    public static Color curColor;

    // storing all the objects to be displayed
    public static ArrayList<itemsMesg> itemsM = new ArrayList<itemsMesg>();

    Model() {
    }

    // serialize
    public static void serializeItem(File f) throws FileNotFoundException, IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(itemsM);
        oos.close();
    }

    // deserialize
    public static void deserializeItem(File file) throws FileNotFoundException, IOException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        try {
            itemsM = (ArrayList<itemsMesg>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Failed while loading a file");
        }
        ois.close();
    }

}

class itemsMesg implements Serializable {
    protected Color color; // colro
    protected int size; // size
    protected boolean chosen; // whether this object is selected
    protected int state; // 1:line 2:ellipse 3:rectangle 4:text
    protected Point p1, p2;// p1:start point, p2:end point
    protected Shape item;// the shape to be displayed
    protected String text; // the text to be displayed

    // Initialize the object whoose state is not 4
    itemsMesg(int state, Shape r) {
        color = Color.BLACK;
        size = 10;
        chosen = false;
        item = r;
        p1 = Model.startP;
        p2 = Model.endP;
        this.state = state;
    }

    // Initialize the object whoose state is 4
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

    // set p1,p2
    void setPoint(Point s, Point e) {
        this.p1 = s;
        this.p2 = e;
    }

    // return start point
    Point getStartP() {
        return p1;
    }

    // return end point
    Point getEndP() {
        return p2;
    }

    // set the shape
    void setItem(Shape r) {
        item = r;
    }

    // set the color
    void setColor(Color c) {
        color = c;
    }

    // return state
    int getState() {
        return this.state;
    }

    // return color
    Color getColor() {
        return color;
    }

    // return size
    int getSize() {
        return size;
    }

    // return shape
    Shape getItem() {
        return item;
    }

    // Judge whether the click point can select this object
    boolean ifContain() {
        if (this.state == 4) {
            if (Model.startP.x >= p1.getX() && Model.startP.x <= (p1.getX() + text.length() * size)
                    && Model.startP.y >= p1.getY() - size && Model.startP.y <= p1.getY()) {
                System.out.println("yes");
                return true;
            }
            return false;
        } else if (this.state == 1)
            return kCal();
        return item.contains(Model.startP.x, Model.startP.y);
    }

    // Judge whether the click point can select this object(Line)
    boolean kCal() {
        double dis;
        if (Model.startP.x < Math.min(p1.x, p2.x) - size || Model.startP.y < Math.min(p1.y, p2.y) - size
                || Model.startP.x > Math.max(p1.x, p2.x) + size || Model.startP.y > Math.max(p1.y, p2.y) + size)
            return false;
        if (Math.abs(p1.x - p2.x) < 1) {
            dis = Math.abs(Model.startP.x - p1.x);
            return dis < size;
        }
        double k = (double) (p1.y - p2.y) / (double) (p1.x - p2.x);
        double b = (double) p1.y - k * (double) p1.x;
        dis = Math.abs(k * Model.startP.x + b - Model.startP.y) / Math.sqrt(k * k + 1);
        return dis < size;
    }
}
