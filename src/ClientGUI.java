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
    JTextField snow = new JTextField();
    String messege = "";

    public ClientGUI()
    {

        super("Client ");
        input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {


                if( e.getExtendedKeyCode() == 32)
                {
                    messege+=input.getText();
                    System.out.println(messege);
                    Client.currentCommand = messege;
                    input.setText("");
                    Client.asd = false;
                    messege = "";
                }

            }
        });

        this.setBounds(150,150,250,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3,1,2,2));

        snow.setFont(new Font("Times New Roman",Font.BOLD,16));
        container.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {


               if( e.getExtendedKeyCode() == 32)
               {
                   messege+=input.getText();
                   System.out.println(messege);
                   Client.currentCommand = messege;
                   input.setText("");
                   Client.asd = false;
                   messege = "";
               }

            }
        });
        container.add(snow);
        container.add(input);
        container.add(button);
        button.addActionListener(new ButtonListener());
    }

    class EnterListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {


        }
    }
    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
           messege+=input.getText();
           System.out.println(messege);
           Client.currentCommand = messege;
           input.setText("");
           Client.asd = false;
           messege = "";

          // Client.currentCommand = messege;

         //  messege = "";
           //chek = false;
        }
    }


}
