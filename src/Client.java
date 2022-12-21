import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader; //Читает текст из потока ввода символов
    private BufferedWriter bufferedWriter; // Класс BufferedWriter записывает текст в поток
    private String username ;
    private String phonenumber;
    private String password;
    public Client(Socket socket, String username,String phonenumber, String password, String logic) { //Связываемся с серваком, в частности с ClientHandler
        try {
            this.socket = socket;

            //Socket имеет выходной поток, который мы можем использовать для отправки данных и входной поток для получения данных

            // В java есть два типа потоков: поток байтов и поток символов.
            // Нам нужен поток символов.
            // Так как мы получаем поток байтов, то нам нужно обернуть конструкцию
            // В java поток символов "оканчивается на условный Writer", а поток байтов на Stream.
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            if(logic.equals("su")) {
                bufferedWriter.write("false"+ "\n");
                bufferedWriter.write(username + "\n"); //Передаем имя пользователя
                bufferedWriter.write(phonenumber + "\n"); //Передаем номер телефона
                bufferedWriter.write(password + "\n"); //Передаем пароль
                bufferedWriter.flush(); // Очищаем буфер
            }
            if (logic.equals("si"))
            {
                bufferedWriter.write("true"+ "\n");
                bufferedWriter.write(username + "\n"); //Передаем имя пользователя
                bufferedWriter.write(password + "\n"); //Передаем пароль
                bufferedWriter.flush(); // Очищаем буфер
                String result = bufferedReader.readLine();
                if(result.equals("NULL"))
                {
                    gui.textArea.setText("Not registrated");
                }
                if(result.equals(null))
                {
                    gui.textArea.setText("Not registrated");

                }
            }

            this.username = username;
            this.phonenumber = phonenumber;
            this.password = password;

        } catch (IOException e) { // Ловим ошибку ввода-вывода
            closeEverything(socket, bufferedReader, bufferedWriter); // Если поймали ее, закрываем соединение
        }
    }

    public String getName(){ // Нужно чисто для JUnit теста
        return username;
    }
    public void sendMessage() { // Метод отправки сообщений
        try {
            while (socket.isConnected()) { // Пока мы подключены
                Client.sleepe();
                String messageToSend =  currentCommand;
                bufferedWriter.write(username + ":" + messageToSend); // Отправляем сообщение
                gui.textArea.setText(gui.textArea.getText()+"<p>"+username+":"+messageToSend);
                JScrollBar vertical = gui.scrollPane.getVerticalScrollBar();
                vertical.setValue( vertical.getMaximum() );
                if(password.length()>0)
                {
                    System.out.println("Well pass");
                }
                bufferedWriter.newLine(); // Говорит: "Брат, я отправил сообщение, не нужно больше ожидать текст".
                bufferedWriter.flush(); // Очищаем буфер
            }
        } catch (IOException e) { // Ловим ошибку ввода-вывода
            closeEverything(socket, bufferedReader, bufferedWriter); // Если поймали ее, закрываем соединение
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void listenForMessage() { // Нужно использовать еще один поток. Слушание сообщений - "блокирующая" операцияю
        // Мы не будем дожидаться получения сообщения, чтобы отправить свое, мы сможем сделать это сразу.

        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;

                while (socket.isConnected()) { // Пока мы подключены
                    try {
                        msgFromGroupChat = bufferedReader.readLine(); // Считываем сообщение
                        System.out.println(msgFromGroupChat);
                        JScrollBar vertical = gui.scrollPane.getVerticalScrollBar();
                        gui.textArea.setHorizontalAlignment(SwingConstants.RIGHT);
                        gui.textArea.setOpaque(true);
                        vertical.setValue( vertical.getMaximum() );
                        gui.textArea.setText(gui.textArea.getText()+"<p>"+msgFromGroupChat);
                        gui.textArea.setHorizontalAlignment(SwingConstants.LEFT);
                        gui.textArea.setOpaque(true);
                        vertical.setValue( vertical.getMaximum() );
                    } catch (IOException e) { // Ловим ошибку ввода-вывода
                        closeEverything(socket, bufferedReader, bufferedWriter); // Если поймали ее, закрываем соединение
                    }
                }
            }
        }).start(); // Создали объект выше и здесь сразу вызвали его.
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        //Используется, для закрытия соединения(socket) и потоков(BufferedReader, BufferedWriter)

        try {
            // Если bufferReader окажется null, выдаст нам ошибку, т.к метод close() чувтсвителен к этому. Этот кусок это правит.
            if (bufferedReader != null) {
                bufferedReader.close();
            }

            // Если bufferWriter окажется null, выдаст нам ошибку, т.к метод close() чувтсвителен к этому. Этот кусок это правит.
            if (bufferedWriter != null) {

                bufferedWriter.close();
            }

            // Если socket окажется null, выдаст нам ошибку, т.к метод close() чувтсвителен к этому. Этот кусок это правит.
            if (socket != null) {

                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static String currentCommand = "a";
    static boolean inputFlag = true;
    static ClientGUI gui = new ClientGUI();//экземпляр интерфейса пользователя
    public static void main(String[] args) throws Exception {
        IPHandler iph = new IPHandler();//экземпляр обработчика
        AuthGUI agui = new AuthGUI();//экземпляр окошка выбора
        agui.show(true);//включаем окошко выбора
        sleepe();//выполняет функцию усыпления потока
        if(currentCommand.equals("su"))//определяем режим работы
        {
            System.out.println("su");
            agui.show(false);
            System.out.println("Manual or Auto?");
            gui.serverMessage.setText("Manual or Auto?");
            gui.setVisible(true);
            String ip = new String();
            sleepe();// этот метод усыпляет поток

            if(currentCommand.equals("m")) {//определяем режим работы

                System.out.println("Manual");
                gui.serverMessage.setText("Manual \n IP:");
                ip = iph.ipset();
            }
            if(currentCommand.equals("a"))//определяем режим работы
            {
                System.out.println("Auto");
                gui.serverMessage.setText("Auto");
                ip=iph.ipautoset();
                System.out.println("Autodetected ip: "+ip);
                gui.serverMessage.setText("Autodetected ip: "+ip);
            }

            String username = null;
            String phonenumber = null;
            String password = null;

            System.out.println("IP seted");//отладка
            gui.serverMessage.setText("IP seted");
            Socket socket = new Socket(ip, 1234);

            username = upinput("Enter yuor username: ");
            phonenumber = upinput("Enter your phone: ");
            String phonenew = Phone.checkPhone(phonenumber);
            password = upinput("Enter your password: " );



            System.out.println(phonenew);
            gui.serverMessage.setText("");


            String logic;
            Client client = new Client(socket, username, phonenew, password, logic = "su"); // Просто создали экземпляр класса
            client.listenForMessage(); // Запускаем метод для прослушивания сообщений
            client.sendMessage(); // Запускаем метод для отправки сообщений
        }//определяем режим работы
        if(currentCommand.equals("si"))
        {
            System.out.println("Manual or Auto?");
            gui.serverMessage.setText("Manual or Auto?");
            gui.setVisible(true);
            System.out.println("si");
            agui.show(false);
            String ip = new String();
            sleepe();// этот метод усыпляет поток

            if(currentCommand.equals("m")) {//определяем режим работы

                System.out.println("Manual");
                gui.serverMessage.setText("Manual \n IP:");
                ip = iph.ipset();
            }
            if(currentCommand.equals("a"))//определяем режим работы
            {
                System.out.println("Auto");
                gui.serverMessage.setText("Auto");
                ip=iph.ipautoset();
                System.out.println("Autodetected ip: "+ip);
                gui.serverMessage.setText("Autodetected ip: "+ip);
            }

            String username = null;
            String password = null;

            System.out.println("IP seted");//отладка
            gui.serverMessage.setText("IP seted");
            Socket socket = new Socket(ip, 1234);

            username = upinput("Enter yuor username: ");
            password = upinput("Enter your password: " );

            gui.serverMessage.setText("");


            String logic;
            Client client = new Client(socket, username, "", password, logic = "si"); // Просто создали экземпляр класса
            client.listenForMessage(); // Запускаем метод для прослушивания сообщений
            client.sendMessage(); // Запускаем метод для отправки сообщений

        }



    }

    static void sleepe() throws InterruptedException {
        while(inputFlag)
        {
            TimeUnit.MILLISECONDS.sleep(300);
        }
        JScrollBar vertical = gui.scrollPane.getVerticalScrollBar();
        vertical.setValue( vertical.getMaximum() );
        inputFlag = true;//после его использования всегда должен стоять inputFlag=true;
    }
    static String upinput(String message) throws InterruptedException {
        String currentString;
        System.out.println(message );
        gui.serverMessage.setText(message );
        sleepe();
        currentString = currentCommand;
        gui.serverMessage.setText("");
        return currentString;
    }

}
