import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ClientHandler implements Runnable,ClientF {  //Чтобы получить доступ к методам интерфейса,
    // интерфейс должен быть «реализован» другим классом с ключевым словом implements.

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>(); // Собираем объекты клиентского обрабочика,
    // для того чтобы прокрутить всех юзеров (для последующей отправки сообщений через BufferWriter)

    //Static нужен, чтобы этот ArrayList принадлежал только классу, а не всем объектам класса
    private Socket socket; // Для установления соединения между клиентом и сервером
    private BufferedReader bufferedReader; // Для того чтобы считывать отправленные сообщения
    private BufferedWriter bufferedWriter; // Для того чтобы отправить сообщение клиенту
    private String clientUsername; // Имя пользователя
    private String clientPassword; // Пароль
    private String clientPhone; // Номер телефона
    private String logic; // Номер телефона

    public ClientHandler(Socket socket) {
        try {

            this.socket = socket;

            //Socket имеет выходной поток, который мы можем использовать для отправки данных и входной поток для получения данных

            // В java есть два типа потоков: поток байтов и поток символов.
            // Нам нужен поток символов.
            // Так как мы получаем поток байтов, то нам нужно обернуть конструкцию
            // В java поток символов "оканчивается на условный Writer", а поток байтов на Stream.
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.logic = bufferedReader.readLine();

            DatabaseHandler dbHandler = new DatabaseHandler(); // База данных

            if(logic.equals("false")) {
                this.clientUsername = bufferedReader.readLine(); // Считываем имя пользователя
                this.clientPhone = bufferedReader.readLine();
                this.clientPassword = bufferedReader.readLine();
                dbHandler.singUpUser(clientUsername, clientPhone, clientPassword); // Регистрируем пользователя
            }
            if(logic.equals("true"))
            {
                this.clientUsername = bufferedReader.readLine();
                this.clientPassword = bufferedReader.readLine();
                bufferedWriter.write(DbLogin(clientUsername,clientPassword)+"\n");
            }

            clientHandlers.add(this); // Добавляем пользователя в массив

            // Представляет собой объект клиентского обработчика, поэтому мы передаем его(this) в массив.


            broadcastMessage("SERVER: " + clientUsername + " has entered the chat");
            // Отправляем сообщение всем пользователям, что подключился новый пользователь

        } catch (IOException | SQLException e) { // Ловим ошибку ввода-вывода
            closeEverything(socket, bufferedReader, bufferedWriter); // Если поймали ее, закрываем соединение
        }
    }

    public String DbLogin(String clientUsername, String clientPassword) throws IOException, SQLException {
        try {
            DatabaseHandler dbHandler = new DatabaseHandler();
            ResultSet result = dbHandler.getUsername(clientUsername, clientPassword);


            while (result.next()) {
                System.out.println("CATCH");
                return "found";
            }
        }
        catch (NullPointerException e)
        {
            return ("NULL");

        }
        return null;
    }
        

    @Override
    public void run() { // Мы реализовали интерфейс Runnable, теперь же мы должны переопределить его метод
        // Мы будем прослушивать сообщения. Прослушивание сообщений - "блокирующая" операция,
        // тоесть программа будет зависать, до завершения операции. Если бы мы не использовали дополнительный поток,
        // то наша программа зависала бы в ожидании сообщений от пользователя.
        // А мы также хотим иметь возможность отправлять сообщения независимо от других пользователей.

        DatabaseHandler dbHandler = new DatabaseHandler(); // База данных
        LoggerCrypt crypt = new LoggerCrypt(); // Шифрование

        String messageFromClient;

        while (socket.isConnected()) { // Пока мы подключены
            try {
                messageFromClient = bufferedReader.readLine(); // Слушаем сообщения пользователя
                broadcastMessage(messageFromClient); // Отправляем сообщение всем пользователям

                dbHandler.logMessage(clientUsername, crypt.encode(messageFromClient)); // Логируем зашифрованное сообщение пользователя

            } catch (IOException e) { // Ловим ошибку ввода-вывода
                closeEverything(socket, bufferedReader, bufferedWriter); // Если поймали ее, закрываем соединение
                break; // Без этого break, у нас будет снова и снова крутиться цикл
            }
        }
    }

    public void broadcastMessage(String messageToSend) { // Отправка сообщения всем пользователям


        for (ClientHandler clientHandler : clientHandlers) { // Цикл for each. Перебираем каждого пользователя
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)) { // Отправляем сообщение всем, кроме себя
                    clientHandler.bufferedWriter.write('\n' + messageToSend); // Отправляем сообщение пользователю
                    clientHandler.bufferedWriter.newLine(); // Говорит: "Брат, я отправил сообщение, не нужно больше ожидать текст".
                    clientHandler.bufferedWriter.flush(); // Очищаем буфер
                }
            } catch (IOException e) { // Ловим ошибку ввода-вывода
                closeEverything(socket, bufferedReader, bufferedWriter); // Если поймали ее, закрываем соединение
            }
        }
    }

    public void removeClientHandler() { // Если клиент отключился, мы его удаляем, потому, что нам больше не нужно отправлять ему сообщения

        clientHandlers.remove(this); // Представляет собой объект клиентского обработчика. Удаляем пользователя из массива.
        broadcastMessage("SERVER: " + clientUsername + " has left the chat!"); // Отправляем всем сообщение, что пользователь вышел.
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        //Используется, для закрытия соединения(socket) и потоков(BufferedReader, BufferedWriter)

        removeClientHandler(); // Выпиливаем пользователя из массива
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

    void demonstateHandlers() throws IOException {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.bufferedWriter.write("%"+clientHandler.clientUsername+"&");

        }
    }
}
