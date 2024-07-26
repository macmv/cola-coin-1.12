package net.macmv.colacoin.database;

import com.google.gson.stream.JsonReader;

import java.io.IOException;

public class Voucher implements JsonValue {
  public long id;
  public int amount;

  public Voucher() {
  }

  @Override
  public void fromJson(JsonReader reader) throws IOException {
    reader.beginObject();
    while (reader.hasNext()) {
      switch (reader.nextName()) {
        case "id":
          id = Long.parseLong(reader.nextString());
          break;
        case "amount":
          amount = reader.nextInt();
          break;
      }
    }
    reader.endObject();
  }
}
