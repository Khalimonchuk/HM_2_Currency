package com.lits.maksymuk;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        String text = "Hello World";
        String key = "AMW-DFA-00000002"; // 128 bit key

        PasswordEncoder passwordEncoder = new PasswordEncoder(key);

        System.out.println(passwordEncoder.encode("qxtr2019"));
    }

    public static class PasswordEncoder {

        private String key;
        private Cipher cipher = Cipher.getInstance("AES");
        private Key aesKey;

        public PasswordEncoder(String key) throws NoSuchPaddingException, NoSuchAlgorithmException {
            this.key = key;
            this.aesKey = new SecretKeySpec(key.getBytes(), "AES");
        }

        public String encode(String message) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        }

        public String decode(String message) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] encodedBytesMessage = Base64.getDecoder().decode(message);
            String decrypted = new String(cipher.doFinal(encodedBytesMessage));
            return decrypted;
        }
    }
}
