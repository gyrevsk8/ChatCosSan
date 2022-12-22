import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

   @org.junit.jupiter.api.Test
    void testsendMessage() {
        Socket socket = new Socket();
        Client client = new Client(socket, "who", "phone", "pass","su");
       assertEquals(null, client.getName());
    }
}