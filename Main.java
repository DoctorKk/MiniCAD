
import java.awt.*;
import javax.swing.*;
import src.View;

public class Main {
    public static void main(String[] args) {
        // change the style
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                View view = new View();
            }
        });
    }
}
