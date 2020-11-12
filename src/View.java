package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import src.Model;

public class View {
    public View() {
        MyFrame frame = new MyFrame();
        frame.setVisible(true);
    }
}

class MyFrame extends JFrame {
    private final String TITLE = "MiniCAD";
    private final int HEIGHT = 900;
    private final int WIDTH = 1600;

    MyFrame() {
        super();
        init();
    }

    private void init() {
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        MyPanel panel = new MyPanel();
        setContentPane(panel);
    }
}

class MyPanel extends JPanel {
    // private MyFrame frame;

    MyPanel() {
        super(new BorderLayout());
        init();
        // this.frame = frame;
    }

    private void init() {
        Frame Left = new Frame();

        add(Left, BorderLayout.WEST);
    }

}

class Frame extends JPanel {
    Frame() {
        super();
        init();
    }

    private void init() {
        final JButton btnClick = new JButton();
        btnClick.setIcon(new ImageIcon("./img/Click.png"));
        btnClick.setBorderPainted(false);
        btnClick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.CurrentState = 0;
            }
        });

        final JButton btnDrawLine = new JButton();
        btnDrawLine.setIcon(new ImageIcon("./img/Line.png"));
        btnDrawLine.setBorderPainted(false);
        btnDrawLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.CurrentState = 1;
            }
        });

        final JButton btnDrawCircle = new JButton();
        btnDrawCircle.setIcon(new ImageIcon("./img/Circle.png"));
        btnDrawCircle.setBorderPainted(false);
        btnDrawCircle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.CurrentState = 2;
            }
        });

        final JButton btnText = new JButton();
        btnText.setIcon(new ImageIcon("./img/Text.png"));
        btnText.setBorderPainted(false);
        btnText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Model.CurrentState = 3;
            }
        });

        final JButton btnSave = new JButton();
        btnSave.setIcon(new ImageIcon("./img/Save.png"));
        btnSave.setBorderPainted(false);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Model.CurrentState = 3;
            }
        });

        final JButton btnLoad = new JButton();
        btnLoad.setIcon(new ImageIcon("./img/Load.png"));
        btnLoad.setBorderPainted(false);
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Model.CurrentState = 3;
            }
        });

        final JButton btnBlank = new JButton();
        btnBlank.setBorderPainted(false);

        Box vBox = Box.createVerticalBox();
        vBox.add(btnClick);
        vBox.add(btnDrawLine);
        vBox.add(btnDrawCircle);
        vBox.add(btnText);
        vBox.add(btnSave);
        vBox.add(btnLoad);
        add(vBox);
    }

}

class DrawBoard extends Canvas {
    DrawBoard() {

    }

    public final void paint(Graphics g) {
        super.paint(g);

    }

}
