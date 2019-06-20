package com.caetp.digiex.utli.encryption;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

public class RSA {

	public static PublicKey getPublicKey(String filename) throws Exception {
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();
		System.out.println(new String(keyBytes));
		X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decodeBase64(keyBytes));
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}

	public static PrivateKey getPrivateKey(String filename) throws Exception {
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	private static void generatePublicKeyAndPrivateKey() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			SecureRandom secureRandom = new SecureRandom(new Date().toString().getBytes());
			keyPairGenerator.initialize(1024, secureRandom);
			KeyPair keyPair = keyPairGenerator.genKeyPair();
			String publicKeyFilename = "D:/publicKeyFile.pem";
			byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
			FileOutputStream fos = new FileOutputStream(publicKeyFilename);
			fos.write(Base64.encodeBase64(publicKeyBytes));
			fos.close();
			String privateKeyFilename = "D:/privateKeyFile";
			byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
			fos = new FileOutputStream(privateKeyFilename);
			fos.write(privateKeyBytes);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] encrypt(String input) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			RSAPublicKey pubKey = (RSAPublicKey) getPublicKey("E:/company/RSA公钥私钥/publicKeyFile");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] cipherText = cipher.doFinal(input.getBytes());
			// 加密后的东西
//			System.out.println("cipher: " + new String(cipherText));
			return cipherText;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String decrypt(byte[] encrypted) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			RSAPrivateKey privKey = (RSAPrivateKey) getPrivateKey("E:/company/RSA公钥私钥/privateKeyFile");
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			byte[] plainText = cipher.doFinal(encrypted);
			// 解密后的东西
			// System.out.println("plain : " + new String(plainText));
			return new String(plainText);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		 generatePublicKeyAndPrivateKey();
		System.out.println(encrypt("MXxoajZuN2FkYmpzfDE0OTI1OTI4NjM2ODg=").toString());
		System.out.println(decrypt(encrypt("MXxoajZuN2FkYmpzfDE0OTI1OTI4NjM2ODg=")));
	}
}