package net.macmv.colacoin.database;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HttpConnection {
  HttpURLConnection conn;

  public HttpConnection(String url) {
    try {
      conn = (HttpURLConnection) new URL(url).openConnection();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public HttpResponse send(HttpRequest request) {
    conn.setDoOutput(true);
    try {
      conn.setRequestMethod("POST");
    } catch (ProtocolException e) {
      throw new RuntimeException(e); // unreachable
    }

    for (String key : request.headers.keySet()) {
      conn.setRequestProperty(key, request.headers.get(key));
    }


    try {
      conn.connect();

      conn.getOutputStream().write(request.body.getBytes());

      byte[] buf = new byte[1024];
      int n = conn.getInputStream().read(buf);
      StringBuilder body = new StringBuilder();
      while (n != -1) {
        body.append(new String(buf, 0, n));
        n = conn.getInputStream().read(buf);
      }

      HttpResponse response = new HttpResponse();
      response.body = body.toString();
      return response;
    } catch (IOException e) {
      throw new RuntimeException(e); // TODO: Maybe nicer exception handling?
    } finally {
      conn.disconnect();
    }
  }
}
