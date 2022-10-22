package org.jd.demo.vertx.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Util {

  private MD5Util() {}

  public static BigInteger generatePK(String oneId) {
    return new BigInteger(md5(oneId).substring(0, 16), 16);
  }

  public static String md5(String oneId) {
    String digest = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] hash = md.digest(oneId.getBytes());

      //converting byte array to Hexadecimal String
      StringBuilder sb = new StringBuilder(2 * hash.length);
      for (byte b : hash) {
        sb.append(String.format("%02x", b & 0xff));
      }

      digest = sb.toString();

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return digest;
  }

}