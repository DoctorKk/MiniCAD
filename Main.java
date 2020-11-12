import java.util.*;
import java.awt.*;
import javax.swing.*;
import src.View;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                View view = new View();

            }
        });
    }
}
