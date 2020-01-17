/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.xml.bind.DatatypeConverter;


public class Protector {

    private static final char[] PASSWORD = "There was a time when I was in a hurry as you are I was like you There was a day when I just had to tell my point of view I was like you".toCharArray();
    private static final byte[] SALT = {
        (byte) 0xae, (byte) 0x11, (byte) 0x13, (byte) 0xaa,
        (byte) 0xce, (byte) 0x10, (byte) 0x10, (byte) 0xac
    };
  


    public static String encrypt(String property) throws GeneralSecurityException, UnsupportedEncodingException {
       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
       SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");

        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return base64Encode(pbeCipher.doFinal(property.getBytes("UTF-8")));
    }
  public static String encryptLocal(String property, String machineID) throws GeneralSecurityException, UnsupportedEncodingException {
       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        char[] localPass = machineID.toCharArray();
       SecretKey key = keyFactory.generateSecret(new PBEKeySpec(localPass));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
 
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return base64Encode(pbeCipher.doFinal(property.getBytes("UTF-8")));
    }
    private static String base64Encode(byte[] bytes) {
      
        return DatatypeConverter.printBase64Binary(bytes);
    }

    public static String decrypt(String property) throws GeneralSecurityException, IOException {
       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
       SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
 
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }
     public static String decryptLocal(String property, String machineID) throws GeneralSecurityException, IOException {
       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
         char[] localPass = machineID.toCharArray();
       SecretKey key = keyFactory.generateSecret(new PBEKeySpec(localPass));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
       pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private static byte[] base64Decode(String property) throws IOException {
        // NB: This class is internal, and you probably should use another impl
        return DatatypeConverter.parseBase64Binary(property);
    }

}