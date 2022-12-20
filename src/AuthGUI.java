import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthGUI extends JFrame {
    JLabel userSign = new JLabel("Username");
    JLabel passwordSign = new JLabel("Password");
    JButton buttonLeft = new JButton("Sign in");
    JButton buttonRight = new JButton("Sign up");
    JTextField input1 = new JTextField("",15);
    JTextField input2 = new JTextField("",15);
    Container container = this.getContentPane();
    GridBagConstraints constraints = new GridBagConstraints();
    public AuthGUI() {
        super("Authorization");
        this.setBounds(860, 400, 250, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonLeft.setPreferredSize(new Dimension(100,23));
        buttonRight.setPreferredSize(new Dimension(100,23));
        container.setLayout(new GridBagLayout());
        constraints.fill = GridBagConstraints.HORIZONTAL;

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
        //container.add(input,constraints);
        container.revalidate();


       buttonRight.addActionListener(new ButtonListener());
       buttonLeft.addActionListener(new ButtonListener());
    }
 class ButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent e)
     {
         e.getActionCommand();
         if(e.getActionCommand().equals("Sign up"))
         {
             Client.currentCommand = "su";
             Client.inputFlag = false;
         }
         if(e.getActionCommand().equals("Sign in"))
         {
             Client.currentCommand = "si";
             Client.inputFlag = false;
         }
     }

    }


}
