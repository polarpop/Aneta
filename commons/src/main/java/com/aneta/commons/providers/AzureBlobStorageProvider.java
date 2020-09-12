package com.aneta.commons.providers;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

public class AzureBlobStorageProvider {
  private String sasToken;
  private String endpoint;

  AzureBlobStorageProvider() {}

  AzureBlobStorageProvider(String sasToken, String endpoint) {
    this.endpoint = endpoint;
    this.sasToken = sasToken;
  }

  public AzureBlobStorageProvider setToken(String sasToken) {
    this.sasToken = sasToken;
    return this;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public AzureBlobStorageProvider setEndpoint(String endpoint) {
    this.endpoint = endpoint;
    return this;
  }

  public BlobServiceClient create() throws RuntimeException {
    if (!(endpoint.equals("")) && !(sasToken.equals(""))) {
      return new BlobServiceClientBuilder()
              .sasToken(sasToken)
              .endpoint(endpoint)
              .buildClient();
    } else {
      throw new RuntimeException("You are missing your endpoint or token.");
    }
  }
}
