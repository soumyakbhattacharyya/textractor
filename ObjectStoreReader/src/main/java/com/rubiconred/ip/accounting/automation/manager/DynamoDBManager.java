package com.rubiconred.ip.accounting.automation.manager;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class DynamoDBManager {

  private static volatile DynamoDBManager instance;

  private static DynamoDBMapper mapper;

  private DynamoDBManager() {

    AmazonDynamoDB defaultClient = AmazonDynamoDBClientBuilder.defaultClient();
    mapper = new DynamoDBMapper(defaultClient);
  }

  public static DynamoDBManager instance() {

    if (instance == null) {
      synchronized (DynamoDBManager.class) {
        if (instance == null)
          instance = new DynamoDBManager();
      }
    }

    return instance;
  }

  public static DynamoDBMapper mapper() {

    DynamoDBManager manager = instance();
    return manager.mapper;
  }

}