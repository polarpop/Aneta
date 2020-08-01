package com.aneta.commons.models.aws;

import com.amazonaws.services.s3.internal.ServiceUtils;
import com.amazonaws.util.Base64;
import com.aneta.commons.utils.SHA1HashUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SignedCookie {
  private String keyPairId;
  private File privateKeyFile;
  private Date expiresIn;
  private String resourcePath;

  public SignedCookie(String keyPairId, File privateKeyFile, String resourcePath) {
    this.keyPairId = keyPairId;
    this.privateKeyFile = privateKeyFile;
    this.resourcePath = resourcePath;
    setExpiresIn();
  }

  public String getKeyPairId() {
    return keyPairId;
  }

  public String getSignature() {
    String policy = getRawPolicy();
    try {
      return SHA1HashUtils.sign(policy, privateKeyFile);
    } catch (Exception e) {
      return null;
    }
  }

  public String getPolicy() {
    return Base64.encodeAsString(getRawPolicy().getBytes());
  }

  private String getRawPolicy() {
    return "{ \"Statement\": [{" +
            "\"Resource\": " + resourcePath +
            ", \"Condition\": {" +
            "\"DateLessThan\": { \"AWS:EpochTime\": " + expiresIn +
            "}}" +
            "}]" +
            "}";
  }

  private void setExpiresIn() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DAY_OF_MONTH, 1);
    String formatted = sdf.format(cal);
    this.expiresIn =  ServiceUtils.parseIso8601Date(formatted);
  }
}
