package org.jd.demo;

public class BinaryTest {

  public static void main(String[] args) {
    System.out.println("Byte 转无符号 int：" + byteToUnsignedInt((byte) 0b11111111));
    System.out.println("Byte 转字符串：" + byteToBinaryString((byte) 120));
  }

  /**
   * Byte 转无符号 int
   */
  public static int byteToUnsignedInt(byte b) {
    return Byte.toUnsignedInt(b);
  }

  /**
   * Byte 转字符串
   */
  public static String byteToBinaryString(byte b) {
    return Integer.toBinaryString((b & 0xFF) + 0x100).substring(1);
  }

}
