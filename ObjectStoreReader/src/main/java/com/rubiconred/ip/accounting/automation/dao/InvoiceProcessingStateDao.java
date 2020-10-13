package com.rubiconred.ip.accounting.automation.dao;

import com.rubiconred.ip.accounting.automation.model.DocumentInformationExtractionJob;

public interface InvoiceProcessingStateDao {

  void saveOrUpdateEvent(DocumentInformationExtractionJob state);

}
