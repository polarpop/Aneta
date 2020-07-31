package com.aneta.core.ocr.processors;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public class ImageProcessor {
  private File image;
  private boolean HAS_PROCESSED;

  public ImageProcessor() {
    String tmp = System.getProperty("java.io.tmpdir");
    String filename = UUID.randomUUID().toString();
    this.image = new File(tmp + '/' + filename);
  }

  public File process(BufferedImage input,
                                float scale,
                                float offset,
                                String outputFileType) throws IOException {
    BufferedImage output = new BufferedImage(1050, 1024, input.getType());
    Graphics2D graphic = output.createGraphics();
    graphic.drawImage(input, 0, 0, 1050, 1024, null);
    graphic.dispose();;

    RescaleOp rescale = new RescaleOp(scale, offset, null);

    BufferedImage file = rescale.filter(output, null);

    ImageIO.write(file, outputFileType, image);
    HAS_PROCESSED = true;
    return image;
  }

  public void dispose() {
    if (image.isFile() && image.canWrite()) {
      image.delete();
      HAS_PROCESSED = false;
    }
  }
}
