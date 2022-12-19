import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username ;

    private String phonenumber;
    private String password;

    Logger logger = new Logger();
    // IPGet ipg = new IPGet();
    public Client(Socket socket, String username,String phonenumber, String password) {
        try {
            this.socket = socket;

            // В java есть два типа потоков: поток байтов и поток символов.
            // Нам нужен поток символов.
            // В java поток символов "оканчивается на Writer", а поток байтов на Stream.
            // Поэтому мы делаем такую обертку.
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            bufferedWriter.write(username + "\n");
            bufferedWriter.write(phonenumber + "\n");
            bufferedWriter.write(password + "\n");
            bufferedWriter.flush();

            this.username = username;
            this.phonenumber = phonenumber;
            this.password = password;
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public String getName(){
        return username;
    }
    public void sendMessage() {
        try {
            while (socket.isConnected()) {
                Client.sleepe();
                String messageToSend =  currentCommand;
                bufferedWriter.write(username + ":" + messageToSend);
                gui.textArea.setText(gui.textArea.getText()+"<p>"+username+":"+messageToSend);
                JScrollBar vertical = gui.scrollPane.getVerticalScrollBar();
                vertical.setValue( vertical.getMaximum() );
                logger.setNewLogMessage(username,phonenumber, messageToSend);
                if(password.length()>0)
                {
                    System.out.println("Well pass");
                }
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
                       // gui.userlist.setText(msgFromGroupChat.substring(msgFromGroupChat.indexOf("%"),msgFromGroupChat.indexOf("&")));
                        //System.out.println(gui.userlist.getText());
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
    static boolean inputFlag = true;
    static ClientGUI gui = new ClientGUI();
    public static void main(String[] args) throws Exception {
        IPHandler iph = new IPHandler();
        System.out.println("Manual or Auto?");



        gui.serverMessage.setText("Manual or Auto?");
        gui.setVisible(true);
        String ip = new String();
        sleepe();// этот метод усыпляет поток

        if(currentCommand.equals("m")) {

            System.out.println("Manual");
            gui.serverMessage.setText("Manual \n IP:");
            ip = iph.ipset();
        }
        if(currentCommand.equals("a"))
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
        password = upinput("Enter your password: " );


        String phonenew = Phone.checkPhone(phonenumber);
        System.out.println(phonenew);
        gui.serverMessage.setText("");


        Client client = new Client(socket, username, phonenew, password);

        client.listenForMessage();

        client.sendMessage();
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
