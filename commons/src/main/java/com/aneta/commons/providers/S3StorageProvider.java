package com.aneta.commons.providers;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;


public class S3StorageProvider {
  private BasicAWSCredentials credentials;
  private AmazonS3 client;

  public S3StorageProvider() { }



  public S3StorageProvider authenticate(String accessKey, String secretKey) {
    this.credentials = new BasicAWSCredentials(accessKey, secretKey);
    return this;
  }

  public S3StorageProvider create(Regions region) {
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
}
