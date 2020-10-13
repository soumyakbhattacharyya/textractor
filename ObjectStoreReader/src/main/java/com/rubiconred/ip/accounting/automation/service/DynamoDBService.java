package com.rubiconred.ip.accounting.automation.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.rubiconred.ip.accounting.automation.dao.InvoiceProcessingStateDao;
import com.rubiconred.ip.accounting.automation.dao.InvoiceProcessingStateDaoImpl;
import com.rubiconred.ip.accounting.automation.model.DocumentInformationExtractionJob;

public class DynamoDBService {

  private static final InvoiceProcessingStateDao invoiceProcessingStateDao = InvoiceProcessingStateDaoImpl.instance();

  public void saveOrUpdateEvent(DocumentInformationExtractionJob state, Context context) {

    context.getLogger().log("Persisting state - " + state);

    if (null == state) {
      throw new IllegalArgumentException("Cannot save null object");
    }

    invoiceProcessingStateDao.saveOrUpdateEvent(state);

    context.getLogger().log("Successfully persisted.");
  }

}
