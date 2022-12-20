import javax.swing.*;
import java.awt.*;

public class AuthGUI extends JFrame {
    JLabel label = new JLabel("Check");
    JButton buttonLeft = new JButton("Left");
    JButton buttonRight = new JButton("Right");
    JTextField input = new JTextField("",15);
    Container container = this.getContentPane();
    GridBagConstraints constraints = new GridBagConstraints();
    public AuthGUI() {
        super("Authorization");
        this.setBounds(1000, 150, 250, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container.setLayout(new GridBagLayout());
        constraints.fill = GridBagConstraints.BELOW_BASELINE_LEADING;


        constraints.gridy = 0;
        constraints.gridx = 4;
        container.add(label,constraints);
        constraints.gridy = 1;
        constraints.gridx = 2;
        container.add(buttonLeft,constraints);
        constraints.gridy = 1;
        constraints.gridx = 4;
        container.add(buttonRight,constraints);
        constraints.gridy = 2;
        constraints.gridx = 3;
        container.add(input,constraints);
        container.revalidate();
    }

}
