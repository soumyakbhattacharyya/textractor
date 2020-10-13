package com.rubiconred.ip.accounting.automation.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification.S3Entity;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class S3DocumentMetadata {

  private final Map<String, String> userMetadata;

  private final String id;
  private final String bucket;
  private final String key;
  private final String contentType;
  private final S3Entity entity;

  private final String eventTime; // The time, in ISO-8601 format, for example, 1970-01-01T00:00:00.000Z, when Amazon S3 finished processing the request
  private final String principalId; // Amazon-customer-ID-of-the-user-who-caused-the-event
  private final String xAmzRequestId; // Amazon S3 generated request ID
  private final String xAmzId2; // Amazon S3 host that processed the request
  private final String sequencer; // a string representation of a hexadecimal value used to determine event sequence, only used with PUTs and DELETEs"

  public S3DocumentMetadata(AmazonS3 s3, S3Event event) {

    List<S3EventNotificationRecord> records = event.getRecords();

    if (null != records && records.size() > 1) {
      throw new IllegalArgumentException("Expected 1 record, multiple received");
    }

    S3EventNotificationRecord cause = event.getRecords().get(0);

    entity = cause.getS3();
    this.id = UUID.randomUUID().toString();
    this.bucket = entity.getBucket().getName();
    this.key = entity.getObject().getKey();

    S3Object response = s3.getObject(new GetObjectRequest(bucket, key));
    this.contentType = response.getObjectMetadata().getContentType();
    this.userMetadata = response.getObjectMetadata().getUserMetadata();

    this.eventTime = cause.getEventTime().toString();
    this.principalId = cause.getUserIdentity().getPrincipalId();
    this.xAmzRequestId = cause.getResponseElements().getxAmzRequestId();
    this.xAmzId2 = cause.getResponseElements().getxAmzId2();
    this.sequencer = entity.getObject().getSequencer();
  }

  public String getId() {
    return id;
  }

  public String getBucket() {
    return bucket;
  }

  public String getEventTime() {
    return eventTime;
  }

  public String getPrincipalId() {
    return principalId;
  }

  public String getxAmzRequestId() {
    return xAmzRequestId;
  }

  public String getxAmzId2() {
    return xAmzId2;
  }

  public String getSequencer() {
    return sequencer;
  }

  public String getKey() {
    return key;
  }

  public String getContentType() {
    return contentType;
  }

  public S3Entity getEntity() {
    return entity;
  }

  public Map<String, String> getUserMetadata() {
    return userMetadata;
  }

  @Override
  public String toString() {
    return "S3DocumentMetadata [id=" + id + ", bucket=" + bucket + ", key=" + key + ", contentType=" + contentType + ", entity=" + entity + ", eventTime=" + eventTime
        + ", principalId=" + principalId + ", xAmzRequestId=" + xAmzRequestId + ", xAmzId2=" + xAmzId2 + ", sequencer=" + sequencer + "]";
  }

}
