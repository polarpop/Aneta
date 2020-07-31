package com.aneta.core.ocr.processors;

import com.aneta.core.ocr.models.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public class ImageProcessor {
  private Image image;

  public ImageProcessor(Image image) {
    this.image = image;
  }

  public File process(float scale, float offset, String outputFileType) throws IOException {
    BufferedImage output = new BufferedImage(1050, 1024, image.getBufferedImage().getType());
    Graphics2D graphic = output.createGraphics();
    graphic.drawImage(image.getBufferedImage(), 0, 0, 1050, 1024, null);
    graphic.dispose();;

    RescaleOp rescale = new RescaleOp(scale, offset, null);

    BufferedImage file = rescale.filter(output, null);

    ImageIO.write(file, outputFileType, image.getFile());
    return image.getFile();
  }
}
