import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame {
    JButton button = new JButton("Enter");
    JTextArea input = new JTextArea(1,15);
    JLabel textArea = new JLabel("DFSDFSDFGSDFG");

    JLabel johnsnow = new JLabel();
    String messege = "";
    final JScrollPane scrollPane = new JScrollPane(textArea);

    public ClientGUI()
    {

        super("Client ");
        this.setBounds(150, 150, 280, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textArea.setText("<html>");

        Container container = this.getContentPane();
        scrollPane.setMinimumSize(new Dimension(280,80));
        scrollPane.setPreferredSize(new Dimension(280,80));
        scrollPane.setWheelScrollingEnabled(true);
       container.setLayout(new GridBagLayout());
       GridBagConstraints constraints = new GridBagConstraints();
        // container.setLayout(new FlowLayout(FlowLayout.));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.gridy   = 0  ;
        johnsnow.setFont(new Font("Times New Roman", Font.BOLD, 16));
        textArea.setFont(new Font("Times New Roman", Font.ITALIC, 16));
        constraints.gridwidth = 2;
        constraints.gridy = 0;
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
        constraints.gridx     = 3;    // нулевая ячейка по горизонтали
        constraints.gridy     = 7;    // первая ячейка по вертикали
        container.add(button, constraints);

        button.addActionListener(new ButtonListener());
        container.revalidate();
    }

    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
           messege+=input.getText();
           textArea.setText( textArea.getText() + "<p>" + input.getText());
           scrollPane.setViewport(scrollPane.setViewport(););
            System.out.println(textArea.getText());
           System.out.println(messege);
           Client.currentCommand = messege;
           input.setText("");
           Client.asd = false;
           messege = "";

        }
    }


}
