package Handler;

import Request.EventRequest;
import Result.EventResult;
import Service.EventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
/**
 * EventHandler class
 */
public class EventHandler implements HttpHandler {
    /**
     * Gson object used to serialize and deserialize JSON
     */
    Gson gson = new Gson();

    /**
     * Handles the HTTP request
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String authtoken = null;

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                if (exchange.getRequestHeaders().containsKey("Authorization")) {
                    authtoken = exchange.getRequestHeaders().getFirst("Authorization");
                }

                if (exchange.getRequestURI().toString().contains("/event/")) {
                    String url = exchange.getRequestURI().toString();
                    String[] urlArray = url.split("/");
                    String eventID = urlArray[2];

                    EventRequest request = new EventRequest(authtoken, eventID);
                    EventService service = new EventService();
                    EventResult result = service.getEvent(request);

                    if (result.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    OutputStream respBody = exchange.getResponseBody();
                    String gsonString = gson.toJson(result);
                    StringHandler.writeString(gsonString, respBody);
                    respBody.close();
                } else {
                    EventRequest request = new EventRequest(authtoken, null);
                    EventService service = new EventService();
                    EventResult result = service.getEvents(request);

                    if (result.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    OutputStream respBody = exchange.getResponseBody();
                    String gsonString = gson.toJson(result);
                    StringHandler.writeString(gsonString, respBody);
                    respBody.close();
                }
            }
        }
        catch (Exception e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
