package net.macmv.colacoin.database;

import com.google.gson.stream.JsonReader;

import java.io.IOException;

public class LoginResult implements JsonValue {
  public String username;

  public LoginResult() {
  }

  @Override
  public void fromJson(JsonReader reader) throws IOException {
    username = reader.nextString();
  }
}
