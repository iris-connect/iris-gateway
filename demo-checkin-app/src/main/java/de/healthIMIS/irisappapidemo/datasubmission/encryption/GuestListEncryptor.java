package de.healthIMIS.irisappapidemo.datasubmission.encryption;

import de.healthIMIS.irisappapidemo.datasubmission.model.dto.GuestListDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Builder
@Slf4j
public class GuestListEncryptor implements Encryptor {

    private final GuestListDto guestList;

    private final String givenPublicKey;

    private SecretKey secretKey;

    private SecretKey getAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

    private byte[] generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    private PublicKey getPublicKey(String givenPublicKey) {
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");

            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(givenPublicKey.replaceAll("\\n", "")));

            return (RSAPublicKey) kf.generatePublic(keySpecX509);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String encrypt() {

        log.info("Encrypting...");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            secretKey = getAESKey();

            byte[] iv = generateIv();
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] cipherText = cipher.doFinal(guestList.asJson().getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder()
                    .encodeToString(combineIVAndData(iv, cipherText));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getSecretKeyBase64() {

        try {
            PublicKey key = getPublicKey(givenPublicKey);
            Cipher encoder = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
            encoder.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipherText = encoder.doFinal(secretKey.getEncoded());
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] combineIVAndData(byte[] iv, byte[] encryptedData) {
        byte[] destination = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, destination, 0, iv.length);
        System.arraycopy(encryptedData, 0, destination, iv.length, encryptedData.length);
        return destination;
    }
}
