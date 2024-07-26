package net.macmv.colacoin.database;

import com.google.gson.stream.JsonReader;

import java.io.IOException;

public class RedeemedVoucher implements JsonValue {
  public int amount;

  public RedeemedVoucher() {
  }

  @Override
  public void fromJson(JsonReader reader) throws IOException {
    amount = reader.nextInt();
  }
}
