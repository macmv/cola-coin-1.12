package net.macmv.colacoin.database;

import java.util.HashMap;

public class HttpRequest {
  public String body;
  public HashMap<String, String> headers = new HashMap<>();

  public void setHeader(String key, String value) {
    headers.put(key, value);
  }

  public void setBody(String body) {
    this.body = body;
  }
}
