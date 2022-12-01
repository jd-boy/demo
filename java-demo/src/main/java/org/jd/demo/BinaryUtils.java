package org.jd.demo;

public class BinaryUtils {

  public static int byteToUnsignedInt(byte b) {
    return Byte.toUnsignedInt(b);
  }

  public static String byteToBinaryString(byte b) {
    return Integer.toBinaryString((b & 0xFF) + 0x100).substring(1);
  }

}
