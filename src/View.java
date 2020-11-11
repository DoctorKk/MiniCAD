package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import src.Model;

public class View extends JPanel {
    View() {

    }
}

class MyFrame extends JFrame {
    private final String TITLE = "MiniCAD";
    private final int HEIGHT = 1080;
    private final int WIDTH = 960;

    MyFrame() {
        super();
        init();
    }

    private void init() {
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        MyPanel panel = new MyPanel(this);
        setContentPane(panel);
    }
}

class MyPanel extends JPanel {
    private MyFrame frame;

    MyPanel(MyFrame frame) {
        super(new BorderLayout());
        this.frame = frame;
    }

    private void init() {
        Frame Left = new Frame();

        add(Left, BorderLayout.EAST);
    }

}

class Frame extends JPanel {
    Frame() {
        super();
    }

    private void init() {
        final JButton btnClick = new JButton("CLICK");
        btnClick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.CurrentState = 0;
            }
        });

        final JButton btnDrawLine = new JButton("Draw Lines");
        btnDrawLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.CurrentState = 1;
            }
        });

        final JButton btnDrawCircle = new JButton("Draw Circles");
        btnDrawCircle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.CurrentState = 2;
            }
        });

        final JButton btnText = new JButton("TEXT");
        btnText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Model.CurrentState = 3;
            }
        });

        final JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Model.CurrentState = 3;
            }
        });

        final JButton btnLoad = new JButton("Load");
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Model.CurrentState = 3;
            }
        });

        Box vBox = Box.createHorizontalBox();
        vBox.add(btnClick);
        vBox.add(btnDrawLine);
        vBox.add(btnDrawCircle);
        vBox.add(btnText);
        vBox.add(btnSave);
        vBox.add(btnLoad);
        add(vBox);
    }

}

class DrawBoard extends JPanel {

}
