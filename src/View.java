package src;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class View extends JFrame implements AWTEventListener {
    private final String TITLE = "MiniCAD";
    private final int HEIGHT = 900;
    private final int WIDTH = 1600;
    private static Timer timer;

    public View() {
        super();
        init();

        // start timer
        timer = new Timer(100, new ReboundListener());
        timer.start();
    }

    // initialize
    private void init() {
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // add KeyListener
        getToolkit().addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);

        // add panel
        MyPanel panel = new MyPanel();
        setContentPane(panel);
        setVisible(true);
    }

    // for timer,refresh the screen
    private class ReboundListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }

    // Key Event
    @Override
    public void eventDispatched(AWTEvent event) {
        if (Model.choosingIndex < 0)
            return;
        if (event.getID() == KeyEvent.KEY_PRESSED) {
            KeyEvent e = (KeyEvent) event;
            itemsMesg temp = Model.itemsM.get(Model.choosingIndex);
            if (Model.choosingIndex >= 0) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_Q:
                        temp.size = temp.size < 5 ? 5 : (temp.size - 1);
                        break;
                    case KeyEvent.VK_E:
                        temp.size++;
                        break;
                    case KeyEvent.VK_A:
                        if (temp.getState() == 4) {
                            temp.size = temp.size < 5 ? 5 : (temp.size - 1);
                        } else {
                            temp.p2.translate(-5, -5 * (temp.p2.y - temp.p1.y) / (temp.p2.x - temp.p1.x));
                            temp.setItem(Control.createShape(temp.p1, temp.p2, temp.getState()));
                        }
                        break;
                    case KeyEvent.VK_D:
                        if (temp.getState() == 4) {
                            temp.size++;
                        } else {
                            temp.p2.translate(5, 5 * (temp.p2.y - temp.p1.y) / (temp.p2.x - temp.p1.x));
                            temp.setItem(Control.createShape(temp.p1, temp.p2, temp.getState()));
                        }
                        break;
                    case KeyEvent.VK_R:
                        Model.itemsM.remove(Model.choosingIndex);
                        Model.choosingIndex = -1;
                        break;
                    default:
                        break;
                }
            }
            System.out.println(e.getKeyCode());
        }
    }

}

class MyPanel extends JPanel {
    private static Timer timer;

    // initialize, use BorderLayout
    MyPanel() {
        super(new BorderLayout());
        init();
    }

    // some buttons put in the left, drawing board put in the center
    private void init() {
        Frame Left = new Frame();
        DrawBoard board = new DrawBoard();
        Control.addControl(board);

        add(Left, BorderLayout.WEST);
        add(board, BorderLayout.CENTER);

    }
}

// buttons
class Frame extends JPanel {
    Frame() {
        super();
        init();
    }

    private void init() {
        // button for select
        final JButton btnClick = new JButton();
        btnClick.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("./img/Click.png")));
        btnClick.setBorderPainted(false);
        btnClick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.CurrentState = 0;
                Model.choosingIndex = -1;
            }
        });

        // draw lines
        final JButton btnDrawLine = new JButton();
        btnDrawLine.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("./img/Line.png")));
        btnDrawLine.setBorderPainted(false);
        btnDrawLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.CurrentState = 1;
                Model.choosingIndex = -1;
                Model.startP = null;
                Model.endP = null;

            }
        });

        // draw ellipses
        final JButton btnDrawCircle = new JButton();
        btnDrawCircle.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("./img/Circle.png")));
        btnDrawCircle.setBorderPainted(false);
        btnDrawCircle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.CurrentState = 2;
                Model.choosingIndex = -1;
                Model.startP = null;
                Model.endP = null;
            }
        });
        // draw rectangles
        final JButton btnDrawRentangle = new JButton();
        btnDrawRentangle.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("./img/Rectangle.png")));
        btnDrawRentangle.setBorderPainted(false);
        btnDrawRentangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.CurrentState = 3;
                Model.choosingIndex = -1;
                Model.startP = null;
                Model.endP = null;
            }
        });
        // insert text
        final JButton btnText = new JButton();
        btnText.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("./img/Text.png")));
        btnText.setBorderPainted(false);
        btnText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.CurrentState = 4;
                Model.curText = JOptionPane.showInputDialog(null, "杈撳叆鏂囨湰鍐呭:", "Text");
            }
        });
        // save as file
        final JButton btnSave = new JButton();
        btnSave.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("./img/Save.png")));
        btnSave.setBorderPainted(false);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                showFileSaveDialog(null);
            }
        });
        // load file
        final JButton btnLoad = new JButton();
        btnLoad.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("./img/Load.png")));
        btnLoad.setBorderPainted(false);
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                showFileOpenDialog(null);
                Model.choosingIndex = -1;
                Model.startP = null;
                Model.endP = null;

            }
        });
        // select color
        final JButton btnColor = new JButton();
        btnColor.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("./img/Color.png")));
        btnColor.setBorderPainted(false);
        btnColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "Color", null);
                if (c != null) {
                    if (Model.choosingIndex >= 0) {
                        Model.itemsM.get(Model.choosingIndex).setColor(c);
                        repaint();
                    }
                }
            }
        });

        Box vBox = Box.createVerticalBox();
        vBox.add(btnClick);
        vBox.add(btnDrawLine);
        vBox.add(btnDrawCircle);
        vBox.add(btnDrawRentangle);
        vBox.add(btnText);
        vBox.add(btnSave);
        vBox.add(btnLoad);
        vBox.add(btnColor);
        add(vBox);
    }

    private static void showFileOpenDialog(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setMultiSelectionEnabled(false);

        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println(file.getAbsolutePath());
            try {
                Model.deserializeItem(file);
            } catch (Exception t) {
                System.out.println("Something went wrong while loading a file");
            }
        }
    }

    private static void showFileSaveDialog(Component parent) {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setSelectedFile(new File("File"));

        int result = fileChooser.showSaveDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {

            File file = fileChooser.getSelectedFile();
            try {
                Model.serializeItem(file);
            } catch (Exception t) {
                System.out.println("Something went wrong while saving");
            }
        }
    }
}

// Drawing board
class DrawBoard extends JComponent {
    DrawBoard() {
    }

    // draw the background
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

    // draw all the objects
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintBackground(g2);

        // draw the object you have not finished yet
        if (Model.startP != null && Model.endP != null) {
            if (Model.CurrentState == 4) {
                g2.setFont(new Font(null, Font.PLAIN, 25));
                g2.drawString(Model.curText, Model.endP.x, Model.endP.y);
            } else {
                Shape temp = null;
                g2.setPaint(Color.BLACK);
                g2.setStroke(new BasicStroke(10));
                temp = Control.createShape(Model.startP, Model.endP, Model.CurrentState);
                if (temp != null)
                    g2.draw(temp);
            }
        }
        // draw all the objects
        for (int i = 0; i < Model.itemsM.size(); i++) {
            g2.setColor(Model.itemsM.get(i).getColor());
            g2.setStroke(new BasicStroke(Model.itemsM.get(i).getSize()));
            if (Model.itemsM.get(i).getState() == 4) {
                g2.setFont(new Font(null, Font.PLAIN, Model.itemsM.get(i).getSize()));
                g2.drawString(Model.itemsM.get(i).text, Model.itemsM.get(i).getStartP().x,
                        Model.itemsM.get(i).getStartP().y);
            } else
                g2.draw(Model.itemsM.get(i).getItem());
        }
    }
}
