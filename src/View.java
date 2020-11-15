package src;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
//import src.Model;
//import src.Control;

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

    private static Timer timer;
    // DrawBoard board;
    // DrawBoard board = new DrawBoard();

    MyPanel() {
        super(new BorderLayout());
        init();
        // Control.addControl(this);
        Control C = new Control();
        // this.frame = frame;
    }

    private void init() {
        Frame Left = new Frame();
        // board = new DrawBoard();
        DrawBoard board = new DrawBoard();
        add(Left, BorderLayout.WEST);
        // JPanel Board = new JPanel();
        // Board.add(board);
        Control.addControl(board);
        add(board, BorderLayout.CENTER);
        // add(board, BorderLayout.CENTER);
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
                System.out.println(Model.CurrentState);
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

        final JButton btnDrawRentangle = new JButton();
        btnDrawRentangle.setIcon(new ImageIcon("./img/Rectangle.png"));
        btnDrawRentangle.setBorderPainted(false);
        btnDrawRentangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.CurrentState = 3;
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
        vBox.add(btnDrawRentangle);
        vBox.add(btnText);
        vBox.add(btnSave);
        vBox.add(btnLoad);
        add(vBox);
    }

}

class DrawBoard extends JComponent {
    DrawBoard() {
    }

    private void paintBackground(Graphics2D g2) {
        g2.setPaint(Color.LIGHT_GRAY);
        for (int i = 0; i < getSize().width; i += 10) {
            Shape line = new Line2D.Float(i, 0, i, getSize().height);
            g2.draw(line);
        }

        for (int i = 0; i < getSize().height; i += 10) {
            Shape line = new Line2D.Float(0, i, getSize().width, i);
            g2.draw(line);
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintBackground(g2);

        // g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

        if (Model.startP != null && Model.endP != null) {
            Shape temp = null;
            g2.setPaint(Color.BLACK);
            g2.setStroke(new BasicStroke(10));
            temp = Control.createShape(Model.startP, Model.endP, Model.CurrentState);
            /*
             * switch (Model.CurrentState) { case 1: temp = new Line2D.Float(Model.startP,
             * Model.endP); break; case 2: temp = new Ellipse2D.Float(Model.startP.x,
             * Model.startP.y, Math.abs(Model.endP.x - Model.startP.x),
             * Math.abs(Model.endP.y - Model.startP.y)); break; case 3: temp = new
             * Rectangle2D.Float(Math.min(Model.startP.x, Model.endP.x),
             * Math.min(Model.startP.y, Model.endP.y), Math.abs(Model.endP.x -
             * Model.startP.x), Math.abs(Model.endP.y - Model.startP.y)); break; }
             */
            if (temp != null)
                g2.draw(temp);
        }

        for (int i = 0; i < Model.itemsM.size(); i++) {
            g2.setColor(Model.itemsM.get(i).getColor());
            g2.setStroke(new BasicStroke(Model.itemsM.get(i).getSize()));
            g2.draw(Model.itemsM.get(i).getItem());
        }
    }
}
