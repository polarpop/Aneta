package com.aneta.commons.utils;

import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class SHA1HashUtils {

  public static String sign(String policy, File privateKey) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException {
    Signature sign = Signature.getInstance("SHA1withRSA");
    FileInputStream reader = new FileInputStream(privateKey.getPath());
    byte[] pkContents = reader.readAllBytes();
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkContents);
    RSAPrivateKey rsa = (RSAPrivateKey) keyFactory
            .generatePrivate(keySpec);
    sign.initSign(rsa);
    sign.update(policy.getBytes());

    byte[] signature = sign.sign();

    return Base64.getEncoder().encodeToString(signature);
  }
}
