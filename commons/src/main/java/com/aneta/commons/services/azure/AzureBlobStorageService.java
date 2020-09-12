package com.aneta.commons.services.azure;

import com.aneta.commons.providers.AzureBlobStorageProvider;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobErrorCode;
import com.azure.storage.blob.models.BlobStorageException;


import java.io.File;
import java.io.InputStream;

public class AzureBlobStorageService {
  private final AzureBlobStorageProvider provider;
  private BlobServiceClient client;


  AzureBlobStorageService(AzureBlobStorageProvider provider) {
    this.provider = provider;
    this.client = provider.create();
  }

  public BlobServiceClient createClient() {
    return provider.create();
  }

  public BlobContainerClient createContainer(String container) throws BlobStorageException {
    try {
      return client.createBlobContainer(container);
    } catch (BlobStorageException e) {
      if (!(e.getErrorCode().equals(BlobErrorCode.CONTAINER_ALREADY_EXISTS))) {
        throw e;
      }

      return client.getBlobContainerClient(container);
    }
  }

  public BlobContainerClient getContainer(String container) {
    return client.getBlobContainerClient(container);
  }

  public void upload(String container, File file) {
    BlobClient client = createContainer(container)
            .getBlobClient(file.getName());

    client.uploadFromFile(file.getPath());
  }

  public void upload(String container,
                     File file,
                     boolean overwrite) {
    BlobClient client = createContainer(container)
            .getBlobClient(file.getName());

    client.uploadFromFile(file.getPath(), overwrite);
  }

  public void upload(String container,
                     String filename,
                     InputStream data,
                     long length) {
    BlobClient client = createContainer(container)
            .getBlobClient(filename);

    client.upload(data, length);
  }

  public void upload(String container,
                     String filename,
                     InputStream data,
                     long length,
                     boolean overwrite) {
    BlobClient client = createContainer(container)
            .getBlobClient(filename);

    client.upload(data, length, overwrite);
  }
}
