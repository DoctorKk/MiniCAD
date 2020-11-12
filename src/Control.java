package src;

import java.util.ArrayList;
import java.util.List;
import src.Model;
import javax.swing.JPanel;
import java.awt.event.*;

public class Control extends JPanel {
    List<Line> Lines = new ArrayList<>();
    // List<Circle> Circles = new ArrayList<>();
    boolean IsinBoard = false, Drawing = false;
    int CurX, CurY, StartX, StartY, EndX, EndY;

    Control(JPanel p) {
        p.addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
                IsinBoard = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                IsinBoard = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // 获取按下的坐标（相对于组件）
                // e.getPoint();
                CurX = e.getX();
                CurY = e.getY();
                if (Model.CurrentState > 0) {
                    StartX = CurX;
                    StartY = CurY;
                    Drawing = true;

                    if (Model.CurrentState == 1) {
                        Line temp = new Line();
                        temp.setXStart(StartX).setYStart(StartY).setXEnd(CurX).setYEnd(CurY);
                        Lines.add(temp);
                    }
                } else {

                }
                // 获取按下的坐标（相对于屏幕）
                e.getLocationOnScreen();
                e.getXOnScreen();
                e.getYOnScreen();

                // 判断按下的是否是鼠标右键
                e.isMetaDown();

                // System.out.println("鼠标按下");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                CurX = e.getX();
                CurY = e.getY();
                if (Model.CurrentState > 0) {
                    EndX = CurX;
                    EndX = CurY;
                    Drawing = false;
                    Model.CurrentState = 0;
                }
                // System.out.println("鼠标释放");
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // 鼠标在组件区域内按下并释放（中间没有移动光标）才识别为被点击
                System.out.println("鼠标点击");
            }
        });
    }

}
