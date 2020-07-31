package com.aneta.core.ocr;

import com.aneta.core.ocr.converters.PDFConverter;
import com.aneta.core.ocr.models.Image;
import com.aneta.core.ocr.processors.ImageProcessor;

import java.io.File;

public class AnetaOCR {
  private String dataPath;
  private final ImageProcessor imageProcessor;
  private final PDFConverter pdfConverter;
  private Image img;

  public AnetaOCR() {
    this.img = new Image();
    this.pdfConverter = new PDFConverter();
    this.imageProcessor = new ImageProcessor(this.img);
  }
  public AnetaOCR(String dataPath) {
    this.img = new Image();
    this.pdfConverter = new PDFConverter();
    this.imageProcessor = new ImageProcessor(this.img);
    this.dataPath = dataPath;
  }

  public ImageProcessor getImageProcessor() {
    return imageProcessor;
  }

  public PDFConverter getPdfConverter() {
    return pdfConverter;
  }

  public void addPDF(File file) {
    File image = pdfConverter.convertPDFToTiff(file);
    this.img.setFile(image);
  }

  public void addImage(File file) {
    this.img.setFile(file);
  }
}
