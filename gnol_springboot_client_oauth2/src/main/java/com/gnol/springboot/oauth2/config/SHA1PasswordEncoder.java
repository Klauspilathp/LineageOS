package com.gnol.springboot.oauth2.config;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gnol.plugins.core.StringUtil;
import com.gnol.plugins.core.exception.GnolRuntimeException;

/**
 * @Title: SHA1PasswordEncoder
 * @Package: com.gnol.springboot.oauth2.config
 * @author: 吴佳隆
 * @date: 2020年7月11日 上午11:58:15
 * @Description: SHA1 的 PasswordEncoder 加密实现类
 * spring boot security 推荐使用 BCryptPasswordEncoder 实现，但此项目为与 gnol-dubbo 项目使用同一库，所以自定义此 PasswordEncoder。
 */
public class SHA1PasswordEncoder implements PasswordEncoder {
    private static final Logger logger = LoggerFactory.getLogger(SHA1PasswordEncoder.class);
    /**
     * 加密的次数
     */
    private final int hashIterations;

    public SHA1PasswordEncoder() {
        this(1);
    }

    public SHA1PasswordEncoder(int hashIterations) {
        this.hashIterations = hashIterations;
        if (this.hashIterations < 1) {
            throw new IllegalArgumentException("Bad hashIterations");
        }
    }

    /**
     * @param rawPassword   盐值与明文密码
     * @return String       密文密码
     */
    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        if ("userNotFoundPassword".equals(rawPassword)) {
            return rawPassword.toString();
        }
        if (rawPassword.equals(StringUtil.EMPTY)) {
            rawPassword = "&";
        }
        String[] splits = StringUtil.splitPreserveAllTokens(rawPassword.toString(), "&", 2);
        if (splits.length < 2) {
            throw new IllegalArgumentException("rawPassword must contain &");
        }
        String password = splits[1];
        if (password == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        String salt = splits[0];
        byte[] saltBytes = salt == null ? null : salt.getBytes(StandardCharsets.UTF_8);
        byte[] hashedBytes = null;
        try {
            hashedBytes = hash(password.getBytes(StandardCharsets.UTF_8), saltBytes, hashIterations);
        } catch (NoSuchAlgorithmException e) {
            throw new GnolRuntimeException(e.getMessage());
        }
        return Hex.encodeToString(hashedBytes);
    }

    /**
     * Hashes the specified byte array using the given {@code salt} for the specified number of iterations.
     *
     * @param bytes          the bytes to hash
     * @param salt           the salt to use for the initial hash
     * @param hashIterations the number of times the the {@code bytes} will be hashed (for attack resiliency).
     * @return the hashed bytes.
     * @throws NoSuchAlgorithmException if the {@link #getAlgorithmName() algorithmName} is not available.
     */
    protected byte[] hash(byte[] bytes, byte[] salt, int hashIterations) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA1");
        if (salt != null) {
            digest.reset();
            digest.update(salt);
        }
        byte[] hashed = digest.digest(bytes);
        int iterations = hashIterations - 1; //already hashed once above
        //iterate remaining number:
        for (int i = 0; i < iterations; i++) {
            digest.reset();
            hashed = digest.digest(hashed);
        }
        return hashed;
    }

    /**
     * @param rawPassword       明文密码
     * @param encodedPassword   盐值与密文密码
     * @return boolean          匹配成功返回 true
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        if (encodedPassword == null) {
            logger.warn("Empty encoded password");
            return false;
        }
        if (encodedPassword.equals("userNotFoundPassword")) {
            logger.error("No user is found in com.gnol.springboot.oauth2.config.UserDetailsServiceImpl.");
            return false;
        }
        String[] splits = StringUtil.splitPreserveAllTokens(encodedPassword, "&", 2);
        if (splits.length < 2) {
            throw new IllegalArgumentException("encodedPassword must contain &");
        }
        encodedPassword = splits[1];
        if (encodedPassword == null || encodedPassword.length() == 0) {
            logger.warn("Empty encoded password");
            return false;
        }
        return encodedPassword.equals(this.encode(splits[0] + "&" + rawPassword));
    }

}

/**
 * <a href="http://en.wikipedia.org/wiki/Hexadecimal">Hexadecimal</a> encoder and decoder.
 * <p/>
 * This class was borrowed from Apache Commons Codec SVN repository (rev. {@code 560660}) with modifications
 * to enable Hex conversion without a full dependency on Commons Codec.  We didn't want to reinvent the wheel of
 * great work they've done, but also didn't want to force every Shiro user to depend on the commons-codec.jar
 * <p/>
 * As per the Apache 2.0 license, the original copyright notice and all author and copyright information have
 * remained in tact.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Hexadecimal">Wikipedia: Hexadecimal</a>
 * @since 0.9
 */
class Hex {
    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f'};

    /**
     * Encodes the specified byte array to a character array and then returns that character array
     * as a String.
     *
     * @param bytes the byte array to Hex-encode.
     * @return A String representation of the resultant hex-encoded char array.
     */
    public static String encodeToString(byte[] bytes) {
        char[] encodedChars = encode(bytes);
        return new String(encodedChars);
    }

    /**
     * Converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.
     * The returned array will be double the length of the passed array, as it takes two characters to represent any
     * given byte.
     *
     * @param data byte[] to convert to Hex characters
     * @return A char[] containing hexadecimal characters
     */
    public static char[] encode(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }

    /**
     * Converts an array of character bytes representing hexadecimal values into an
     * array of bytes of those same values. The returned array will be half the
     * length of the passed array, as it takes two characters to represent any
     * given byte. An exception is thrown if the passed char array has an odd
     * number of elements.
     *
     * @param array An array of character bytes containing hexadecimal digits
     * @return A byte array containing binary data decoded from
     *         the supplied byte array (representing characters).
     * @throws IllegalArgumentException Thrown if an odd number of characters is supplied
     *                                  to this function
     * @see #decode(char[])
     */
    public static byte[] decode(byte[] array) throws IllegalArgumentException {
        String s = new String(array, StandardCharsets.UTF_8);
        return decode(s);
    }

    /**
     * Converts the specified Hex-encoded String into a raw byte array.  This is a
     * convenience method that merely delegates to {@link #decode(char[])} using the
     * argument's hex.toCharArray() value.
     *
     * @param hex a Hex-encoded String.
     * @return A byte array containing binary data decoded from the supplied String's char array.
     */
    public static byte[] decode(String hex) {
        return decode(hex.toCharArray());
    }

    /**
     * Converts an array of characters representing hexadecimal values into an
     * array of bytes of those same values. The returned array will be half the
     * length of the passed array, as it takes two characters to represent any
     * given byte. An exception is thrown if the passed char array has an odd
     * number of elements.
     *
     * @param data An array of characters containing hexadecimal digits
     * @return A byte array containing binary data decoded from
     *         the supplied char array.
     * @throws IllegalArgumentException if an odd number or illegal of characters is supplied
     */
    public static byte[] decode(char[] data) throws IllegalArgumentException {
        int len = data.length;
        if ((len & 0x01) != 0) {
            throw new IllegalArgumentException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }
        return out;
    }

    /**
     * Converts a hexadecimal character to an integer.
     * 
     * @param ch    A character to convert to an integer digit
     * @param index The index of the character in the source
     * @return An integer
     * @throws IllegalArgumentException if ch is an illegal hex character
     */
    protected static int toDigit(char ch, int index) throws IllegalArgumentException {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new IllegalArgumentException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

}