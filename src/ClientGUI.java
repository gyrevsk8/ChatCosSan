import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ClientGUI extends JFrame {

    protected boolean light = true;
    JButton button = new JButton("Enter");//создание объектов
    JButton outfit = new JButton("☽");//создание объектов
    JButton manual = new JButton("Manual");//создание объектов
    JButton auto = new JButton("Server set");//создание объектов
    JTextField input = new JTextField("",15);//создание объектов, здесь 15-длина ввода
    public JLabel textArea = new JLabel();//создание объектов
    JLabel serverMessage = new JLabel();//создание объектов
    String messege = "";
    final JScrollPane scrollPane = new JScrollPane(textArea);//создание объекта отвечающего за возможность скролить область сообщений
    Container container = this.getContentPane();//передаем контейнеру contenpane нашего окна
    GridBagConstraints constraints = new GridBagConstraints();//создание канфигуратора для сетки


    public ClientGUI()
    {

        super("Client ");//установка имени окна
        this.setBounds(840, 150, 480, 400);//место и размеры окна
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//операция при закрытии

        getRootPane().setBorder(BorderFactory.createEmptyBorder(0,5,0,5));//создание отступа с помощью создателя


        textArea.setText("<html>");//установка в окне текста метки html
        textArea.setBackground(Color.lightGray);//Установка цвета фона
        textArea.setOpaque(true);//устанавливаем разрешение на перекрашивание всех пикселей у объекта
        textArea.setVerticalAlignment(SwingConstants.BOTTOM);//всё будет написано начиная снизу

        auto.addActionListener(new ChoiceListener());// создание слушателей кнопок
        manual.addActionListener(new ChoiceListener());//создание слушателей кнопок

        outfit.setBackground(Color.BLACK);//Установка цвета фона
        outfit.setForeground(Color.white);//Установка цвета фона
        outfit.setFocusPainted(false);//установка свойства для закрашивания
        outfit.setOpaque(true);//устанавливаем разрешение на перекрашивание всех пикселей у объекта

        button.setFocusPainted(false);//установка свойства для закрашивания

        scrollPane.setPreferredSize(new Dimension(280,260));//место и размеры контейнера пролистывания
        scrollPane.setWheelScrollingEnabled(true);//возможность прокручивать область колёсиком


        container.setLayout(new GridBagLayout());//установка сетки GridBag

        constraints.fill = GridBagConstraints.HORIZONTAL;//устанавливает для элементов растяжку по горизонтали

        constraints.weightx = 0.5;//дополнительное место в пол клетки
        constraints.gridy   = 0  ;//у координата
        serverMessage.setFont(new Font("Times New Roman", Font.BOLD, 16));//шрифт через конструктор шрифтов
        textArea.setFont(new Font("Times New Roman", Font.BOLD, 16));//шрифт через конструктор шрифтов
        constraints.gridwidth = 2;//ширина 2 клетки
        constraints.gridx = 2;//координата х
        container.add(scrollPane,constraints);//добавляем объект в контейнер с параметрами constraints

        constraints.gridx = 2;
        constraints.gridy = 4;

        serverMessage.setPreferredSize(new Dimension(280,40));
        container.add(serverMessage,constraints);//добавляем объект в контейнер с параметрами constraints

        constraints.gridwidth =1;
        constraints.ipadx = 2;
        constraints.gridx = 2;
        constraints.gridy = 5;
        container.add(auto,constraints);//добавляем объект в контейнер с параметрами constraints

        constraints.gridwidth = 1; //размер 1 ячейка
        constraints.ipadx = 1; //размер по х
        constraints.gridx = 3; //координата х
        container.add(manual,constraints);//добавляем объект в контейнер с параметрами constraints

        constraints.ipady     = 1;   // кнопка высокая
        constraints.ipadx = 1;      //координата х
        constraints.weightx   = 0.0;//дополнительное место в пол клетки
        constraints.gridwidth = 1;    // размер кнопки в две ячейки
        constraints.gridx     = 4;    // нулевая ячейка по горизонтали
        constraints.gridy     = 0;    // первая ячейка по вертикали
        container.add(outfit, constraints);//добавляем объект в контейнер с параметрами constraints


        constraints.ipady     = 1;   // кнопка высокая
        constraints.ipadx = 1;
        constraints.weightx   = 0.0; //
        constraints.gridwidth = 1;    // размер кнопки в две ячейки
        constraints.gridx     = 4;    // нулевая ячейка по горизонтали
        constraints.gridy     = 5;    // первая ячейка по вертикали
        container.add(button, constraints); //добавляем объект в контейнер с параметрами constraints
        button.show(false); //убираем видимость кнопки

        outfit.addActionListener(new OutfitListener());//добавляем слушатели к кнопкам
        button.addActionListener(new ButtonListener());//добавляем слушатели к кнопкам
        input.addKeyListener(new KeyListener() { //создание слушателя нажатий на клавиатуру

            public void keyPressed(KeyEvent e) {
               // System.out.println(e.getKeyCode());
                if(e.getKeyCode()==KeyEvent.VK_ENTER){//если кнопка ENTER то действуем
                    messege+=input.getText();//получаем текст
                   // textArea.setText( textArea.getText() + "<p>" + input.getText());

                    //System.out.println(textArea.getText());
                    System.out.println(messege);
                    Client.currentCommand = messege;
                    Client.inputFlag = false;
                    messege = "";
                    input.setText("");
                   // input.setCaretPosition(0);
                    scrollPane.getVerticalScrollBar().getMaximum();
                }
            }
            public void keyReleased(KeyEvent e) {
            }
            public void keyTyped(KeyEvent e) {
            }

        });
        container.revalidate();
    }

    class ButtonListener implements ActionListener//создание слушателя для кнопки
    {
        public void actionPerformed(ActionEvent e)
        {

           messege+=input.getText();
           //textArea.setText( textArea.getText() + "<p>" + input.getText());
            //System.out.println(textArea.getText());
            System.out.println(messege);
           Client.currentCommand = messege;
           input.setText("");
           Client.inputFlag = false;
           messege = "";
           input.setCaretPosition(0);
            scrollPane.getVerticalScrollBar().getMaximum();

        }
    }
    class ChoiceListener implements ActionListener//создание слушателя для кнопок
    {
        public void actionPerformed(ActionEvent e)
        {
            JButton btn = (JButton) e.getSource();
            if(btn.getText().equals("Manual"))
            {
                Client.currentCommand="m";
                Client.inputFlag = false;
                container.remove(auto);
                container.remove(manual);
                createInputLine(container,constraints,3,5,input);
                button.show(true);
                container.revalidate();

            }
            if(btn.getText().equals("Server set"))
            {
                Client.currentCommand="a";
                Client.inputFlag = false;
                container.remove(auto);
                container.remove(manual);
                createInputLine(container,constraints,3,5,input);
                button.show(true);
                container.revalidate();
            }

        }
    }
    class OutfitListener implements ActionListener//создание слушателя для кнопки
    {

        public void actionPerformed(ActionEvent e) {
            if (light) {
              getRootPane().setBackground(Color.DARK_GRAY);//установка цвета
              getContentPane().setBackground(Color.DARK_GRAY);//установка цвета
              textArea.setBackground(Color.DARK_GRAY);//установка цвета
              textArea.setForeground(Color.white);//установка цвета
              textArea.setOpaque(true);
                //ImageIcon  i = new ImageIcon("src/wp.jpg");// Получить файлы изображений под соответствующим путем (эта картина должна быть под SRC)
               // i.setImage(i.getImage().getScaledInstance(textArea.getWidth()-1,textArea.getHeight()-1,Image.SCALE_AREA_AVERAGING));
                //textArea.setIcon(i);
               // textArea.setOpaque(true);

              scrollPane.setBackground(Color.BLACK);//установка цвета
              scrollPane.setOpaque(true);

              button.setBackground(Color.black);//установка цвета
              button.setForeground(Color.WHITE);//установка цвета
              button.setOpaque(true);

              serverMessage.setBackground(Color.DARK_GRAY);//установка цвета
              serverMessage.setForeground(Color.WHITE);//установка цвета
              serverMessage.setOpaque(true);

              input.setBackground(Color.DARK_GRAY);//установка цвета
              input.setForeground(Color.WHITE);//установка цвета
              input.setOpaque(true);

              outfit.setBackground(Color.WHITE);//установка цвета
              outfit.setForeground(Color.black);//установка цвета
              outfit.setText("☼");
              outfit.setOpaque(true);

              light = !light;
            } else
            {
                getRootPane().setBackground(Color.WHITE);//установка цвета
                getContentPane().setBackground(Color.WHITE);//установка цвета

                ImageIcon  i = new ImageIcon("src/11.jpg");// Получить файлы изображений под соответствующим путем (эта картина должна быть под SRC)
                i.setImage(i.getImage().getScaledInstance(textArea.getWidth()-1,textArea.getHeight()-1,Image.SCALE_AREA_AVERAGING));

               // textArea.setIcon(i);
               // textArea.setOpaque(true);
                textArea.setBackground(Color.lightGray);//установка цвета
                textArea.setForeground(Color.BLACK);//установка цвета
                textArea.setOpaque(true);

                serverMessage.setBackground(Color.WHITE);//установка цвета
                serverMessage.setForeground(Color.BLACK);//установка цвета
                serverMessage.setOpaque(true);

                button.setBackground(Color.WHITE);//установка цвета
                button.setForeground(Color.black);//установка цвета
                button.setOpaque(true);

                input.setBackground(Color.WHITE);//установка цвета
                input.setForeground(Color.BLACK);//установка цвета
                input.setOpaque(true);

                outfit.setBackground(Color.BLACK);//установка цвета
                outfit.setForeground(Color.WHITE);//установка цвета
                outfit.setText("☽");
                outfit.setOpaque(true);
                light = !light;
            }


        }
    }


    void createInputLine(Container container, GridBagConstraints constraints, int x, int y, JTextField input)
    {
        constraints.gridx = x;
        constraints.gridy = y;
        container.add(input,constraints);

    }


}

