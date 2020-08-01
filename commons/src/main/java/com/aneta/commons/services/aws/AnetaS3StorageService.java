package com.aneta.commons.services.aws;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.aneta.commons.models.aws.SignedCookie;
import com.aneta.commons.providers.S3StorageProvider;
import com.aneta.commons.utils.AWSSignedRequest;

import java.io.File;
import java.util.Date;
import java.util.List;

public interface AnetaS3StorageService {

  void setProvider(S3StorageProvider provider);

  AWSSignedRequest createSignedRequest(String domain,
                                       String keyPairId,
                                       File privateKeyFile);

  List<Bucket> getBuckets() throws SdkClientException;

  Bucket createBucket(String name) throws SdkClientException;

  void upload(String bucketName,
              File file,
              ObjectMetadata metadata) throws SdkClientException;

  String getSignedUrl(String domain,
                      String keyPairId,
                      File privateKey,
                      String s3BucketName,
                      String s3ObjectName);

  String getSignedUrl(String domain,
                      String keyPairId,
                      File privateKey,
                      String s3BucketName,
                      String s3ObjectName,
                      Date lessThanDate);

  SignedCookie getSignedCookie(String domain,
                               String keyPairId,
                               File privateKey,
                               String s3BucketName,
                               String s3ObjectName);
}
