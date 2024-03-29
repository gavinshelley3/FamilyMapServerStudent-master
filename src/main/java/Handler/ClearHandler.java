package Handler;

import DataAccess.DataAccessException;
import Request.ClearRequest;
import Result.ClearResult;
import Service.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
/**
 * ClearHandler class
 */
public class ClearHandler implements HttpHandler {
    /**
     * ClearHandler constructor
     */
    public ClearHandler() {}

    /**
     * The Gson object used to serialize and deserialize JSON
     */
    private final Gson gson = new Gson();

    /**
     * Handles the HTTP request
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {

                ClearRequest request = new ClearRequest();
                ClearService service = new ClearService();
                ClearResult result = service.clear(request);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream resBody = exchange.getResponseBody();
                String json = gson.toJson(result);
                StringHandler.writeString(json, resBody);
                resBody.close();
                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}
