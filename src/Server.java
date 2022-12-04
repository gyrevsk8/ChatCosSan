import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ClientInfoStatus;

public class Server implements ServerF {

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {

        try {

            while (!serverSocket.isClosed()) { // Для того, чтобы сервак не закрывался и ждал подключения
                Socket socket = serverSocket.accept(); // Ждем подключение клиента
                System.out.println("New client was connected");

                ClientHandler clientHandler = new ClientHandler(socket); // Некий коммутатор - он будет получать сообщения одного клиента и отправлять их другому
                Thread thread = new Thread(clientHandler); // Для того, чтобы мы могли создавать несколько потоков
                thread.start();

            }

        } catch (IOException e) {

        }

    }


    public void closeServerSocket() {
        try {
            if (serverSocket != null) { // Если serverSocket окажется null, выдаст нам ошибку, т.к метод close() чувтсвителен к этому. Этот кусок это правит.
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException{

        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}

