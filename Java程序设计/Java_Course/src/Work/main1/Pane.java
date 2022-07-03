package Work.main1;

import javax.swing.*;

public class Pane {
    private JTextArea textArea1;
    private JPanel panel1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pane");
        frame.setContentPane(new Pane().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
