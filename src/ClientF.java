import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * Интерфейс для использования класса-обработчика клиентов
 * Является расширенной версией интерфейса Runnable
 */
public interface ClientF {
    public void run();
    public void broadcastMessage(String messageToSend) ;
    public void removeClientHandler() ;
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) ;
}
