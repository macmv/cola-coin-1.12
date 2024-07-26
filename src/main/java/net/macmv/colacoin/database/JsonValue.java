package net.macmv.colacoin.database;

import com.google.gson.stream.JsonReader;

import java.io.IOException;

public interface JsonValue {
  void fromJson(JsonReader reader) throws IOException;

  class StringValue implements JsonValue {
    public String value;

    @Override
    public void fromJson(JsonReader reader) throws IOException {
      value = reader.nextString();
    }
  }

  class NumberValue implements JsonValue {
    public double value;

    @Override
    public void fromJson(JsonReader reader) throws IOException {
      value = reader.nextDouble();
    }
  }
}
