import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    Mss mss = new Mss();
    public Client(Socket socket, String username) {
       try {
           this.socket = socket;

           // В java есть два типа потоков: поток байтов и поток символов.
           // Нам нужен поток символов.
           // В java поток символов "оканчивается на Writer", а поток байтов на Stream.
           // Поэтому мы делаем такую обертку.
           this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
           this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           
           this.username = username;
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
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                mss.mess(username, messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
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

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter IP pls: ");
        String ip = scanner.nextLine();
        Socket socket = new Socket(ip, 1234);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
    }
}
