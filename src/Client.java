import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    private String phonenumber;

    Logger logger = new Logger();
   // IPGet ipg = new IPGet();
    public Client(Socket socket, String username,String phonenumber) {
       try {
           this.socket = socket;

           // В java есть два типа потоков: поток байтов и поток символов.
           // Нам нужен поток символов.
           // В java поток символов "оканчивается на Writer", а поток байтов на Stream.
           // Поэтому мы делаем такую обертку.
           this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
           this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           
           this.username = username;
           this.phonenumber = phonenumber;
       } catch (IOException e) {
           closeEverything(socket, bufferedReader, bufferedWriter);
       }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();

            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                Client.sleepe();
                String messageToSend =  currentCommand;
                bufferedWriter.write(username + ": " + messageToSend);
                gui.textArea.setText(gui.textArea.getText()+"<p>"+username+":"+messageToSend);
                logger.setNewLogMessage(username,phonenumber, messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void listenForMessage() { // Нужно использовать еще один поток. Мы не будем дожидаться получения сообщения, чтобы отправить свое, мы сможем сделать это сразу
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;

                while (socket.isConnected()) {
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
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if (bufferedReader != null) { // Если bufferReader окажется null, выдаст нам ошибку, т.к метод close() чувтсвителен к этому. Этот кусок это правит.
                bufferedReader.close();
            }
            if (bufferedWriter != null) { // Если bufferWriter окажется null, выдаст нам ошибку, т.к метод close() чувтсвителен к этому. Этот кусок это правит.
                bufferedWriter.close();
            }
            if (socket != null) { // Если socket окажется null, выдаст нам ошибку, т.к метод close() чувтсвителен к этому. Этот кусок это правит.
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static String currentCommand = "a";
    static boolean asd = true;
    static ClientGUI gui = new ClientGUI();
    public static void main(String[] args) throws Exception {
        IPHandler iph = new IPHandler();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Manual or Auto?");



        gui.johnsnow.setText("Manual or Auto?");
        gui.setVisible(true);
        String ip = new String();
        sleepe();// этот метод усыпляет поток

        if(currentCommand.equals("m")) {

            System.out.println("Manual");
            gui.johnsnow.setText("Manual \n IP:");
            ip = iph.ipset();
        }
        if(currentCommand.equals("a"))
        {
            System.out.println("Auto");
            gui.johnsnow.setText("Auto");
            ip=iph.ipautoset();
            System.out.println("Autodetected ip: "+ip);
            gui.johnsnow.setText("Autodetected ip: "+ip);
        }


        System.out.println("IP seted");//отладка
        gui.johnsnow.setText("IP seted");
        Socket socket = new Socket(ip, 1234);


        System.out.println("Enter your username: ");
        gui.johnsnow.setText("Enter your username: ");
        sleepe();
       // String username = scanner.nextLine();
        String username = currentCommand;
        System.out.println(currentCommand);//отладка

        System.out.println("Enter your phone: ");//отладка  //
       gui.johnsnow.setText("Enter your phone: ");
        sleepe();

        // String phonenumber = scanner.nextLine();
        String phonenumber = currentCommand;
        String phonenew = Phone.checkPhone(phonenumber);
        gui.johnsnow.setText("");
        Client client = new Client(socket, username,phonenew);
        client.listenForMessage();
        client.sendMessage();
    }

static void sleepe() throws InterruptedException {
    while(asd)
    {
        TimeUnit.SECONDS.sleep(1);
    }
    JScrollBar vertical = gui.scrollPane.getVerticalScrollBar();
    vertical.setValue( vertical.getMaximum() );
    asd = true;//после его использования всегда должен стоять asd=true;
}

}
