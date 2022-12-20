import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class LoggerCrypt {
    private PrivateKey privateKey;// приватный ключ
    private PublicKey publicKey;// открытый ключ

    public LoggerCrypt() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");//создаем экземпляр класс генератор с помощью метода getInstance получаем генератор RSA
            generator.initialize(2048);//устанавливаем размер ключа
            KeyPair pair = generator.generateKeyPair();//создаем пару ключей
            privateKey = pair.getPrivate();//используя метод getPrivate получаем приватный ключ
            publicKey = pair.getPublic();//используя метод getPublic получаем публичный ключ
        } catch (Exception ignored) {
        }
    }

    public String encode(String message) {//метод для кодировки сообщения по публичному ключу
        return Base64.getEncoder()//вызываем класс BASE64 для кодировки
                .encodeToString(//переводим это все в строку
                        crypt(message.getBytes(), Cipher.ENCRYPT_MODE, publicKey)//шифруем по сообщению в байтах,криптмоду и ключу
                );
    }

    public String decode(String encryptedMessage) {//метод для раскодировки сообщения по приватному ключу
        byte[] decodedMessage = Base64.getDecoder().decode(encryptedMessage);//массив байтов там расшифруется зашифрованное  сообщение в байткод
        return new String(
                crypt(decodedMessage, Cipher.DECRYPT_MODE, privateKey)
                , StandardCharsets.UTF_8
        );//возвращаем расшифрованное сообщение в виде строки
    }

    private byte[] crypt(byte[] messageToBytes, int cryptMode, Key key) {//метод который шифрует сообщение и возврщает зашифрованный/расшифрованный массив байтов
        byte[] cryptedMessage = new byte[]{1};//будущий массив шифрованных байтов
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");//создаем экземпляр шифр, выбираем нужную кодировку
            cipher.init(cryptMode, key);//инициализация шифра по ключу
            cryptedMessage = cipher.doFinal(messageToBytes);//сообщение шифруется/расшифруется в зависимости от того как оно инициализировано
        } catch (Exception ignored) {}
        return cryptedMessage;// возвращаем сообщение
    }
}