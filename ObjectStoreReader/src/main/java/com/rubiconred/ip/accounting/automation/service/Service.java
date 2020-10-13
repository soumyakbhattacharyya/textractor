package com.rubiconred.ip.accounting.automation.service;

import com.amazonaws.services.lambda.runtime.Context;

public interface Service<INPUT, OUTPUT> {

  public OUTPUT invoke(INPUT event, Context context);

}
