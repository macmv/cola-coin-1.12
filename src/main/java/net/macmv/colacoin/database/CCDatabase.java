package net.macmv.colacoin.database;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CCDatabase {
  private static final String FAUNA = "https://db.fauna.com/query/1";

  public static CCDatabase INSTANCE = new CCDatabase();

  public QueryResponse<AccountStatus> status(String secret) {
    return post(secret, "Query.identity() { username: .discord_name, balance }", new HashMap<>(), AccountStatus.class);
  }
  public QueryResponse<Voucher> create_voucher(String secret, int amount) {
    Map<String, Long> args = new HashMap<>();
    args.put("amount", (long) amount);
    return post(secret, "create_voucher(amount)", args, Voucher.class);
  }
  public QueryResponse<RedeemedVoucher> redeem_voucher(String secret, long id) {
    Map<String, Long> args = new HashMap<>();
    args.put("id", id);
    return post(secret, "redeem_voucher(Voucher(id))", args, RedeemedVoucher.class);
  }

  private <T extends JsonValue> QueryResponse<T> post(String secret, String query, Map<String, Long> args, Class<T> clazz) {
    HttpConnection client = new HttpConnection(FAUNA);
    return parseResponse(client.send(buildRequest(secret, query, args)), clazz);
  }

  private HttpRequest buildRequest(String secret, String query, Map<String, Long> args) {
    try {
      HttpRequest request = new HttpRequest();

      Writer out = new StringWriter();
      JsonWriter writer = new JsonWriter(out);
      writer.beginObject();

      writer.name("query");
      writer.value(query);

      writer.name("arguments");
      writer.beginObject();
      for (Map.Entry<String, Long> entry : args.entrySet()) {
        writer.name(entry.getKey());
        writer.value(entry.getValue());
      }
      writer.endObject();

      writer.endObject();
      writer.close();

      request.setHeader("Authorization", "Bearer " + secret);
      request.setHeader("Content-Type", "application/json");
      request.setBody(out.toString());

      return request;
    } catch (IOException e) {
      throw new RuntimeException(e); // unreachable
    }
  }

  private <T extends JsonValue> QueryResponse<T> parseResponse(HttpResponse response, Class<T> clazz) {
    try {
      JsonReader reader = new JsonReader(new StringReader(response.body));

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
