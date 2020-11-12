package src;

import javax.swing.*;
import java.awt.*;
//import javax.swing.JPanel;

public class Model extends JPanel {
    public static int CurrentState = 0; // 0选中，1画线，2画圆,3文本

    Model() {

    }

}

class Shape extends JPanel {
    private Color color = Color.BLACK;
    private int size;
    private boolean chosen;

    protected int xStart, yStart;
    protected int xEnd, yEnd;

    Shape() {
    }

    Shape setXStart(int x) {
        this.xStart = x;
        return this;
    }

    Shape setYStart(int y) {
        this.yStart = y;
        return this;
    }

    Shape setXEnd(int x) {
        this.xEnd = x;
        return this;
    }

    Shape setYEnd(int y) {
        this.yEnd = y;
        return this;
    }

    void render(Graphics g) {
    }

    boolean ifChosen(int x, int y) {
        return false;
    }

}

class Line extends Shape {

    Line() {
    }

    @Override
    void render(Graphics g) {

    }
}