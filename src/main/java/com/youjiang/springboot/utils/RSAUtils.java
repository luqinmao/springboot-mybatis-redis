package com.youjiang.springboot.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.crypto.Cipher;


public class RSAUtils {

	public static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJH+fyv1rj+pS0oafslLtWSIkNBAc13JZipTVLKz4kq0Nsm199kDsHpLs4bGbLBbk2FjggcWdhtwJdZLd+kst1Egxjncl8P0jzZLLqOMro40Hhvsb5bd43bG8fkhfwoPs3BpdawEI1qVzlMO1AqYi6vob+9kGeRBnX9xmG+cJGQNAgMBAAECgYAdeWLVTilaouQXVtgH7af2XvfVg12HLrTYkLBFz7k82btB5+Hcxzjmx8e7yu2AgUx02fETWz6G9ZI9/Eh+I8/yW90R+z7IXmGE23wDzQa+28BmvLZeDhFDMDeRJerii7pkgQwKf5rvT0K2fN+3w7C84TlSRL+WBnohTyFfocvzgQJBAMw3SU4slrMobBxswqs38yopKto+BrOt1jGDZlcFIOY0d/Ya29qlexCjm8oFo8Q1+QxzyLvTNnacQsal7MEA8C0CQQC3A7rvfqEmWsAaHW4V1EEswn2dyn+JPkBzeuQuQXtKGXHGy1VdLrhV7834+brfFTL3atCc+riWljSJTnDDCc9hAkA2ikFytmfdG1ucT9PB5KR1yx7dwJkLnc7J1H0dmooJ0VOEj/HNuEpgzEFMOCtfuMZ18+pVd3hhPyR6Z1AOz9BVAkBT40nUbV7AjehwD/1d1CTrt1qM8Tz0U6yHQlVFPo6csH0OejVuuPOVsI3hCdc2Wik6b6Of0Oy7Wa5LIcFeJSkBAkA7yzRva7jWFDpQB9BaaT/L15TKN3iWluGpLpX8xc4a2EmddCKEv381D7f2rLpLInUD87EoFZZQtj5XMR7ImQtl";
	private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCR/n8r9a4/qUtKGn7JS7VkiJDQQHNdyWYqU1Sys+JKtDbJtffZA7B6S7OGxmywW5NhY4IHFnYbcCXWS3fpLLdRIMY53JfD9I82Sy6jjK6ONB4b7G+W3eN2xvH5IX8KD7NwaXWsBCNalc5TDtQKmIur6G/vZBnkQZ1/cZhvnCRkDQIDAQAB";

	/** RSA最大加密明文大小 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/** RSA最大解密密文大小 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/** 加密算法RSA */
	private static final String KEY_ALGORITHM = "RSA";

	/**
	 * 生成公钥和私钥
	 * 
	 * @throws Exception
	 * 
	 */
	public static void getKeys() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		String publicKeyStr = getPublicKeyStr(publicKey);
		String privateKeyStr = getPrivateKeyStr(privateKey);

		System.out.println("公钥\r\n" + publicKeyStr);
		System.out.println("私钥\r\n" + privateKeyStr);
	}

	/**
	 * 使用模和指数生成RSA公钥
	 * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
	 * /None/NoPadding】
	 * 
	 * @param modulus
	 *            模
	 * @param exponent
	 *            公钥指数
	 * @return
	 */
	public static RSAPublicKey getPublicKey(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 使用模和指数生成RSA私钥
	 * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
	 * /None/NoPadding】
	 * 
	 * @param modulus
	 *            模
	 * @param exponent
	 *            指数
	 * @return
	 */
	public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 公钥加密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data) throws Exception {
		byte[] dataByte = data.getBytes();
		byte[] keyBytes = Base64.getDecoder().decode(PUBLIC_KEY);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		// Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = dataByte.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(dataByte, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(dataByte, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return Base64.getEncoder().encodeToString(encryptedData);
	}


	/**
	 * 私钥解密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String data) throws Exception {
		byte[] encryptedData = Base64.getDecoder().decode(data);
		byte[] keyBytes = Base64.getDecoder().decode(PRIVATE_KEY);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		// Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher
						.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher
						.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return new String(decryptedData);
	}

	


	/**
	 * 获取模数和密钥
	 * 
	 * @return
	 */
	public static Map<String, String> getModulusAndKeys() {

		Map<String, String> map = new HashMap<String, String>();

		try {
			InputStream in = RSAUtils.class
					.getResourceAsStream("/rsa.properties");
			Properties prop = new Properties();
			prop.load(in);

			String modulus = prop.getProperty("modulus");
			String publicKey = prop.getProperty("publicKey");
			String privateKey = prop.getProperty("privateKey");

			in.close();

			map.put("modulus", modulus);
			map.put("publicKey", publicKey);
			map.put("privateKey", privateKey);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
		try {
			byte[] buffer = Base64.getDecoder().decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	/**
	 * 从字符串中加载私钥<br>
	 * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
	 * 
	 * @param privateKeyStr
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey loadPrivateKey(String privateKeyStr)
			throws Exception {
		try {
			byte[] buffer = Base64.getDecoder().decode(privateKeyStr);
			// X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}

	public static String getPrivateKeyStr(PrivateKey privateKey)
			throws Exception {
		return new String( Base64.getEncoder().encode(privateKey.getEncoded()));
	}

	public static String getPublicKeyStr(PublicKey publicKey) throws Exception {
		return new String( Base64.getEncoder().encode(publicKey.getEncoded()));
	}
}