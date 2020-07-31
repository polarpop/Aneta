package com.aneta.commons.storages;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudfront.util.SignerUtils.Protocol;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.aneta.commons.models.SignedCookie;
import com.aneta.commons.utils.AWSSignedRequest;

import java.io.File;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class S3StorageProvider {
  private BasicAWSCredentials credentials;
  private AmazonS3 client;

  public S3StorageProvider() { }

  public AWSSignedRequest createAwsSignedProvider(String domain,
                                                  String keyPairId,
                                                  File privateKeyFile) {
    return new AWSSignedRequest(domain, keyPairId, privateKeyFile);
  }

  public S3StorageProvider authenticate(String accessKey, String secretKey) {
    this.credentials = new BasicAWSCredentials(accessKey, secretKey);
    return this;
  }

  public S3StorageProvider setClient(Regions region) {
    if (credentials != null) {
      this.client = AmazonS3ClientBuilder.standard()
              .withCredentials((AWSCredentialsProvider) credentials)
              .withRegion(region)
              .build();
    }
    return this;
  }

  public AmazonS3 getClient() {
    return client;
  }

  public List<Bucket> getBuckets() throws AmazonServiceException, SdkClientException {
    if (client == null) return new ArrayList<Bucket>();
    return client.listBuckets();
  }

  public Bucket getBucket(String name) {
    if (client.doesBucketExistV2(name)) {
      List<Bucket> buckets = getBuckets();
      for (Bucket bucket: buckets) {
        if (bucket.getName().equals(name)) {
          return bucket;
        }
      }
    }
    return null;
  }

  public Bucket createBucket(String name) throws AmazonServiceException, SdkClientException {
    Bucket bucket = null;
    if (client.doesBucketExistV2(name)) {
      bucket = getBucket(name);
    } else {
      bucket = client.createBucket(new CreateBucketRequest(name));
    }
    return bucket;
  }

  public void upload(String bucketName,
                     File file,
                     ObjectMetadata metadata) throws AmazonServiceException, SdkClientException {
    if (client != null) {
      PutObjectRequest request = new PutObjectRequest(bucketName, file.getName(), file);
      request.setMetadata(metadata);
      client.putObject(request);
    }
  }


  public String getSignedUrl(String domain,
                                String keyPairId, File privateKey,
                                String s3BucketName, String s3ObjectName) {
    try {
      String s3Object = s3BucketName + '/' + s3ObjectName;
      return new AWSSignedRequest()
              .addDomain(domain)
              .addKeyPairId(keyPairId)
              .addPrivateKeyFile(privateKey)
              .createSignedUrl(s3Object, Protocol.https);
    } catch (InvalidKeySpecException exception) {
      return null;
    } catch (IOException exception) {
      return null;
    }
  }

  public String getSignedUrl(String domain,
                                String keyPairId, File privateKey,
                                String s3BucketName, String s3ObjectName,
                                Date lessThanDate) {
    try {
      String s3Object = s3BucketName + '/' + s3ObjectName;
      return new AWSSignedRequest()
              .addDomain(domain)
              .addKeyPairId(keyPairId)
              .addPrivateKeyFile(privateKey)
              .createSignedUrl(s3Object, Protocol.https, lessThanDate);
    } catch (InvalidKeySpecException exception) {
      return null;
    } catch (IOException exception) {
      return null;
    }
  }

  public SignedCookie getSignedCookie(String domain, String keyPairId,
                                      File privateKey, String s3BucketName,
                                      String s3ObjectName) {
    return new AWSSignedRequest()
            .addDomain(domain)
            .addKeyPairId(keyPairId)
            .addPrivateKeyFile(privateKey)
            .getSignedCookie(s3BucketName, s3ObjectName);
  }
}
