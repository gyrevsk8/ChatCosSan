import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class LoggerCrypt {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public LoggerCrypt() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();
        } catch (Exception ignored) {
        }
    }

    public String encode(String message) {
        return Base64
                .getEncoder()
                .encodeToString(
                        crypt(message.getBytes(), Cipher.ENCRYPT_MODE, publicKey)
                );
    }

    public String decode(String encryptedMessage) {
        byte[] decodedMessage = Base64.getDecoder().decode(encryptedMessage);
        return new String(
                crypt(decodedMessage, Cipher.DECRYPT_MODE, privateKey)
                , StandardCharsets.UTF_8
        );
    }

    private byte[] crypt(byte[] messageToBytes, int cryptMode, Key key) {
        byte[] cryptedMessage = new byte[]{1};
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            cipher.init(cryptMode, key);
            cryptedMessage = cipher.doFinal(messageToBytes);
        } catch (Exception ignored) {}
        return cryptedMessage;
    }
}