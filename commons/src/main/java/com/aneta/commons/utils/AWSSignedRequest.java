package com.aneta.commons.utils;


import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils.Protocol;
import com.aneta.commons.models.aws.SignedCookie;

import java.io.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

/**
 * Utility class to create a signed url for CloudFront
 *
 * @author Doug Schamberg
 *
 * @see com.amazonaws.services.cloudfront.CloudFrontUrlSigner
 */
public class AWSSignedRequest {
  private String domain;
  private File privateKeyFile;
  private String keyPairId;


  public AWSSignedRequest() {}

  /**
   * Creates a new AWSSignedRequest.
   * @param domain The domain you are serving the content from.
   * @param keyPairId The keyPairId from CloudFront.
   * @param privateKeyFile The privateKeyFile, you can either create this
   *                       or add it from an existing CloudFront privateKeyFile.
   * @see com.amazonaws.services.cloudfront.CloudFrontUrlSigner
   * */
  public AWSSignedRequest(String domain, String keyPairId, File privateKeyFile) {
    this.domain = domain;
    this.keyPairId = keyPairId;
    this.privateKeyFile = privateKeyFile;
  }

  /**
   * Adds the resource domain to the AWSSignedRequest.
   * @param domain The domain you are serving the content from.
   * */
  public AWSSignedRequest addDomain(String domain) {
    this.domain = domain;
    return this;
  }
  /**
   * Adds the resource domain to the AWSSignedRequest.
   * @param keyPairId The keyPairId from CloudFront.
   * @see <a href="https://docs.aws.amazon.com/AmazonCloudFront/latest/DeveloperGuide/private-content-trusted-signers.html#private-content-creating-cloudfront-key-pairs-procedure" rel="noopener">Private Key File Creation</a>
   * */
  public AWSSignedRequest addKeyPairId(String keyPairId) {
    this.keyPairId = keyPairId;
    return this;
  }
  /**
   * Adds the resource domain to the AWSSignedRequest.
   * @param privateKeyFile The privateKeyFile, you can either create this
   *                       or add it from an existing CloudFront privateKeyFile.
   * @see <a href="https://docs.aws.amazon.com/AmazonCloudFront/latest/DeveloperGuide/private-content-trusted-signers.html#private-content-creating-cloudfront-key-pairs-procedure" rel="noopener">Private Key File Creation</a>
   * */
  public AWSSignedRequest addPrivateKeyFile(File privateKeyFile) {
    this.privateKeyFile = privateKeyFile;
    return this;
  }
  /**
   * Creates a signed url to a CloudFront resource.
   * @param s3ObjectKeyName The S3 object's key name.
   * @param protocol The protocol you will be using. @defaults Protocol.http
   * */
  public String createSignedUrl(
          String s3ObjectKeyName,
          Protocol protocol) throws InvalidKeySpecException, IOException {
    if (privateKeyFile == null || keyPairId == null || domain == null) return null;
    Date date = new Date();
    Protocol prt= protocol;

    if (prt == null) {
      prt = Protocol.https;
    }
    return CloudFrontUrlSigner.getSignedURLWithCannedPolicy(prt, domain,
            privateKeyFile, s3ObjectKeyName,
            keyPairId, date);
  }
  /**
   * Creates a signed url to a CloudFront resource.
   * @param s3ObjectKeyName The S3 object's key name.
   * @param protocol The protocol you will be using. @defaults Protocol.http
   * @param lessThanDate The date that you want the request to expire in.
   * */
  public String createSignedUrl(
          String s3ObjectKeyName,
          Protocol protocol,
          Date lessThanDate) throws InvalidKeySpecException, IOException {
    if (privateKeyFile == null || keyPairId == null || domain == null) return null;
    Protocol prt= protocol;

    if (prt == null) {
      prt = Protocol.https;
    }
    return CloudFrontUrlSigner.getSignedURLWithCannedPolicy(prt, domain,
            privateKeyFile, s3ObjectKeyName,
            keyPairId, lessThanDate);
  }

  /**
   * Creates a SignedCookie object.
   *
   * @param s3BucketName The S3 bucket name you are getting the object from.
   * @param s3ObjectName The S3 object's name.
   *
   * @see SignedCookie
   * */
  public SignedCookie getSignedCookie(String s3BucketName, String s3ObjectName) {
    if (privateKeyFile == null || keyPairId == null || domain == null) return null;
    String resource = "https://" + domain + "/" + s3BucketName + "/" + s3ObjectName;
    return new SignedCookie(resource, privateKeyFile, keyPairId);
  }
}
