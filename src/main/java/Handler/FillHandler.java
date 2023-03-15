package Handler;

import Request.FillRequest;
import Result.FillResult;
import Service.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import static Handler.StringHandler.writeString;
/**
 * FillHandler class
 */
public class FillHandler implements HttpHandler {
    /**
     * Gson object used to serialize and deserialize JSON
     */
    Gson gson = new Gson();

    /**
     * FillHandler constructor
     */
    public FillHandler() {}

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
                if (exchange.getRequestURI().toString().contains("/fill/")) {
                    String url = exchange.getRequestURI().toString();
                    String[] urlArray = url.split("/");
                    String username = urlArray[2];
                    if (exchange.getRequestURI().toString().contains("/fill/" + username + "/")) {
                        String generations = urlArray[3];
                        FillRequest request = new FillRequest(username, Integer.parseInt(generations));
                        FillService service = new FillService();
                        FillResult result = service.fillGenerations(request);

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                        OutputStream respBody = exchange.getResponseBody();
                        String gsonString = gson.toJson(result);
                        writeString(gsonString,respBody);
                        respBody.close();
                    }
                    else {
                        FillRequest request = new FillRequest(username);
                        FillService service = new FillService();
                        FillResult result = service.fill(request);

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                        OutputStream respBody = exchange.getResponseBody();
                        String gsonString = gson.toJson(result);
                        writeString(gsonString,respBody);
                        respBody.close();
                    }
                }
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
