package com.rubiconred.ip.accounting.automation.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import com.amazonaws.services.textract.model.StartDocumentTextDetectionRequest;
import com.amazonaws.services.textract.model.StartDocumentTextDetectionResult;
import com.amazonaws.util.StringUtils;
import com.rubiconred.ip.accounting.automation.model.S3DocumentMetadata;
import com.rubiconred.ip.accounting.automation.model.factory.RequestFactory;
import com.rubiconred.ip.accounting.automation.util.Constants;

public class TextractService implements Service<S3DocumentMetadata, String> {

  public String invoke(S3DocumentMetadata event, Context context) {

    String roleArn = System.getenv(Constants.ROLE_ARN);
    String snsTopicArn = System.getenv(Constants.SNS_TOPIC_ARN);

    if (StringUtils.isNullOrEmpty(roleArn)) {
      throw new IllegalArgumentException(Constants.ROLE_ARN + " is required as environment variable");
    }
    if (StringUtils.isNullOrEmpty(snsTopicArn)) {
      throw new IllegalArgumentException(Constants.SNS_TOPIC_ARN + " is required as environment variable");
    }

    AmazonTextract textractClient = AmazonTextractClientBuilder.defaultClient();
    RequestFactory factory = new RequestFactory(event, roleArn, snsTopicArn);
    StartDocumentTextDetectionRequest req = factory.build();

    context.getLogger().log("Invoking Textract service");
    StartDocumentTextDetectionResult startDocumentTextDetectionResult = textractClient.startDocumentTextDetection(req);
    String textractJobId = startDocumentTextDetectionResult.getJobId();

    context.getLogger().log("Successfully returned. Textract Job Id: " + textractJobId);
    return textractJobId;
  }

}
