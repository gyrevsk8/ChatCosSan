import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientHandler implements Runnable,ClientF{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>(); // Для того чтобы прокрутить всех
    // (для последующей отправки сообщений через BufferWriter)

    private Socket socket; // Для установления соединения между клиентом и сервером
    private BufferedReader bufferedReader; // Для того, чтобы считывать отправленные сообщения
    private BufferedWriter bufferedWriter; // Для того, чтобы отправить сообщение клиенту
    private String clientUsername ; // Имя пользователя
    private String clientPhone;
    private String clientPassword;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;

            // В java есть два типа потоков: поток байтов и поток символов.
            // Нам нужен поток символов.
            // В java поток символов "оканчивается на Writer", а поток байтов на Stream.
            // Поэтому мы делаем такую обертку.
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.clientUsername = bufferedReader.readLine(); // Считываем имя пользователя
            Client.gui.userlist.setText(clientUsername);
            this.clientPhone = bufferedReader.readLine();
            this.clientPassword = bufferedReader.readLine();

            DatabaseHandler dbHandler = new DatabaseHandler();
            dbHandler.singUpUser(clientUsername, clientPhone, clientPassword);


            clientHandlers.add(this); // Добавляем пользователя в массив
            broadcastMessage("SERVER: " + clientUsername + " has entered the chat");
            setNames();
            Client.gui.textArea.setText(Client.gui.textArea.getText()+"<p>"+"SERVER: " + clientUsername + " has entered the chat");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void setNames() {
        for (ClientHandler clientHandler : clientHandlers)
        {
            Client.gui.addUserlist(Client.gui.clientUsernames,clientHandler.clientUsername);
    }

    }
    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {// цикл for each
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write('\n'+messageToSend);
                    Client.gui.textArea.setText(Client.gui.textArea.getText()+"<p>"+clientUsername+":"+messageToSend);
                    clientHandler.bufferedWriter.newLine(); // Говорит - брат, я отправил сообщение, не нужно больше ожидать текст
                    clientHandler.bufferedWriter.flush(); // Очищаем буфер
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler() { // Если клиент отключился, мы его удаляем, потому, что нам больше не нужно отправлять ему сообщения

        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUsername + " has left the chat!");
        //  Client.gui.addUserlist(Client.gui.clientUsernames,);
        Client.gui.textArea.setText(Client.gui.textArea.getText()+"<p>"+"SERVER: " + clientUsername + " has left the chat!");

    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
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
}
