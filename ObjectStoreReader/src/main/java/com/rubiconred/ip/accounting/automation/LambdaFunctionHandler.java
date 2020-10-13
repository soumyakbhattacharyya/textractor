package com.rubiconred.ip.accounting.automation;

import java.util.UUID;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.util.StringUtils;
import com.rubiconred.ip.accounting.automation.model.DocumentInformationExtractionJob;
import com.rubiconred.ip.accounting.automation.model.S3DocumentMetadata;
import com.rubiconred.ip.accounting.automation.service.DynamoDBService;
import com.rubiconred.ip.accounting.automation.service.Service;
import com.rubiconred.ip.accounting.automation.service.TextractService;

public class LambdaFunctionHandler implements RequestHandler<S3Event, String> {

  private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();

  public LambdaFunctionHandler() {
  }

  LambdaFunctionHandler(AmazonS3 s3) {
    this.s3 = s3;
  }

  @Override
  public String handleRequest(S3Event event, Context context) {
    context.getLogger().log("Received event: " + event);

    S3DocumentMetadata doc = new S3DocumentMetadata(s3, event);

    // TODO - look up this service
    Service<S3DocumentMetadata, String> textractService = new TextractService();
    DynamoDBService dbService = new DynamoDBService();

    try {

      context.getLogger().log(doc.toString());

      // invoke
      String jobId = getJobId(textractService, doc, context);

      // make db entry
      DocumentInformationExtractionJob state = new DocumentInformationExtractionJob(doc, jobId);
      dbService.saveOrUpdateEvent(state, context);

      context.getLogger().log("Finished processing event");
      return "Finished processing event";

    } catch (Exception e) {
      e.printStackTrace();
      context.getLogger().log(e.getMessage());
      throw e;
    }
  }

  /*
   * Textract invocation causes expense; this function ensure, in TEST scope, a dummy variable is being sent instead
   */
  private String getJobId(Service<S3DocumentMetadata, String> textractService, S3DocumentMetadata doc, Context context) {
    if (!StringUtils.isNullOrEmpty(System.getenv("SCOPE")) && System.getenv("SCOPE").equalsIgnoreCase("TEST")) {
      return "MOCK_JOB_ID_" + UUID.randomUUID().toString();
    } else {
      return textractService.invoke(doc, context);
    }
  }

}