package com.aneta.commons.services.aws;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.aneta.commons.models.aws.SignedCookie;
import com.aneta.commons.providers.S3StorageProvider;
import com.aneta.commons.utils.AWSSignedRequest;

import java.io.File;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class S3StorageService {
  private S3StorageProvider provider;

  public S3StorageService() {}

  public S3StorageService(S3StorageProvider provider) {
    this.provider = provider;
  }

  public void setProvider(S3StorageProvider provider) {
    this.provider = provider;
  }

  public AWSSignedRequest createSignedRequest(String domain,
                                                  String keyPairId,
                                                  File privateKeyFile) {
    return new AWSSignedRequest(domain, keyPairId, privateKeyFile);
  }

  public List<Bucket> getBuckets() throws SdkClientException {
    if (provider.getClient() == null) return new ArrayList<Bucket>();
    return provider.getClient().listBuckets();
  }

  public Bucket getBucket(String name) {
    if (provider.getClient().doesBucketExistV2(name)) {
      List<Bucket> buckets = getBuckets();
      for (Bucket bucket: buckets) {
        if (bucket.getName().equals(name)) {
          return bucket;
        }
      }
    }
    return null;
  }

  public Bucket createBucket(String name) throws SdkClientException {
    Bucket bucket = null;
    if (provider.getClient().doesBucketExistV2(name)) {
      bucket = getBucket(name);
    } else {
      bucket = provider.getClient().createBucket(new CreateBucketRequest(name));
    }
    return bucket;
  }

  public void upload(String bucketName,
                     File file,
                     ObjectMetadata metadata) throws SdkClientException {
    if (provider.getClient() != null) {
      PutObjectRequest request = new PutObjectRequest(bucketName, file.getName(), file);
      request.setMetadata(metadata);
      provider.getClient().putObject(request);
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
              .createSignedUrl(s3Object, SignerUtils.Protocol.https);
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
              .createSignedUrl(s3Object, SignerUtils.Protocol.https, lessThanDate);
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
