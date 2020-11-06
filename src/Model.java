package src;

import javax.swing.*;
import java.awt.*;
//import javax.swing.JPanel;

public class Model extends JPanel{
    
}


class Shape extends JPanel{
    private int color;
    private int size;

    Shape(){}

    void render(Graphics g){}

    
}

class Line extends Shape{
    private int xStart, yStart;
    private int xEnd, yEnd;

    Line(){}

    Line setXStart(int x){
        this.xStart = x;
        return this;
    }
    Line setYStart(int y){
        this.yStart = y;
        return this;
    }
    Line setXEnd(int x){
        this.xEnd = x;
        return this;
    }
    Line setYEnd(int y){
        this.yEnd = y;
        return this;
    }

    
    @Override
    void render(Graphics g){

    }
}