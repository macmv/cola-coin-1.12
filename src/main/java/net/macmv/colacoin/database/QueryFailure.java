package net.macmv.colacoin.database;

import com.google.gson.stream.JsonReader;

import java.io.IOException;

public class QueryFailure<T extends JsonValue> implements QueryResponse<T> {
  public final String code;
  public final String message;
  public QueryFailure(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public static QueryResponse<? extends JsonValue> parse(JsonReader reader) throws IOException {
    String code = "";
    String message = "";

    reader.beginObject();
    while (reader.hasNext()) {
      switch (reader.nextName()) {
        case "code":
          code = reader.nextString();
          break;
        case "message":
          message = reader.nextString();
          break;
        default:
          reader.skipValue();
          break;
      }
    }
    reader.endObject();

    return new QueryFailure<>(code, message);
  }
}
