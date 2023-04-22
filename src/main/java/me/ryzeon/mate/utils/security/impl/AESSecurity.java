package me.ryzeon.mate.utils.security.impl;

import me.ryzeon.mate.utils.security.SecurityProcess;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/22/23 @ 04:29
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public class AESSecurity implements SecurityProcess {

    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static final String ALGORITHM = "AES";

    /**
     * It takes a string, converts it to bytes, hashes it with SHA-1, truncates it to 16 bytes, and
     * then uses it as a key for AES
     *
     * @param myKey The key that you want to use to encrypt/decrypt the data.
     */
    public void prepareSecreteKey(String myKey) {
        MessageDigest sha;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String encrypt(String text, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        prepareSecreteKey(key);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)));
    }
    @Override
    public String decrypt(String text, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        prepareSecreteKey(key);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(text)));
    }


}
