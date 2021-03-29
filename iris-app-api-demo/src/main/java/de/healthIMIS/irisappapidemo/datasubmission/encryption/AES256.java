package de.healthIMIS.irisappapidemo.datasubmission.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AES256 {

    public static String encrypt(String strToEncrypt, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
}
