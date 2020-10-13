package com.rubiconred.ip.accounting.automation.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.rubiconred.ip.accounting.automation.manager.DynamoDBManager;
import com.rubiconred.ip.accounting.automation.model.DocumentInformationExtractionJob;

public class InvoiceProcessingStateDaoImpl implements InvoiceProcessingStateDao {

  private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

  private static volatile InvoiceProcessingStateDaoImpl instance;

  private InvoiceProcessingStateDaoImpl() {
  }

  public static InvoiceProcessingStateDaoImpl instance() {

    if (instance == null) {
      synchronized (InvoiceProcessingStateDaoImpl.class) {
        if (instance == null)
          instance = new InvoiceProcessingStateDaoImpl();
      }
    }
    return instance;
  }

  @Override
  public void saveOrUpdateEvent(DocumentInformationExtractionJob event) {

    mapper.save(event);
  }

}
