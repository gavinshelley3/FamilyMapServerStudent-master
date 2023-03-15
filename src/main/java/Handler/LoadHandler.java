package Handler;

import DataAccess.DataAccessException;
import Request.LoadRequest;
import Result.LoadResult;
import Service.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
/**
 * LoadHandler class
 */
public class LoadHandler implements HttpHandler {
    /**
     * Gson object used to serialize and deserialize JSON
     */
    private final Gson gson = new Gson();

    /**
     * LoadHandler constructor
     */
    public LoadHandler() {}

    /**
     * Handles the HTTP request
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                exchange.getRequestHeaders();
                InputStream reqBody = exchange.getRequestBody();
                String reqData = StringHandler.readString(reqBody);
                System.out.println(reqData);

                LoadRequest request = gson.fromJson(reqData, LoadRequest.class);
                LoadService service = new LoadService();
                LoadResult result = service.load(request);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream resBody = exchange.getResponseBody();
                String json = gson.toJson(result);
                StringHandler.writeString(json, resBody);
                resBody.close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
