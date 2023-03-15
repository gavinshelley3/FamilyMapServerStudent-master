package Handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.nio.file.Files;
/**
 * FileHandler class
 */
public class FileHandler implements HttpHandler {
    /**
     * FileHandler constructor
     */
    public FileHandler() {}

    /**
     * Handles the HTTP request
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String urlPath = exchange.getRequestURI().toString();
            if (urlPath == null || urlPath.equals("/")) {
                urlPath = "/index.html";
            }
            String filePath = "web" + urlPath;
            File file = new File(filePath);
            if (file.exists()) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                Files.copy(file.toPath(), respBody);
                respBody.close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                OutputStream respBody = exchange.getResponseBody();
                Headers headers = exchange.getResponseHeaders();
                headers.set("Content-Type", "text/html");
                new OutputStreamWriter(respBody);
                file = new File("web/HTML/404.html");
                Files.copy(file.toPath(), respBody);
                respBody.close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders((HttpURLConnection.HTTP_SERVER_ERROR), 0);
            exchange.getResponseBody().close();
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
