package com.rubiconred.ip.accounting.automation.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.rubiconred.ip.accounting.automation.util.EXECUTION_STATE;

@DynamoDBTable(tableName = "DOCUMENT_INFORMATION_EXTRACTION_JOB")
public class DocumentInformationExtractionJob {

  @DynamoDBHashKey
  private String causalEventId; // internal UUID that the processor assigns to the event

  @DynamoDBAttribute
  private String documentName;

  @DynamoDBAttribute
  private String bucket;

  @DynamoDBAttribute
  private String contentType;

  @DynamoDBAttribute
  private String eventTime;

  @DynamoDBAttribute
  private String principalId;

  @DynamoDBAttribute
  private String xAmzRequestId;

  @DynamoDBAttribute
  private String xAmzId2;

  @DynamoDBAttribute
  private String sequencer;

  @DynamoDBAttribute
  private String jobId; // job id comes from Textract, which it gives to each analysis task

  @DynamoDBAttribute
  private String executionState;

  public String getCausalEventId() {
    return causalEventId;
  }

  public void setCausalEventId(String causalEventId) {
    this.causalEventId = causalEventId;
  }

  public String getDocumentName() {
    return documentName;
  }

  public void setDocumentName(String documentName) {
    this.documentName = documentName;
  }

  public String getBucket() {
    return bucket;
  }

  public void setBucket(String bucket) {
    this.bucket = bucket;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getEventTime() {
    return eventTime;
  }

  public void setEventTime(String eventTime) {
    this.eventTime = eventTime;
  }

  public String getPrincipalId() {
    return principalId;
  }

  public void setPrincipalId(String principalId) {
    this.principalId = principalId;
  }

  public String getxAmzRequestId() {
    return xAmzRequestId;
  }

  public void setxAmzRequestId(String xAmzRequestId) {
    this.xAmzRequestId = xAmzRequestId;
  }

  public String getxAmzId2() {
    return xAmzId2;
  }

  public void setxAmzId2(String xAmzId2) {
    this.xAmzId2 = xAmzId2;
  }

  public String getSequencer() {
    return sequencer;
  }

  public void setSequencer(String sequencer) {
    this.sequencer = sequencer;
  }

  public String getJobId() {
    return jobId;
  }

  public void setJobId(String jobId) {
    this.jobId = jobId;
  }

  public String getExecutionState() {
    return executionState;
  }

  public void setExecutionState(String executionState) {
    this.executionState = executionState;
  }

  public DocumentInformationExtractionJob(S3DocumentMetadata documentMetadata, String jobId) {

    setJobId(jobId);

    setCausalEventId(documentMetadata.getId());
    setDocumentName(documentMetadata.getKey());
    setBucket(documentMetadata.getBucket());
    setContentType(documentMetadata.getContentType());
    setEventTime(documentMetadata.getEventTime());
    setPrincipalId(documentMetadata.getPrincipalId());
    setxAmzRequestId(documentMetadata.getxAmzRequestId());
    setxAmzId2(documentMetadata.getxAmzId2());
    setSequencer(documentMetadata.getSequencer());

    setExecutionState(EXECUTION_STATE.SUBMITTED.name());

  }

  public DocumentInformationExtractionJob() {
    super();
  }

}
