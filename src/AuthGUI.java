import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthGUI extends JFrame {
    JButton buttonLeft = new JButton("Sign in");//создание кнопок выбора
    JButton buttonRight = new JButton("Sign up");//создание кнопок выбора

    Container container = this.getContentPane();//контейнер содержащий объекты
    GridBagConstraints constraints = new GridBagConstraints();//создание канфигуратора для сетки
    public AuthGUI() {
        super("Authorization");//установка имени окна
        this.setBounds(860, 400, 250, 150);//установка метса и размеров окна
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//операция при закрытии окна

        buttonLeft.setPreferredSize(new Dimension(100,23));//установка желаемых размеров кнопок
        buttonRight.setPreferredSize(new Dimension(100,23));//установка желаемых размеров кнопок
        container.setLayout(new GridBagLayout());//установка сетки GridBag
        constraints.fill = GridBagConstraints.HORIZONTAL;//устанавливает для элементов растяжку по горизонтали

        constraints.gridwidth = 1;//ширина 1 клетка
        constraints.gridy = 1;//координата по у
        constraints.gridx = 2;//координата по х
        container.add(buttonLeft,constraints);
        constraints.gridy = 1;//координата по у
        constraints.gridx = 4;//координата по х
        container.add(buttonRight,constraints);

        constraints.gridy = 2;//координата по у
        constraints.gridx = 1;//координата по х
        constraints.gridwidth = 4;//ширина 4 клетки
        //container.add(input,constraints);
        container.revalidate();//


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
