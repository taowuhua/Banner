package com.ryx.credit.tnh.utils;

/**
 */
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class CryptoUtils {

    private final static byte[] MSTKEY0 = { 0x36, 0x20, 0x18, 0x19, 0x32, 0x34, 0x46, 0x48 };
    private final static byte[] MSTKEY = { (byte) 0xA8, 0x73, (byte) 0xCA, (byte) 0xA0, (byte) 0x9E, 0x7E, 0x70, 0x29,
            0x50, (byte) 0xAA, 0x2B, 0x36, (byte) 0xC3, (byte) 0xCB, (byte) 0xB9, 0x19 };


    private String transDate,transTime;
    private long transLog;
    private boolean bUpdateLogNo = true;

    private static CryptoUtils instance = null;

    private CryptoUtils(){

    }

    public static CryptoUtils getInstance(){
        if(instance == null)
            instance = new CryptoUtils();

        return instance;
    }

    public String getTransTime() {
        if (bUpdateLogNo) {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("HHmmss");
            transDate = sDateFormat.format(new java.util.Date());
            return transDate;
        } else {
            return transDate;
        }
    }

    public String getTransDate() {
        if (bUpdateLogNo) {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
            transTime = sDateFormat.format(new java.util.Date());
            return transTime;
        } else {
            return transTime;
        }
    }
    public String getyyMMddTransDate() {
    	if (bUpdateLogNo) {
    		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyMMdd");
    		transTime = sDateFormat.format(new java.util.Date());
    		return transTime;
    	} else {
    		return transTime;
    	}
    }

    public void setTransLogNo(long logNo){
        this.transLog = logNo;
    }

    public String getTransLogNo() {

        String sResult = "";

        if (bUpdateLogNo) {
            transLog++;

            if (transLog < 0)
                transLog = -transLog;
        }
        sResult = Long.toString(transLog);

        while (sResult.length() < 6)
            sResult = "0" + sResult;
        sResult = sResult.substring(sResult.length() - 6, sResult.length());
        return sResult;
    }

    public void setTransLogUpdate(boolean isNeedUpdate)
    {
        this.bUpdateLogNo = isNeedUpdate;
    }

    public  String EncodeDigest(String str) {
        String sResult = "";
        try {
            String newSign = EncodeDigest(URLEncoder.encode(str, "UTF-8").getBytes("UTF-8"));
            sResult += "<sign>" + newSign + "</sign>";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sResult;
    }


    public String EncodeDigest(byte[] buf) {
        String sResult = "";
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(buf);
            sResult = toHexString(algorithm.digest(), "").toUpperCase();
            return sResult;
        } catch (NoSuchAlgorithmException e) {
            // Log.v("he--------------------------------ji", "toMd5(): " + e);
            // throw new RuntimeException(e);
            return null;
        }
    }



    public String encryptAES(byte[] key, byte[] in){
        String sResult = "";
        try {
            SecretKeySpec skeySpec_encrypt = new SecretKeySpec(key, "AES");
            Cipher cipher_encrypt = Cipher.getInstance("AES");
            cipher_encrypt.init(Cipher.ENCRYPT_MODE, skeySpec_encrypt);
            byte[] encrypted = cipher_encrypt.doFinal(in);
            sResult = toHexString(encrypted, "").toUpperCase();
        } catch (Exception e) {
            //String s = e.getMessage();
            e.printStackTrace();
        }
        return sResult;
    }

    public String decryptAES(byte[] key, byte[] in){
        String sResult = "";
        try {
            SecretKeySpec skeySpec_decrypt = new SecretKeySpec(key, "AES");
            Cipher cipher_decrypt = Cipher.getInstance("AES");
            cipher_decrypt.init(Cipher.DECRYPT_MODE, skeySpec_decrypt);
            byte[] decrypted = cipher_decrypt.doFinal(in);
            sResult = toString(decrypted, ""); //.toUpperCase();
        } catch (Exception e) {
            //String s = e.getMessage();
            e.printStackTrace();
        }
        return sResult;
    }

    public byte[] bytesToHex(byte[] bufin) {
        byte[] bufout = new byte[bufin.length / 2];
        for (int i = 0; i < bufin.length / 2; i++) {
            if (bufin[2 * i] >= 0x30 && bufin[2 * i] <= 0x39)
                bufout[i] = (byte) ((bufin[2 * i] & 0x0F) * 16);
            if (bufin[2 * i] >= 'a' && bufin[2 * i] <= 'f')
                bufout[i] = (byte) ((bufin[2 * i] - 'a' + 10) * 16);
            if (bufin[2 * i] >= 'A' && bufin[2 * i] <= 'F')
                bufout[i] = (byte) ((bufin[2 * i] - 'A' + 10) * 16);

            if (bufin[2 * i + 1] >= 0x30 && bufin[2 * i + 1] <= 0x39)
                bufout[i] += (byte) (bufin[2 * i + 1] & 0x0F);
            if (bufin[2 * i + 1] >= 'a' && bufin[2 * i + 1] <= 'f')
                bufout[i] += (byte) (bufin[2 * i + 1] - 'a' + 10);
            if (bufin[2 * i + 1] >= 'A' && bufin[2 * i + 1] <= 'F')
                bufout[i] += (byte) (bufin[2 * i + 1] - 'A' + 10);
        }
        return bufout;
    }

    private String toHexString(byte[] bytes, String separator) {
        String s1 = "", s2 = "";
        for (int i = 0; i < bytes.length; i++) {
            s1 = Integer.toHexString(0xFF & bytes[i]);
            if (s1.length() == 1) {
                s2 += "0";
            }
            s2 += s1;
            s2 += separator;
        }
        return s2;
    }

    private String toString(byte[] bytes, String separator) {
        String s1 = "", s2 = "";
        for (int i = 0; i < bytes.length; i++) {
            s1 =  String.valueOf((char)bytes[i]);
            s2 += s1;
            s2 += separator;
        }
        return s2;
    }




}

