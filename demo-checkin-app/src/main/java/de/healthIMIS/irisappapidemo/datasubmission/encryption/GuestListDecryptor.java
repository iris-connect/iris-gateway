package de.healthIMIS.irisappapidemo.datasubmission.encryption;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
public class GuestListDecryptor implements Decryptor {

    private final String encryptedSecretKeyB64;

    private SecretKey secretKey;

    private byte[] iv;

    private final String cipherText;

    private String clearText;

    private String privateKey;

    public GuestListDecryptor(String encryptedSecretKeyB64, String privateKey, String cipherText) {
        this.encryptedSecretKeyB64 = encryptedSecretKeyB64;
        this.cipherText = cipherText;
        this.privateKey = privateKey.replaceAll(System.lineSeparator(), "");
        this.privateKey = privateKey.replaceAll("\\n", "");
    }

    public String decrypt() {

        log.info("Decrypting...");
        try {
            decryptEncryptedSecretKeyB64();

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            byte[] cipherBytes = Base64.getDecoder().decode(cipherText);

            iv = Arrays.copyOfRange(cipherBytes, 0, 16);

            cipherBytes = Arrays.copyOfRange(cipherBytes, 16, cipherBytes.length);

            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            clearText = new String(cipher.doFinal(cipherBytes), StandardCharsets.UTF_8);

            return clearText;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void decryptEncryptedSecretKeyB64() {
        try {
            PrivateKey key = getPrivateKey();
            Cipher decoder = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
            decoder.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedKey = decoder.doFinal(Base64.getDecoder().decode(encryptedSecretKeyB64));
            secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PrivateKey getPrivateKey() {
        try {
            byte[] encoded = Base64.getDecoder().decode(privateKey);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
