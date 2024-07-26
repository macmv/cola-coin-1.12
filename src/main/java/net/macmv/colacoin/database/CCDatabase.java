package net.macmv.colacoin.database;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class CCDatabase {
  private static final URI FAUNA = URI.create("https://db.fauna.com/query/1");

  public static CCDatabase INSTANCE = new CCDatabase();

  public QueryResponse<AccountStatus> status(String secret) {
    return post(secret, "Query.identity() { username: .discord_name, balance }", new HashMap<>(), AccountStatus.class);
  }
  public QueryResponse<Voucher> create_voucher(String secret, int amount) {
    Map<String, Integer> args = new HashMap<>();
    args.put("amount", amount);
    return post(secret, "create_voucher(amount)", args, Voucher.class);
  }

  private HttpClient client = HttpClients.createDefault();

  private <T extends JsonValue> QueryResponse<T> post(String secret, String query, Map<String, Integer> args, Class<T> clazz) {
    try {
      return parseResponse(client.execute(buildRequest(secret, query, args)), clazz);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private HttpPost buildRequest(String secret, String query, Map<String, Integer> args) {
    try {
      HttpPost post = new HttpPost(FAUNA);

      Writer out = new StringWriter();
      JsonWriter writer = new JsonWriter(out);
      writer.beginObject();

      writer.name("query");
      writer.value(query);

      writer.name("arguments");
      writer.beginObject();
      for (Map.Entry<String, Integer> entry : args.entrySet()) {
        writer.name(entry.getKey());
        writer.value(entry.getValue());
      }
      writer.endObject();

      writer.endObject();
      writer.close();

      post.setHeader("Authorization", "Bearer " + secret);
      post.setHeader("Content-Type", "application/json");
      post.setEntity(new StringEntity(out.toString()));

      return post;
    } catch (IOException e) {
      throw new RuntimeException(e); // unreachable
    }
  }

  private <T extends JsonValue> QueryResponse<T> parseResponse(HttpResponse response, Class<T> clazz) {
    try {
      String res = EntityUtils.toString(response.getEntity());
      JsonReader reader = new JsonReader(new StringReader(res));

      QueryResponse qr = null;

      reader.beginObject();
      while (reader.hasNext()) {
        switch (reader.nextName()) {
          case "data":
            T out = clazz.getConstructor().newInstance();
            out.fromJson(reader);
            qr = new QuerySuccess<>(out);
            break;
          case "error":
            qr = QueryFailure.parse(reader);
            break;
          default:
            reader.skipValue();
        }
      }
      reader.endObject();

      return qr;
    } catch (IOException | NoSuchMethodException e) {
      throw new RuntimeException(e); // unreachable
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
