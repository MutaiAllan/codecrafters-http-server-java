import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Objects;
public class Main {
  public static void main(String[] args) {
    System.out.println("Logs from your program will appear here!");
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    try {
      serverSocket = new ServerSocket(4221);
      serverSocket.setReuseAddress(true);
      System.out.println("Waiting for connection!");
      clientSocket = serverSocket.accept(); // Wait for connection from client.
      System.out.println("Connected!");
      try (BufferedReader in = new BufferedReader(
               new InputStreamReader(clientSocket.getInputStream()))) {
        try (OutputStream out = clientSocket.getOutputStream()) {
          String[] request =
              in.readLine().split(" "); // Ignore the client input
          // if (Objects.equals(request[1], "/")) {
          String path = request[1];
          if (path.equals("/")) {
            out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
          } else if (path.startsWith("/echo/")) {
            String content = path.replace("/echo/", "");
                        out.write("""
                                HTTP/1.1 200 OK\r
                                Content-Type: text/plain\r
                                Content-Length: %d\r
                                \r
                                %s\r
1
                                """.formatted(content.length(), content).trim().getBytes());
            } else {
            out.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
          }
        }
      }
      System.out.println("accepted new connection");
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }
}