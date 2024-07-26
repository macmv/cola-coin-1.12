package net.macmv.colacoin.database;

public class QuerySuccess<T extends JsonValue> implements QueryResponse {
  public final T value;
  public QuerySuccess(T value) {
    this.value = value;
  }
}
