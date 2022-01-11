package net.bank.transactions.common.exception;

import lombok.Getter;

@Getter
public enum CodeException {

  ERR_0001("Transactions Not Found"),
  ERR_0002("Transaction reference Not Found"),
  ERR_0003("Amount can not be 0 or null"),
  ERR_0004("Account IBAN can not be 0 or null");

  private final String description;

  CodeException(String description) {
    this.description = description;
  }

  public String getCode() {
    return this.name().replace("_", "-");
  }
}
