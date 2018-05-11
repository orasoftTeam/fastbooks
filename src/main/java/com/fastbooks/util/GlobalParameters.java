/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.util;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author dell
 */
public class GlobalParameters {
    private @Getter @Setter String appPath = /*;    "C:\\Users\\luis\\Pictures\\fastbooks" "/u01/app/oracle/fastbooks"*/System.getProperty("user.dir");  
    private @Getter @Setter String email = "fastbooks20@gmail.com";
    private @Getter @Setter String pass = "orasoft123";
    private @Getter @Setter String imgBytesSize = "1000000";
    private @Getter @Setter String key = "fastbooks20";

   /* public GlobalParameters() {
        try {
            this.pass = Desencriptar("apfs7EywyAXytc1Zrah34w==");
        } catch (Exception e) {
            System.out.println("com.fastbooks.util.GlobalParameters.<init>()");
            e.printStackTrace();
        }
    }
    
    
     public String encriptar(String texto, String keymod) {

        String secretKey = keymod; //llave para encriptar datos
        String base64EncryptedString = "";

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    public String Desencriptar(String textoEncriptado) throws Exception {

        String secretKey = this.key; //llave para encriptar datos
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }*/
    
}
