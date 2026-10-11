package be.depairon.espaceclient.eglobal.utils.encryption;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

public class YGEncryption {

    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    public static final String DES_ENCRYPTION_SCHEME = "DES";
    public static final String DEFAULT_ENCRYPTION_KEY = "zert654df3b1d32fhrt61h5rewcxv321uli51hjk jhk";

    private KeySpec keySpec;
    private SecretKeyFactory keyFactory;
    private Cipher cipher;

    private static final String UNICODE_FORMAT = "UTF8";

    public YGEncryption(String encryptionScheme) throws EncryptionException {
        this(encryptionScheme, DEFAULT_ENCRYPTION_KEY);
    }

    public YGEncryption(String encryptionScheme, String encryptionKey)
            throws EncryptionException {
        if (encryptionKey == null)
            throw new IllegalArgumentException("encryption key was null");
        if (encryptionKey.trim().length() < 24)
            throw new IllegalArgumentException(
                    "encryption key was less than 24 characters");

        try {
            byte[] keyAsBytes = encryptionKey.getBytes(UNICODE_FORMAT);
            if (encryptionScheme.equals(DESEDE_ENCRYPTION_SCHEME)) {
                keySpec = new DESedeKeySpec(keyAsBytes);
            } else if (encryptionScheme.equals(DES_ENCRYPTION_SCHEME)) {
                keySpec = new DESKeySpec(keyAsBytes);
            } else {
                throw new IllegalArgumentException("Encryption scheme not supported: "
                        + encryptionScheme);
            }
            keyFactory = SecretKeyFactory.getInstance(encryptionScheme);
            cipher = Cipher.getInstance(encryptionScheme);

        } catch (InvalidKeyException | UnsupportedEncodingException
                | NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new EncryptionException(e);
        }

    }

    public String encrypt(String unencryptedString) throws EncryptionException {
        if (unencryptedString == null || unencryptedString.trim().length() == 0)
            throw new IllegalArgumentException(
                    "unencrypted string was null or empty");
        try {
            SecretKey key = keyFactory.generateSecret(keySpec);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cleartext = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] ciphertext = cipher.doFinal(cleartext);
            return new String(java.util.Base64.getMimeEncoder().encode(ciphertext),
                    StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new EncryptionException(e);
        }
    }

    public String decrypt(String encryptedString) throws EncryptionException {
        if (encryptedString == null || encryptedString.trim().length() <= 0)
            return "";
        try {
            SecretKey key = keyFactory.generateSecret(keySpec);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] cleartext = java.util.Base64.getMimeDecoder().decode(encryptedString);
            ;
            byte[] ciphertext = cipher.doFinal(cleartext);

            return bytes2String(ciphertext);
        } catch (Exception e) {
            throw new EncryptionException(e);
        }
    }

    private static String bytes2String(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        for (byte aByte : bytes) {
            stringBuffer.append((char) aByte);
        }
        return stringBuffer.toString();
    }

    @SuppressWarnings("serial")
    public static class EncryptionException extends Exception {
        public EncryptionException(Throwable t) {
            super(t);
        }
    }
}