import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server implements ServerF { // Чтобы получить доступ к методам интерфейса,
    // интерфейс должен быть «реализован» другим классом с ключевым словом implements.

    //Интерфейсы определяют некоторый функционал, не имеющий конкретной реализации,
    // который затем реализуют классы, применяющие эти интерфейсы.

    private ServerSocket serverSocket; // Класс ServerSocket

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {

        try { // Блок Try-catch: простыми словами try-пробуй сделать это, если обнаружишь ошибку, то catch(отлавливай ее).

            while (!serverSocket.isClosed()) { // Для того, чтобы сервак не закрывался и ждал подключения
                Socket socket = serverSocket.accept(); // Ждем подключение клиента
                // (Возвращает экземпляр класса Socket, с которым в дальнейшем будет работа по обмену данными.
                // Данный метод ожидает подключения к созданной ServerSocket (к открытому порту, описанному при создании ServerSocket),
                // пока подключение не произошло, находится в состоянии ожидания.

                System.out.println("New client was connected");


                ClientHandler clientHandler = new ClientHandler(socket); // Клиентский обработчик - посредник между сервером и клиентом.
                // Читает сообщения и отправляет их, заносит данные в базу и т.д.

                Thread thread = new Thread(clientHandler); // Для того, чтобы мы могли создавать несколько потоков
                // В Java поток – это единица реализации программного кода.
                // Последовательность данных, которая могут работать параллельно с другими своими «аналогами».
                thread.start(); // Запуск потока
            }

        } catch (IOException e) { // Исключение, которое выдается при возникновении ошибки ввода-вывода

        }

    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) { // Если serverSocket окажется null, выдаст нам ошибку, т.к метод close() чувтсвителен к этому. Этот кусок это правит.
                serverSocket.close(); // Закрываем socket
            }
        } catch (IOException e) {
            e.printStackTrace(); // Печатает трассировку стека
            // Стек хранит значения примитивных переменных, создаваемых в методах
        }
    }


    public static void main(String[] args) throws IOException{ //Оператор throws используется в объявлении метода для того,
        // чтобы сообщить вызывающий код о том, что данный метод может генерировать исключение, которое он не обрабатывает.

        ServerSocket serverSocket = new ServerSocket(1234); //Создает серверный сокет, привязанный к указанному порту.
        Server server = new Server(serverSocket);
        server.startServer();
    }
}

