package Handler;

import Request.LoginRequest;
import Result.LoginResult;
import Service.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import static Handler.StringHandler.readString;
import static Handler.StringHandler.writeString;
/**
 * LoginHandler class
 */
public class LoginHandler implements HttpHandler {
    /**
     * Gson object used to serialize and deserialize JSON
     */
    Gson gson = new Gson();

    /**
     * LoginHandler constructor
     */
    public LoginHandler() {}

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
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                System.out.println(reqData);

                LoginRequest request = gson.fromJson(reqData, LoginRequest.class);
                LoginService service = new LoginService();
                LoginResult result = service.login(request);

                if (result.getAuthtoken() != null) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                OutputStream respBody = exchange.getResponseBody();
                String respData = gson.toJson(result);
                writeString(respData, respBody);
                respBody.close();
            }
        }
        catch(Exception e) {
            exchange.sendResponseHeaders((HttpURLConnection.HTTP_SERVER_ERROR), 0);
            exchange.getResponseBody().close();
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
