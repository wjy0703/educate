package cn.com.educate.core.utils;

/**
 * @author wjy
 * @since 2011-08-30
 */
public class BytesUtil {
  public static int toInt(byte[] bytes) {
    int result = 0;
    for (int i = 0; i < 4; i++) {
      result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
    }
    return result;
  }

  public static short toShort(byte[] bytes) {
    return (short) ( ( ( - (short) Byte.MIN_VALUE + (short) bytes[0]) << 8) -
                    (short) Byte.MIN_VALUE + (short) bytes[1]);
  }

  public static byte[] toBytes(int value) {
    byte[] result = new byte[4];
    for (int i = 3; i >= 0; i--) {
      result[i] = (byte) ( (0xFFl & value) + Byte.MIN_VALUE);
      value >>>= 8;
    }
    return result;
  }

  public static byte[] toBytes(short value) {
    byte[] result = new byte[2];
    for (int i = 1; i >= 0; i--) {
      result[i] = (byte) ( (0xFFl & value) + Byte.MIN_VALUE);
      value >>>= 8;
    }
    return result;
  }
}
