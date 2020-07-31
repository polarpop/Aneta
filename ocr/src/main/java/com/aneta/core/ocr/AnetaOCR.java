package com.aneta.core.ocr;

import com.aneta.core.ocr.converters.PDFConverter;
import com.aneta.core.ocr.processors.ImageProcessor;

import java.io.File;

public class AnetaOCR {
  private String dataPath;
  private final ImageProcessor processor;
  private final PDFConverter converter;
  private File tempImage;

  public AnetaOCR() {
    this.converter = new PDFConverter();
    this.processor = new ImageProcessor();
  }
  public AnetaOCR(String dataPath) {
    this.converter = new PDFConverter();
    this.processor = new ImageProcessor();
    this.dataPath = dataPath;
  }

  public ImageProcessor getProcessor() {
    return processor;
  }

  public PDFConverter getConverter() {
    return converter;
  }

  public void addPDF(File file) {
    tempImage = converter.convertPDFToTiff(file);
  }

  public void addImage(File file) {
    tempImage = file;
  }
}
