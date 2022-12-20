import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AuthGUI extends JFrame {
    JLabel label = new JLabel("");
    JButton buttonLeft = new JButton("Sign in");
    JButton buttonRight = new JButton("Sign up");
    JTextField input = new JTextField("?",15);
    Container container = this.getContentPane();
    GridBagConstraints constraints = new GridBagConstraints();
    public AuthGUI() {
        super("Authorization");
        this.setBounds(750, 150, 250, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonLeft.setPreferredSize(new Dimension(100,23));
        buttonRight.setPreferredSize(new Dimension(100,23));
        container.setLayout(new GridBagLayout());
        constraints.fill = GridBagConstraints.HORIZONTAL;


        constraints.gridwidth = 2;
        constraints.gridy = 0;
        constraints.gridx = 1;
        container.add(label,constraints);

        constraints.gridwidth = 1;
        constraints.gridy = 1;
        constraints.gridx = 2;
        container.add(buttonLeft,constraints);
        constraints.gridy = 1;
        constraints.gridx = 4;
        container.add(buttonRight,constraints);

        constraints.gridy = 2;
        constraints.gridx = 1;
        constraints.gridwidth = 4;
        container.add(input,constraints);
        container.revalidate();


       //buttonRight.addActionListener(new SignListner());
        //buttonLeft.addActionListener(new SignListner());
    }



}
