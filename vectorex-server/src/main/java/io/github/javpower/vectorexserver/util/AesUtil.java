package io.github.javpower.vectorexserver.util;

import io.github.javpower.vectorexserver.config.VectorRex;
import io.github.javpower.vectorexserver.exception.CommonException;
import io.github.javpower.vectorexserver.response.ServerResponse;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesUtil {

    // AES 密钥（16 字节）
    private static final String SECRET_KEY = VectorRexSpringUtils.getBean(VectorRex.class).getSecretKey();

    // 加密算法
    private static final String ALGORITHM = "AES";

    /**
     * 加密
     *
     * @param data 需要加密的数据
     * @return 加密后的字符串（Base64 编码）
     */
    public static String encrypt(String data) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    /**
     * 解密
     *
     * @param encryptedData 加密后的字符串（Base64 编码）
     * @return 解密后的原始数据
     */
    public static String decrypt(String encryptedData) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw CommonException.create(ServerResponse.createByError("token无效"));
        }
    }
}