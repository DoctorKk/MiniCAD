package src;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
//import src.Model;
//import src.Control;

public class View {

    public View() {
        MyFrame frame = new MyFrame();
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                // 获取键值，和 KeyEvent.VK_XXXX 常量比较确定所按下的按键
                int keyCode = e.getKeyCode();
                System.out.println("按下: " + e.getKeyCode());
            }

            public void keyTyped(KeyEvent e) {
                // e.getKeyChar() 获取键入的字符
                System.out.println("键入: " + e.getKeyChar());
            }

            public void keyReleased(KeyEvent e) {
                System.out.println("释放: " + e.getKeyCode());
            }
        });
        // Control.addKeyControl(frame);
        frame.setVisible(true);
    }
}

class MyFrame extends JFrame {
    private final String TITLE = "MiniCAD";
    private final int HEIGHT = 900;
    private final int WIDTH = 1600;
    private static Timer timer;

    private class ReboundListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }

    MyFrame() {
        super();
        init();
        timer = new Timer(100, new ReboundListener());
        timer.start();
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
        // Control C = new Control();
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
                Model.startP = null;
                Model.endP = null;
                // System.out.println(Model.CurrentState);
            }
        });

        final JButton btnDrawCircle = new JButton();
        btnDrawCircle.setIcon(new ImageIcon("./img/Circle.png"));
        btnDrawCircle.setBorderPainted(false);
        btnDrawCircle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.CurrentState = 2;
                Model.startP = null;
                Model.endP = null;
            }
        });

        final JButton btnDrawRentangle = new JButton();
        btnDrawRentangle.setIcon(new ImageIcon("./img/Rectangle.png"));
        btnDrawRentangle.setBorderPainted(false);
        btnDrawRentangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.CurrentState = 3;
                Model.startP = null;
                Model.endP = null;
            }
        });

        final JButton btnText = new JButton();
        btnText.setIcon(new ImageIcon("./img/Text.png"));
        btnText.setBorderPainted(false);
        btnText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.CurrentState = 4;
                Model.curText = JOptionPane.showInputDialog(null, "输入文本内容:", "Text");
            }
        });

        final JButton btnSave = new JButton();
        btnSave.setIcon(new ImageIcon("./img/Save.png"));
        btnSave.setBorderPainted(false);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Model.CurrentState = 3;
                // showFileOpenDialog(null);
                showFileSaveDialog(null);
            }
        });

        final JButton btnLoad = new JButton();
        btnLoad.setIcon(new ImageIcon("./img/Load.png"));
        btnLoad.setBorderPainted(false);
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Model.CurrentState = 3;
                showFileOpenDialog(null);
                // try {
                // Model.deserializeItem();
                // } catch (Exception t) {

                // }
            }
        });

        final JButton btnColor = new JButton();
        btnColor.setIcon(new ImageIcon("./img/Color.png"));
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

            }
        }
    }

    private static void showFileSaveDialog(Component parent) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置打开文件选择框后默认输入的文件名
        fileChooser.setSelectedFile(new File("测试文件.zip"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showSaveDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"保存", 则获取选择的保存路径
            File file = fileChooser.getSelectedFile();
            try {
                Model.serializeItem(file);
            } catch (FileNotFoundException t) {

            } catch (IOException t) {

            }
        }
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
