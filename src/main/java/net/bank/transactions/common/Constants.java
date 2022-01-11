package net.bank.transactions.common;

public final class Constants {
  public static final String RESPONSE_CODE_OK = "200";
  public static final String RESPONSE_DESCRIPTION_OK = "OK";
  public static final String RESPONSE_CODE_CONFLICT = "409";
  public static final String RESPONSE_DESCRIPTION_CONFLICT = "Conflict";
  public static final String RESPONSE_CODE_FORBIDDEN = "403";
  public static final String RESPONSE_DESCRIPTION_FORBIDDEN = "Forbidden";
  public static final String RESPONSE_CODE_UNAUTHORIZED = "401";
  public static final String RESPONSE_DESCRIPTION_UNAUTHORIZED = "Unauthorized";
  public static final String RESPONSE_CODE_INTERNAL_SERVER_ERROR = "500";
  public static final String RESPONSE_DESCRIPTION_INTERNAL_SERVER_ERROR = "Internal Server Error";

  public static final String API_VERSION = "/api/v1";
  
  public static final String COLUMN_ORDER_TRANSACTIONS = "amount";

  private Constants() {
  }
}
