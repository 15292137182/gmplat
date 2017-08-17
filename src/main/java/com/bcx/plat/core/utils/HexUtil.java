package com.bcx.plat.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * 加密工具
 *
 * Create By HCL at 2017/8/17
 *
 * Author wzp
 */
public class HexUtil {

  private final static String HEX_NUMS_STR = "0123456789ABCDEF";
  private final static Integer SALT_LENGTH = 12;

  /**
   * 禁止使用 new 的方式构造类
   */
  private HexUtil() {
  }

  /**
   * 将16进制字符串转换成数组
   *
   * @return byte[]
   * @author jacob
   */
  public static byte[] hexStringToByte(String hex) {
    /*
     * len为什么是hex.length() / 2 ? 首先，hex是一个字符串，里面的内容是像16进制那样的char数组
		 * 用2个16进制数字可以表示1个byte，所以要求得这些char[]可以转化成什么样的byte[]，首先可以确定的就是长度为这个char[]
		 * 的一半
		 */
    int len = (hex.length() / 2);
    byte[] result = new byte[len];
    char[] hexChars = hex.toCharArray();
    for (int i = 0; i < len; i++) {
      int pos = i * 2;
      result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4 | HEX_NUMS_STR
          .indexOf(hexChars[pos + 1]));
    }
    return result;
  }

  /**
   * 将数组转换成16进制字符串
   *
   * @return String
   * @author jacob
   */
  public static String byteToHexString(byte[] salt) {
    StringBuilder hexString = new StringBuilder();
    for (byte aSalt : salt) {
      String hex = Integer.toHexString(aSalt & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      hexString.append(hex.toUpperCase());
    }
    return hexString.toString();
  }

  /**
   * 密码验证
   *
   * @param pwd 用户输入密码
   * @param dbPWD 数据库保存的密码
   */
  public static boolean validPasswd(String pwd, String dbPWD)
      throws NoSuchAlgorithmException, UnsupportedEncodingException {
    byte[] pwIndb = hexStringToByte(dbPWD);
    // 定义salt
    byte[] salt = new byte[SALT_LENGTH];
    System.arraycopy(pwIndb, 0, salt, 0, SALT_LENGTH);
    // 创建消息摘要对象
    MessageDigest md = MessageDigest.getInstance("MD5");
    // 将盐数据传入消息摘要对象
    md.update(salt);
    md.update(pwd.getBytes("UTF-8"));
    byte[] digest = md.digest();
    // 声明一个对象接收数据库中的口令消息摘要
    byte[] digestIndb = new byte[pwIndb.length - SALT_LENGTH];
    // 获得数据库中口令的摘要
    System.arraycopy(pwIndb, SALT_LENGTH, digestIndb, 0, digestIndb.length);
    // 比较根据输入口令生成的消息摘要和数据库中的口令摘要是否相同
    // 口令匹配相同
    return Arrays.equals(digest, digestIndb);
  }

  /**
   * 获得md5之后的16进制字符
   *
   * @param pwd 用户输入密码字符
   * @return String md5加密后密码字符
   */
  public static String getEncryptedPwd(String pwd)
      throws NoSuchAlgorithmException, UnsupportedEncodingException {
    //拿到一个随机数组，作为盐
    byte[] _pwd;
    SecureRandom sc = new SecureRandom();
    byte[] salt = new byte[SALT_LENGTH];
    sc.nextBytes(salt);

    //声明摘要对象，并生成
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(salt);
    md.update(pwd.getBytes("UTF-8"));
    byte[] digest = md.digest();

    _pwd = new byte[salt.length + digest.length];
    System.arraycopy(salt, 0, _pwd, 0, SALT_LENGTH);
    System.arraycopy(digest, 0, _pwd, SALT_LENGTH, digest.length);
    return byteToHexString(_pwd);
  }
}
