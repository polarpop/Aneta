package com.aneta.core.ocr.models;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class Image {
  private File file;

  public Image() {
    String tmpdir = System.getProperty("java.io.tmpdir");
    String filename = String.join(".", UUID.randomUUID().toString(), ".tiff");
    setFile(tmpdir, filename);
  }

  public File getFile() {
    return file;
  }

  public void setFile(File newFile) {
    if (file != null) {
      dispose();
    }
    this.file = newFile;
  }

  public void setFile(String pathname, String filename) {
    Path filepath = Paths.get(pathname, filename);
    if (file != null) {
      dispose();
    }
    File newFile = new File(filepath.toString());
    if (!newFile.exists() && newFile.canWrite()) {
      try {
        newFile.createNewFile();
      } catch (IOException e) {}
    }

    this.file = newFile;

  }

  public BufferedImage getBufferedImage() {
    if (file.exists() && file.canRead()) {
      try {
        return ImageIO.read(file);
      } catch (Exception e) {}

    }
    return null;
  }

  public void dispose() {
    if (file.canWrite() && file.exists()) {
      file.delete();
      this.file = null;
    }
  }
}
