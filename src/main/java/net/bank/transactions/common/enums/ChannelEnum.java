package net.bank.transactions.common.enums;

public enum ChannelEnum {

  CLIENT(true),
  ATM(true),
  INTERNAL(false);

  private boolean restFee;

  ChannelEnum(final boolean restFee) {
    this.restFee = restFee;
  }

  public String getChannel() {
    return this.name();
  }

  public boolean isRestFee() {
    return this.restFee;
  }
}
