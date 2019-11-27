import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;

public class ActionListener extends UI {
    private JTextField box1;
    private JTextField box2;
    private JTextField box3;
    private JPasswordField passwordField;

    /**
     * Dont know the window created but this is just a half structed
     * class. In theory, we add these fields to our boxes.
     */

    //public class that extends a window class (maybe UI) not sure
    public ActionListener() {

        super(300, 125, 100);
        setLayout(new FlowLayout());

        box1 = new JTextField("Score");
        add(box1); //add method needs to be made

        box2 = new JTextField("Store");
        add(box2);

        box3 = new JTextField("End");
        add(box3);

        passwordField = new JPasswordField("Password");
        add(passwordField);

        FieldButton Button = new FieldButton();
        box1.addActionListener(Button);
        box2.addActionListener(Button);
        box3.addActionListener(Button);
        passwordField.addActionListener(Button);
    }

    private class FieldButton implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            String string = "";
            if (event.getSource() == box1) {
                string = string.format("testField 1: %s", event.getActionCommand());
            } else if (event.getSource() == box2) {
                string = string.format("testField 2: %s", event.getActionCommand());
            } else if (event.getSource() == box3) {
                string = string.format("testField 3: %s", event.getActionCommand());
            } else if (event.getSource() == passwordField) {
                string = string.format("password field is : %s", event.getActionCommand());
            }

            JOptionPane.showMessageDialog(null, string);
        }
    }
}