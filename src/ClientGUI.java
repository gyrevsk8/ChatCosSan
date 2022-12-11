import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ClientGUI extends JFrame {

    protected boolean light = true;
    JLabel label;
    JButton button = new JButton("Enter");
    JButton outfit = new JButton("☽");
    JTextField input = new JTextField("",15);
    public JLabel textArea = new JLabel("DFSDFSDFGSDFG");

    JLabel johnsnow = new JLabel();
    String messege = "";
    final JScrollPane scrollPane = new JScrollPane(textArea);




    public ClientGUI()
    {

        super("Client ");
        ImageIcon icon = new ImageIcon("wp.jpg");
        icon = new ImageIcon(icon.getImage().getScaledInstance(300,150, BufferedImage.SCALE_SMOOTH));
        ImageIcon finalIcon = icon;


     //   textArea.setOpaque(false);
        this.setBounds(150, 150, 480, 260);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);





        textArea.setText("<html>");
        textArea.setBackground(Color.lightGray);
        textArea.setOpaque(true);


        outfit.setBackground(Color.BLACK);
        outfit.setForeground(Color.white);
        outfit.setFocusPainted(false);
        outfit.setOpaque(true);

        button.setFocusPainted(false);

        Container container = this.getContentPane();
        scrollPane.setMinimumSize(new Dimension(280,150));
        scrollPane.setPreferredSize(new Dimension(280,160));
        scrollPane.setWheelScrollingEnabled(true);
        container.setLayout(new GridBagLayout());

        //textArea.setIcon(icon);
        super.setIconImage(icon.getImage());
        GridBagConstraints constraints = new GridBagConstraints();
        // container.setLayout(new FlowLayout(FlowLayout.));
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.weightx = 0.5;
        constraints.gridy   = 0  ;
        johnsnow.setFont(new Font("Times New Roman", Font.BOLD, 16));
        textArea.setFont(new Font("Times New Roman", Font.BOLD, 18));
        constraints.gridwidth = 2;
        constraints.gridx = 2;
        container.add(scrollPane,constraints);

        constraints.gridx = 2;
        constraints.gridy = 4;

        johnsnow.setPreferredSize(new Dimension(280,40));
        container.add(johnsnow,constraints);

        constraints.gridx = 2;
        constraints.gridy = 5;
        container.add(input,constraints);

        constraints.ipady     = 1;   // кнопка высокая
        constraints.ipadx = 1;
        constraints.weightx   = 0.0;
        constraints.gridwidth = 1;    // размер кнопки в две ячейки
        constraints.gridx     = 4;    // нулевая ячейка по горизонтали
        constraints.gridy     = 0;    // первая ячейка по вертикали
        container.add(outfit, constraints);


        constraints.ipady     = 1;   // кнопка высокая
        constraints.ipadx = 1;
        constraints.weightx   = 0.0;
        constraints.gridwidth = 1;    // размер кнопки в две ячейки
        constraints.gridx     = 4;    // нулевая ячейка по горизонтали
        constraints.gridy     = 5;    // первая ячейка по вертикали
        container.add(button, constraints);

        outfit.addActionListener(new OutfitListener());
        button.addActionListener(new ButtonListener());
        input.addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    messege+=input.getText();
                   // textArea.setText( textArea.getText() + "<p>" + input.getText());
                    JScrollBar vertical = scrollPane.getVerticalScrollBar();
                    vertical.setValue( vertical.getMaximum() );
                    //System.out.println(textArea.getText());
                    System.out.println(messege);
                    Client.currentCommand = messege;

                    Client.asd = false;
                    messege = "";

                    input.setText("");
                   // input.setCaretPosition(0);
                }
            }

            public void keyReleased(KeyEvent e) {



            }

            public void keyTyped(KeyEvent e) {


            }

        });
        container.revalidate();
    }

    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

           messege+=input.getText();
           //textArea.setText( textArea.getText() + "<p>" + input.getText());
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue( vertical.getMaximum() );
            //System.out.println(textArea.getText());
           System.out.println(messege);
           Client.currentCommand = messege;
           input.setText("");
           Client.asd = false;
           messege = "";
            input.setCaretPosition(0);

        }
    }
    class OutfitListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e) {
            if (light) {
              getContentPane().setBackground(Color.BLACK);
              textArea.setBackground(Color.DARK_GRAY);
             textArea.setForeground(Color.white);
              textArea.setOpaque(true);
                //ImageIcon  i = new ImageIcon("src/wp.jpg");// Получить файлы изображений под соответствующим путем (эта картина должна быть под SRC)
               // i.setImage(i.getImage().getScaledInstance(textArea.getWidth()-1,textArea.getHeight()-1,Image.SCALE_AREA_AVERAGING));
                //textArea.setIcon(i);
               // textArea.setOpaque(true);

              scrollPane.setBackground(Color.BLACK);
              scrollPane.setOpaque(true);

              button.setBackground(Color.black);
              button.setForeground(Color.WHITE);
              button.setOpaque(true);

              johnsnow.setBackground(Color.DARK_GRAY);
              johnsnow.setForeground(Color.WHITE);
              johnsnow.setOpaque(true);

              input.setBackground(Color.DARK_GRAY);
              input.setForeground(Color.WHITE);
              input.setOpaque(true);

              outfit.setBackground(Color.WHITE);
              outfit.setForeground(Color.black);
              outfit.setText("☼");
              outfit.setOpaque(true);

              light = !light;
            } else
            {
                getContentPane().setBackground(Color.WHITE);

                ImageIcon  i = new ImageIcon("src/11.jpg");// Получить файлы изображений под соответствующим путем (эта картина должна быть под SRC)
                i.setImage(i.getImage().getScaledInstance(textArea.getWidth()-1,textArea.getHeight()-1,Image.SCALE_AREA_AVERAGING));

               // textArea.setIcon(i);
               // textArea.setOpaque(true);
                textArea.setBackground(Color.lightGray);
                textArea.setForeground(Color.BLACK);
                textArea.setOpaque(true);

                johnsnow.setBackground(Color.WHITE);
                johnsnow.setForeground(Color.BLACK);
                johnsnow.setOpaque(true);

                button.setBackground(Color.WHITE);
                button.setForeground(Color.black);
                button.setOpaque(true);

                input.setBackground(Color.WHITE);
                input.setForeground(Color.BLACK);
                input.setOpaque(true);

                outfit.setBackground(Color.BLACK);
                outfit.setForeground(Color.WHITE);
                outfit.setText("☽");
                outfit.setOpaque(true);
                light = !light;
            }


        }
    }





}

