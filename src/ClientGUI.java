import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame {
    JButton button = new JButton("Enter");
    JTextField input = new JTextField("",15);
    JLabel textArea = new JLabel();

    JLabel johnsnow = new JLabel();
    String messege = "";


    public ClientGUI()
    {

        super("Client ");
        this.setBounds(150, 150, 280, 120);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();

        container.setLayout(new GridLayout(4, 1, 2, 2));
        johnsnow.setFont(new Font("Times New Roman", Font.BOLD, 16));

        textArea.setMinimumSize(new Dimension(60,60));
        textArea.setPreferredSize(new Dimension(40,40));
        textArea.setMaximumSize(new Dimension(40,40));

        container.add(textArea);
        container.add(johnsnow);
        container.add(input);
        container.add(button);
        button.addActionListener(new ButtonListener());
    }

    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
           messege+=input.getText();
            textArea.setText(textArea.getText()+'\n'+input.getText());
           System.out.println(messege);
           Client.currentCommand = messege;
           input.setText("");
           Client.asd = false;
           messege = "";

        }
    }


}
