package net.bank.transactions.common.enums;

public enum StatusEnum {

  PENDING, SETTLED, FUTURE, INVALID;

  public String getStatus() {
    return this.name();
  }
}
