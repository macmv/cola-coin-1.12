package net.macmv.colacoin.database;

import com.google.gson.stream.JsonReader;

import java.io.IOException;

public class AccountStatus implements JsonValue {
  public String username;
  public int balance;

  public AccountStatus() {
  }

  @Override
  public void fromJson(JsonReader reader) throws IOException {
    reader.beginObject();
    while (reader.hasNext()) {
      switch (reader.nextName()) {
        case "balance":
          balance = reader.nextInt();
          break;
        case "username":
          username = reader.nextString();
          break;
      }
    }
    reader.endObject();
  }
}
