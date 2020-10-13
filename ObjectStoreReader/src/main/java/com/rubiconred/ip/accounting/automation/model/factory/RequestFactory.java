package com.rubiconred.ip.accounting.automation.model.factory;

import com.amazonaws.services.textract.model.DocumentLocation;
import com.amazonaws.services.textract.model.NotificationChannel;
import com.amazonaws.services.textract.model.S3Object;
import com.amazonaws.services.textract.model.StartDocumentTextDetectionRequest;
import com.rubiconred.ip.accounting.automation.model.S3DocumentMetadata;

public class RequestFactory {

  private final String roleArn;
  private final String topicArn;
  private final S3DocumentMetadata doc;

  public RequestFactory(S3DocumentMetadata doc, String roleArn, String topicArn) {
    super();
    this.roleArn = roleArn;
    this.topicArn = topicArn;
    this.doc = doc;
  }

  public StartDocumentTextDetectionRequest build() {

    S3Object textractModelS3Object = new com.amazonaws.services.textract.model.S3Object().withBucket(this.doc.getBucket()).withName(this.doc.getKey());
    DocumentLocation textractModelDocumentLocation = new DocumentLocation().withS3Object(textractModelS3Object);
    StartDocumentTextDetectionRequest req = new StartDocumentTextDetectionRequest().withDocumentLocation(textractModelDocumentLocation).withJobTag("InvoiceAnalyzer");
    NotificationChannel channel = new NotificationChannel();
    channel.setRoleArn(roleArn);
    channel.setSNSTopicArn(topicArn);
    req.setNotificationChannel(channel);

    return req;
  }

}
