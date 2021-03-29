package de.healthIMIS.irisappapidemo.datasubmission.encryption;

import de.healthIMIS.irisappapidemo.datasubmission.model.dto.GuestListDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;
import java.util.Base64;

@Builder
@Slf4j
public class GuestListEncryptor implements Encryptor {

    private final GuestListDto guestList;

    private final String givenPublicKey;

    private SecretKey secretKey;

    public static SecretKey getAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

    public static byte[] generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return iv;
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
            byte[] cipherText = cipher.doFinal(guestList.toString().getBytes());

            return Base64.getEncoder()
                    .encodeToString(combineIVAndData(iv, cipherText));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getSecretKeyBase64() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    private byte[] combineIVAndData(byte[] iv, byte[] encryptedData) {
        byte[] destination = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, destination, 0, iv.length);
        System.arraycopy(encryptedData, 0, destination, iv.length, encryptedData.length);
        return destination;
    }
}
