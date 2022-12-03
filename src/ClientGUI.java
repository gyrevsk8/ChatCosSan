import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.Reader;
import java.io.Writer;

public class ClientGUI extends JFrame {
    JButton button = new JButton("Enter");
    JTextField input = new JTextField("",15);
    String messege = "";

    public ClientGUI()
    {

        super("Client ");
        this.setBounds(100,100,250,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(2,1,2,2));
        container.add(input);
        container.add(button);
        button.addActionListener(new ButtonListener());
    }
    boolean chek = false;
    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
           messege+=input.getText();
           Client.currentCommand = messege;
           chek=true;
           messege = "";
            Client.currentCommand = messege;
         //  messege = "";
           //chek = false;
        }
    }


}
