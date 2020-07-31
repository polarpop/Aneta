package com.aneta.core.ocr.converters;

import jdk.internal.jline.internal.Nullable;
import net.sourceforge.tess4j.util.PdfUtilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PDFConverter {
  private File pdfFile;

  public PDFConverter() {}

  public PDFConverter(File pdfFile) {
    this.pdfFile = pdfFile;
  }

  public void setPdfFile(File pdfFile) {
    this.pdfFile = pdfFile;
  }

  public boolean isValidPdf(File file) {
    boolean isPDF;
     try {
       String contentType = Files.probeContentType(file.toPath());
       if (contentType.equals("application/pdf")) {
         isPDF = true;
       } else {
         isPDF = false;
       }
     } catch (IOException exception) {
       isPDF = false;
     }
     return isPDF;
  }

  public File convertPDFToTiff(@Nullable File file) {
     if (file == null && pdfFile == null) return null;

     try {
       if (isValidPdf(file)) {
         return PdfUtilities.convertPdf2Tiff(file);
       }
       return PdfUtilities.convertPdf2Tiff(pdfFile);
     } catch (IOException exception) {
       return null;
     }
  }

  public File[] convertPDFToPng(@Nullable File file) {
    if (file == null && pdfFile == null) return null;

    try {
      if (isValidPdf(file)) {
        return PdfUtilities.convertPdf2Png(file);
      }
      return PdfUtilities.convertPdf2Png(pdfFile);
    } catch (IOException exception) {
      return null;
    }
  }
}
